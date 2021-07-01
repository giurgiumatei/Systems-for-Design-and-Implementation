package ro.ubb.springjpa.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.springjpa.model.Client;
import ro.ubb.springjpa.utils.Pair;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class ClientValidator implements Validator<Client> {

    /**
     * Validates a given Client entity.
     * @param entity - non-null.
     * @throws ValidatorException if entity is invalid, meaning it doesn't have: non-null name or clients age is lower than 18.
     */
    @Override
    public void validate(Client entity) throws ValidatorException {
        Predicate<String> isNull = (s) -> (s==null || s.equals(""));
        boolean nameNotValid = isNull.test(entity.getName());
        String nameNotValidMessage = "Client name cannot be empty! ";

        Predicate<LocalDate> isLessDifference = (dt) -> ( Period.between( dt,LocalDate.now()).getYears()<18) ;
        boolean isLess = isLessDifference.test(entity.getDateOfBirth());
        String isLessMessage = "Client must be at least 18 years of age!";

        List<Pair<Boolean,String>> checkList = Arrays.asList(new Pair<Boolean,String>(nameNotValid,nameNotValidMessage),
                new Pair<Boolean,String>(isLess,isLessMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent(s -> { throw new ValidatorException(s); });

    }

}