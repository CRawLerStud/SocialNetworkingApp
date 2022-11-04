package validators;

import models.Friendship;
import models.User;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        validateFriendship(entity);
    }

    /**
     * Validates a friendship<br>
     * A friendship is valid if:
     * <ul>
     *     <li>Has different users</li>
     *     <li>No user is null</li>
     * </ul>
     * @param entity the friendship that is validating
     * @throws ValidationException if the friendship is not valid
     */
    private void validateFriendship(Friendship entity) throws ValidationException {
        User user1 = entity.getUser1();
        User user2 = entity.getUser2();
        if(user1 == user2 || user1 == null || user2 == null)
            throw new ValidationException("Invalid friendship!");
    }


}
