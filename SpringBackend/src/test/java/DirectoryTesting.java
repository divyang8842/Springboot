import com.entity.Directory;
import com.entity.User;
import com.repository.DirectoryRepository;
import com.repository.UserRepository;
import com.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DirectoryRepository.class)
@DataJpaTest
public class DirectoryTesting {

    private DirectoryRepository directoryRepository;

    @Before
    public void setUp() {
        directoryRepository = Mockito.mock(DirectoryRepository.class);
    }


    @Test
    public void DirFind() {
        Directory directory = directoryRepository.findByFilepathAndDeleteflag("1",0);
        assertNotEquals("Durectory found",null,directory);
    }

    @Test
    public void DirCreation() {

        Directory directory = new Directory();
        directory.setParentId(-1);
        directory.setFilepath("1/");
        directory.setFilename("test");
        directory.setFile(true);
        directory.setCreatedBy(1);
        directory.setCreatedOn(new Date());

        directory = directoryRepository.save(directory);
        String dirid = String.valueOf(directory.getId());
        assertNotEquals("Directory Creted",Long.parseLong(dirid),0);
    }

    @Test
    public void DirectoryUpdation() {
        Directory directory = directoryRepository.findByFilepathAndDeleteflag("1",0);
        directory.setFile(true);
        directory = directoryRepository.save(directory);
        assertEquals("Directory Updated",true,directory.isFile());
    }

    @Test
    public void DirectoryDelete() {
        Directory directory = directoryRepository.findByFilepathAndDeleteflag("1",0);
        directoryRepository.deleteChildDir(String.valueOf(directory.getId()));
        List lst = directoryRepository.findByParentIdAndDeleteflag(directory.getId(),0);
        assertEquals("Directory Deleted",0,lst.size());
    }
}
