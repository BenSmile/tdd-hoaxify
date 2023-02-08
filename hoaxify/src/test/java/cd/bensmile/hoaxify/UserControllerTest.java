package cd.bensmile.hoaxify;

import cd.bensmile.hoaxify.models.User;
import cd.bensmile.hoaxify.repositories.UserRepository;
import cd.bensmile.hoaxify.shared.GenericResponse;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    public static final String API_V_1_0_USERS = "/api/v1.0/users";

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    UserRepository userRepository;

    @Before
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void postUserWhenUserIsValidReceiveOk() {
        User user = createValidUser();
        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_V_1_0_USERS, user, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postUserWhenUserIsValidReceiveSuccessMessage() {
        User user = createValidUser();
        ResponseEntity<GenericResponse> response = testRestTemplate.postForEntity(API_V_1_0_USERS, user, GenericResponse.class);
        assertThat(response.getBody().getMessage()).isNotNull();
    }


    @Test
    public void postUserWhenUserIsValidPasswordHashedInDatabase() {
        User user = createValidUser();
        testRestTemplate.postForEntity(API_V_1_0_USERS, user, Object.class);
        List<User> all = userRepository.findAll();
        User userInDB = all.get(0);
        assertThat(userInDB.getPassword()).isNotEqualTo(user.getPassword());
    }

    @Test
    public void postUserWhenUserIsValidUserSavedToDatabase() {
        User user = createValidUser();
        testRestTemplate.postForEntity(API_V_1_0_USERS, user, Object.class);
        assertThat(userRepository.count()).isEqualTo(1);
    }

    private User createValidUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setDisplayName("test-display");
        user.setPassword("P4ssword");
        return user;
    }
}
