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
        if (user.getLogin() == null) {
            throw new UserValidationException("Users login can not be null");
        }
        if (user.getLogin().length() < MINIMAL_LENGTH_FOR_LOGIN_PASSWORD) {
            throw new UserValidationException("Login length must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("There is already User with such login");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Users password can not be null");
        }
        if (user.getPassword().length() < MINIMAL_LENGTH_FOR_LOGIN_PASSWORD) {
            throw new UserValidationException("Password length must be at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new UserValidationException("Users who under 18 years old, can not register");
        }

        storageDao.add(user);
        return user;
    }
}
