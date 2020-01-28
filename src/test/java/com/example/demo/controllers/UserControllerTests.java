package com.example.demo.controllers;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerTests {

    @InjectMocks
    UserController userController;

    @Mock
    UserRepository userRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void createUserWithSmallPasswordTest() {
        CreateUserRequest createUserRequest = new CreateUserRequest() {
            {
                setPassword("gundeep");
            }

            {
                setUsername("gundeep");
            }

            {
                setConfirmPassword("gundeep");
            }
        };
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    public void createUserWithConfirmMismatchPasswordTest() {
        CreateUserRequest createUserRequest = new CreateUserRequest() {
            {
                setPassword("gundeep333");
            }

            {
                setUsername("gundeep");
            }

            {
                setConfirmPassword("gundeep213");
            }
        };
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

    }

    @Test
    public void createUserHappyPathTest() {
        CreateUserRequest createUserRequest = new CreateUserRequest() {
            {
                setPassword("gundeep123");
            }

            {
                setUsername("gundeep");
            }

            {
                setConfirmPassword("gundeep123");
            }
        };
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    public void getUnknownUserWithUsernameTest() {

        ResponseEntity<User> responseEntity = userController.findByUserName("gundeep");
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getUserWithUsernameTest() {
        Mockito.when(userRepository.findByUsername("gundeep")).thenReturn(new User(){
            {setUsername("gundeep");}
        });
        ResponseEntity<User> responseEntity = userController.findByUserName("gundeep");
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
