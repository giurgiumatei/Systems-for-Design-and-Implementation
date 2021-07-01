package gunshop.domain.validators;

import gunshop.domain.Category;
import gunshop.domain.GunType;
import utils.Pair;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

public class GunTypeValidator implements Validator<GunType> {

    /**
     * Validates a given GunType entity.
     * @param entity - non-null
     * @throws ValidatorException if entity is invalid, meaning it doesn't have: a non-null name and a non-null category.
     */
    @Override
    public void validate(GunType entity) throws ValidatorException
    {


        Predicate<String> nameIsNull = (s) -> (s==null || s.equals(""));
        boolean nameNotValid = nameIsNull.test(entity.getName());
        String nameNotValidMessage = "Gun Types must have a name! ";

        Predicate<String> categoryIsNull = (s) -> (s==null || s.equals(""));
        //boolean categoryNotValid = entity.getCategory() != null;
        boolean categoryNotValid = !Arrays.asList(Category.values()).contains(entity.getCategory());
//        boolean categoryNotValid = categoryIsNull.test(entity.getCategory());
        String categoryNotValidMessage = "Gun Types category is incorrect!";


        List<Pair<Boolean,String>> checkList = Arrays.asList(new Pair<Boolean,String>(nameNotValid,nameNotValidMessage),
                new Pair<Boolean,String>(categoryNotValid,categoryNotValidMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce(String :: concat)
                .ifPresent(s -> { throw new ValidatorException(s); });

    }
}
