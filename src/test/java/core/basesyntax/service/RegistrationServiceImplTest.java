package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        User userDeniska = new User();
        userDeniska.setAge(18);
        userDeniska.setLogin("denys18");
        userDeniska.setPassword("qwe123");

        User userBob = new User();
        userBob.setAge(22);
        userBob.setLogin("bobito22");
        userBob.setPassword("bobikLobik");

        Storage.people.add(userDeniska);
        Storage.people.add(userBob);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_duplicateLogin_notOk() {
        User userDenys = new User();
        userDenys.setAge(18);
        userDenys.setLogin("denys18");
        userDenys.setPassword("qwe123");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userDenys);
        });
    }

    @Test
    void register_incorrectLengthLogin_notOK() {
        User userAlice = new User();
        userAlice.setAge(18);
        userAlice.setLogin("Ali");
        userAlice.setPassword("qwe123");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userAlice);
        });
    }

    @Test
    void register_incorrectLengthPassword_notOk() {
        User userAlice = new User();
        userAlice.setAge(18);
        userAlice.setLogin("Alice18");
        userAlice.setPassword("qwe");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userAlice);
        });
    }

    @Test
    void register_underAgedUser_notOk() {
        User userAlex = new User();
        userAlex.setAge(16);
        userAlex.setLogin("Alex16");
        userAlex.setPassword("qazwsxx123");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userAlex);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        User userAlex = new User();
        userAlex.setAge(16);
        userAlex.setLogin("Alex16");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userAlex);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        User userAlex = new User();
        userAlex.setAge(16);
        userAlex.setPassword("qazwsxx123");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userAlex);
        });
    }

    @Test
    void register_nullAge_notOk() {
        User userAlex = new User();
        userAlex.setLogin("Alex16");
        userAlex.setPassword("qazwsxx123");
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userAlex);
        });
    }

    @Test
    void register_correctUser_ok() {
        User userAlex = new User();
        userAlex.setAge(22);
        userAlex.setLogin("Alex161");
        userAlex.setPassword("qazwsx123");
        assertEquals(userAlex, registrationService.register(userAlex));
    }

    @Test
    void register_addUserTwice_notOk() {
        User userDenys = new User();
        userDenys.setAge(44);
        userDenys.setLogin("denchik777");
        userDenys.setPassword("qwe123");
        registrationService.register(userDenys);
        assertThrows(UserValidationException.class, () -> {
            registrationService.register(userDenys);
        });
    }

    @Test
    void register_addDifferentCorrectUsers_ok() {
        User userDenys = new User();
        userDenys.setAge(36);
        userDenys.setLogin("666666");
        userDenys.setPassword("666666");

        User userVovka = new User();
        userVovka.setAge(56);
        userVovka.setLogin("f23f9h3f23f83f8f923hf923hf28hf23hf239fh");
        userVovka.setPassword("f3208i9f2389f2389f2389fh923hf92h3f892h");

        User userEva = new User();
        userEva.setAge(23);
        userEva.setLogin("_22_2_--");
        userEva.setPassword("!fk((dkasm");
        assertEquals(userDenys, registrationService.register(userDenys));
        assertEquals(userVovka, registrationService.register(userVovka));
        assertEquals(userEva, registrationService.register(userEva));
    }
}
