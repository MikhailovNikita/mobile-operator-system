package integration;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tsystems.persistence.dao.api.UserDAO;
import ru.tsystems.persistence.entity.User;

import javax.persistence.PersistenceException;
import java.util.Date;

import static ru.tsystems.persistence.entity.UserRole.ROLE_USER;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring.xml"})
@WebAppConfiguration
@Transactional
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void getUser() {
        User admin = userDAO.get(1L);
        Assert.assertEquals("a@b.c", admin.getEmail());
    }

    @Test
    public void getAllUsers() {
       Assert.assertTrue(userDAO.getAllClients().size() > 1);
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void saveEntity() {
        User user = new User();
        user.setEmail("a@b.c");
        user.setName("John");
        user.setLastName("Smith");
        user.setPassport("123123123");
        user.setAddress("Karaganda");
        user.setBirthDate(new Date());
        user.setBlocked(false);
        user.setRole(ROLE_USER);
        user.setPassword("qwerty");
        userDAO.persist(user);
    }
}
