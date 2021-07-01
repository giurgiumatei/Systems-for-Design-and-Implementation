package ro.ubb.catalog.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Rental;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

@Component
public class RentalValidator implements Validator<Rental> {

    /**
     * Validates a given Rental entity.
     * @param entity - non-null.
     * @throws ValidatorException if entity is invalid, meaning it doesn't have: a price bigger than 0.
     */
    @Override
    public void validate(Rental entity) throws ValidatorException
    {

        if(entity.getPrice() <= 0)
        {
            throw new ValidatorException("Rental price must bigger than 0!");
        }

    }
}
