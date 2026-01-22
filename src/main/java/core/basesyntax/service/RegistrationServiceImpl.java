package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH_FOR_LOGIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserValidationException("User can not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("There is already User with such login");
        }
        if (user.getLogin() == null
                || user.getLogin().length() < MINIMAL_LENGTH_FOR_LOGIN_PASSWORD) {
            throw new UserValidationException("Users login length must be more then 6");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MINIMAL_LENGTH_FOR_LOGIN_PASSWORD) {
            throw new UserValidationException("Users password must be more then 6");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new UserValidationException("Users who under 18 years old, can not register");
        }
        storageDao.add(user);
        return user;
    }
}
