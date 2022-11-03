package validators;

import models.Friendship;
import models.User;

public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        validateFriendship(entity);
    }

    private void validateFriendship(Friendship entity) throws ValidationException {
        User user1 = entity.getUser1();
        User user2 = entity.getUser2();
        if(user1 == user2)
            throw new ValidationException("Same user!");
    }


}
