package ro.ubb.catalog.core.model.validators;

import ro.ubb.catalog.core.model.exceptions.ValidatorException;

public interface Validator<T> {

    /**
     * Validates a given entity.
     * @param entity - non-null
     * @throws ValidatorException if entity is not valid
     */
    void validate(T entity) throws ValidatorException;
}
