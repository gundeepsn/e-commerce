package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderControllerTests {
    @InjectMocks
    OrderController orderController;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void submitOrderUnknownUserTest() {
        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit("gundeep");
        assertNull(orderResponseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderResponseEntity.getStatusCode());
    }

    @Test
    public void submitOrderHappyPathTest() {
        Mockito.when(userRepository.findByUsername("gundeep")).thenReturn(new User() {
            {
                setUsername("gundeep");
                setCart(new Cart() {
                    {
                        setItems(new ArrayList<Item>());
                    }
                });
            }
        });
        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit("gundeep");
        assertNotNull(orderResponseEntity.getBody());
        assertEquals(HttpStatus.OK, orderResponseEntity.getStatusCode());
    }

    @Test
    public void orderHistoryUnknownUserTest() {
        ResponseEntity<List<UserOrder>> orderResponseEntity = orderController.getOrdersForUser("gundeep");
        assertNull(orderResponseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderResponseEntity.getStatusCode());
    }

    @Test
    public void orderHistoryHappyPathTest() {
        User testUser = new User() {
            {
                setUsername("gundeep");
                setCart(new Cart() {
                    {
                        setItems(new ArrayList<Item>());
                    }
                });
            }
        };

        Mockito.when(userRepository.findByUsername("gundeep")).thenReturn(testUser);

        Mockito.when(orderRepository.findByUser(testUser)).thenReturn(new ArrayList<UserOrder>());

        ResponseEntity<List<UserOrder>> orderResponseEntity = orderController.getOrdersForUser("gundeep");
        assertNotNull(orderResponseEntity.getBody());
        assertEquals(HttpStatus.OK, orderResponseEntity.getStatusCode());
    }
}
