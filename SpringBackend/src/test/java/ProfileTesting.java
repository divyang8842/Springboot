import com.entity.Profile;
import com.repository.ProfileRepository;
import com.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfileRepository.class)
@DataJpaTest
public class ProfileTesting {

    private ProfileRepository profileRepository;

    @Before
    public void setUp() {
        profileRepository = Mockito.mock(ProfileRepository.class);
    }

    @Test
    public void ProfileFind() {
        Profile profile = profileRepository.findByUserid(1);
        assertNotEquals("Profile Found",null,profile);
    }

    @Test
    public void ProfileInsert() {
        Profile profile = new Profile();
        profile.setUserid(1);
        profile.setEducation("Education");
        profile.setMobile("123456");
        profile.setShows("Shows");
        profile = profileRepository.save(profile);
        Assert.assertEquals("Profile Insert",profile.getEducation(),"Education");
    }


    @Test
    public void ProfileUpdate() {
        Profile profile = profileRepository.findByUserid(1);
        profile.setMobile("123456789");
        profile = profileRepository.save(profile);
        Assert.assertEquals("Profile Insert",profile.getMobile(),"123456789");
    }

}
