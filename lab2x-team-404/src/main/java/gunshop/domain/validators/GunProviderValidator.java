package gunshop.domain.validators;

import gunshop.domain.Category;
import gunshop.domain.GunProvider;
import utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class GunProviderValidator implements Validator<GunProvider> {
    @Override
    public void validate(GunProvider entity) throws ValidatorException
    {


        Predicate<String> nameIsNull = (s) -> (s==null || s.equals(""));
        boolean nameNotValid = nameIsNull.test(entity.getName());
        String nameNotValidMessage = "Gun Provider must have a name! ";


        Predicate<String> specialityIsNull = (s) -> (s==null || s.equals(""));
        boolean specialityNotValid = specialityIsNull.test(entity.getSpeciality());
        String specialityNotValidMessage = "Gun Provider must have a Speciality! ";

        Predicate<Integer> reputationOutOfBoundaries = (i) -> !(i>0 && i<=10);
        boolean reputationNotValid = reputationOutOfBoundaries.test(entity.getReputation());
        String reputationNotValidMessage = "Gun Provider must have a reputation between 1 and 10! ";



        List<Pair<Boolean,String>> checkList = Arrays.asList(new Pair<Boolean,String>(nameNotValid,nameNotValidMessage),
                new Pair<Boolean,String>(specialityNotValid,specialityNotValidMessage), new Pair<Boolean, String>(reputationNotValid,reputationNotValidMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce(String :: concat)
                .ifPresent(s -> { throw new ValidatorException(s); });

    }
}
