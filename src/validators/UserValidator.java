package validators;

import models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User>{
    @Override
    public void validate(User entity) throws ValidationException {
        validateName(entity.getLastname(), entity.getSurname());
        validateAge(entity.getYears());
    }

    /**
     * Validates two strings(lastname and surname) that represent a fullname.
     * <br>A lastname/surname is valid if:<br>
     * <ul>
     *     <li>Contains just letters and spaces.</li>
     *     <li>Contains at least one letter.</li>
     * </ul>
     * @param lastname a lastname(String)
     * @param surname a surname(String)
     * @throws ValidationException if the fullname is not valid.
     */
    private void validateName(String lastname, String surname) throws ValidationException {
        if(lastname.length() == 0 || surname.length() == 0)
            throw new ValidationException("Invalid name!");
        Pattern pattern = Pattern.compile("^[A-Z][A-Za-z ]+");
        Matcher matcher = pattern.matcher(lastname);
        if (!(matcher.matches()) && lastname.trim().length() > 0)
            throw new ValidationException("Invalid lastname!");
        matcher = pattern.matcher(surname);
        if (!(matcher.matches()) && surname.trim().length() > 0)
            throw new ValidationException("Invalid surname!");
    }

    /**
     * Validates a number which represents the age of a user.
     * <br>The age is valid if it is <b>at least (<=) 13.</b>
     * @param years the age of a user.
     * @throws ValidationException if the age is not valid.
     */
    private void validateAge(int years) throws ValidationException {
        if (years < 13)
            throw new ValidationException("Invalid age!");
    }

}
