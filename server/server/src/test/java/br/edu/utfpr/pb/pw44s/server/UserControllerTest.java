package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;


@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@AutoConfigureTestRestTemplate
public class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    public void postUser_whenUserIsValid_receiveCREATED() {
        User user = User.builder()
                .username("test-user")
                .displayName("test-Display")
                .password("P4ssword").build();

        restTestClient
                .post()
                .uri("/users")
                .body(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody();
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDatabase() {

    }

    private User createValidUser(){
        return User.builder().username("test-user").displayName("test-Display").password("P4ssword").build();
    }


}
