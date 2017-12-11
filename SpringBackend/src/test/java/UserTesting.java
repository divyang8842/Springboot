import com.entity.User;
import com.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserRepository.class)
@DataJpaTest
public class UserTesting {

    private UserRepository userRepository;

    @Before
    public void setUp() {
       userRepository = Mockito.mock(UserRepository.class);
    }


    @Test
    public void UserFind() {
        User user = userRepository.findByEmail("a@b.cd");
        assertNotEquals("Durectory found",null,user);
    }

    @Test
    public void UserCreation() {

        User user = new User();
        user.setPassword("12");
        user.setLastname("13");
        user.setFirstname("41");
        user.setEmail("16");
        user.setRootdir("91");

        user = userRepository.save(user);
        String userid = String.valueOf(user.getId());
        assertNotEquals("User Creted",Long.parseLong(userid),0);
    }

    @Test
    public void UserUpdation() {

        User user = userRepository.findByEmail("a@b.cd");
        user.setRootdir("50");
        user = userRepository.save(user);
        String userid = String.valueOf(user.getId());
        assertEquals("User Updated",user.getRootdir(),"50");
    }
}