package gunshop.repository;

import gunshop.domain.BaseEntity;
import gunshop.domain.validators.GunShopException;
import gunshop.domain.validators.Validator;
import gunshop.domain.validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Optional;


/**
 * This is an implementation of a repository using XML files. It uses the files as cache storage and just
 * loads and saves the data after each operation (performed in-memory). It is much simpler to implement than creating
 * one operation for each needed in order to work with XML and does the same thing.
 * @param <ID> the id type of the entity
 * @param <T> the entity type
 */
public class XMLRepository<ID,T extends BaseEntity<ID>> extends InMemoryRepository<ID,T> {

    private final Class<T> classOfT;
    private final String fileName;

    public XMLRepository(Validator<T> validator, String fileName, Class<T> type)
    {
        super(validator);
        this.fileName = fileName;
        this.classOfT = type;
        loadDataFromXML();
    }

    /**
     * Gets the fileName.
     * @return String - fileName
     */
    private String getFileName() {
        return fileName;
    }

    /**
     * Reads the entities from a given text file and loads them into the main memory.
     */
    public void loadDataFromXML(){
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document root = documentBuilder.parse(getFileName());

            Element data = root.getDocumentElement();
            NodeList dataList = data.getChildNodes();

            for(int i=0; i < dataList.getLength(); ++i){
                if(!(dataList.item(i) instanceof Element))
                    continue;

                // we use reflection to access createFromNode method
                // the first parameter is the name of the method and the second one is an array containing the types of the parameters
                Method createFromNodeMethod = BaseEntity.class.getDeclaredMethod("createFromNode", Element.class);

                @SuppressWarnings("unchecked")
                T eachItem = (T) createFromNodeMethod.invoke(classOfT.getDeclaredConstructor().newInstance(), dataList.item(i));
                super.save(eachItem);
            }
        } catch (Exception e) {
            throw new GunShopException("Could not load from XML File");
        }
    }

    /**
     *Removes all the nodes in the XML tree.
     */
    private void removeAllNodes(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(getFileName());

            document.getDocumentElement().setTextContent("");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(getFileName()))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an entity to a document.
     * @param entity - given entity.
     * @param document - given document.
     */
    private void addToDocument(T entity, Document document){
        Element root = document.getDocumentElement();
        try {
            Method createNodeFromThisMethod = BaseEntity.class.getDeclaredMethod("createNodeFromThis", Document.class);
            root.appendChild((Node) createNodeFromThisMethod.invoke(entity,document));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all entities to the XML file.
     */
    private void saveAllEntitiesToXML(){
        try {
            // another way of initializing a DocumentBuilder object than in loadDataFromXML
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(getFileName());


            super.findAll().forEach(entity -> {
                addToDocument(entity, document);
            }
            );
            /*for(T entity : entities.values()){
                addTtoDocument(entity, document);
            }*/

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(getFileName()))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a given entity to the XML file.
     * @param entity - given entity.
     */
    private void saveEntityToXML(T entity){
        try {
            // another way of initializing a DocumentBuilder object than in loadDataFromXML
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(getFileName());

            addToDocument(entity, document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(getFileName()))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the given entity into the repository.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws GunShopException
     *            if the id is not unique.
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
//        TODO don't save all when adding, just when close OR save only the last

        Optional<T> optional = super.save(entity);
        if (optional.isEmpty()) {
            saveEntityToXML(entity);
            return Optional.empty();
        }
        return optional; //duplicate element
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the deleted entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> delete(ID id)
    {
        Optional<T> optional = super.delete(id);
        if (optional.isPresent()) {
            removeAllNodes();
            saveAllEntitiesToXML();
            return optional;
        }
        return Optional.empty(); //not existing
    }

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id doesn't exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        Optional<T> optional = super.update(entity);
        if (optional.isPresent()) {
            removeAllNodes();
            saveAllEntitiesToXML();
            return optional;
        }
        return Optional.empty(); //not existing
    }

}
