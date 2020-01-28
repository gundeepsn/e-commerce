package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CartControllerTests {

    @InjectMocks
    CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void addToCartForUnknownUserTest() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest() {
            {
                setItemId(1);
            }

            {
                setQuantity(2);
            }

            {
                setUsername("gundeep");
            }
        };

        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void addToCartUnknownItemTest() {
        Mockito.when(userRepository.findByUsername("gundeep")).thenReturn(new User() {
            {
                setUsername("gundeep");
            }
        });
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest() {
            {
                setItemId(1);
            }

            {
                setQuantity(2);
            }

            {
                setUsername("gundeep");
            }
        };

        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void addToCartHappyPathTest() {
        Mockito.when(userRepository.findByUsername("gundeep")).thenReturn(new User() {
            {
                setUsername("gundeep");
                setCart(new Cart());
            }
        });

        Mockito.when(itemRepository.findById(1l)).thenReturn(java.util.Optional.of(new Item() {
            {
                setId(1l);
            }

            {
                setPrice(BigDecimal.valueOf(2.99));
            }

            {
                setName("Round Widget");
            }
        }));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest() {
            {
                setItemId(1);
            }

            {
                setQuantity(2);
            }

            {
                setUsername("gundeep");
            }
        };

        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void removeFromCartForUnknownUserTest() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest() {
            {
                setItemId(1);
            }

            {
                setQuantity(2);
            }

            {
                setUsername("gundeep");
            }
        };

        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void removeFromCartUnknownItemTest() {
        Mockito.when(userRepository.findByUsername("gundeep")).thenReturn(new User() {
            {
                setUsername("gundeep");
            }
        });
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest() {
            {
                setItemId(1);
            }

            {
                setQuantity(2);
            }

            {
                setUsername("gundeep");
            }
        };

        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void removeFromCartHappyPathTest() {
        Mockito.when(userRepository.findByUsername("gundeep")).thenReturn(new User() {
            {
                setUsername("gundeep");
                setCart(new Cart());
            }
        });

        Mockito.when(itemRepository.findById(1l)).thenReturn(java.util.Optional.of(new Item() {
            {
                setId(1l);
            }

            {
                setPrice(BigDecimal.valueOf(2.99));
            }

            {
                setName("Round Widget");
            }
        }));
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest() {
            {
                setItemId(1);
            }

            {
                setQuantity(2);
            }

            {
                setUsername("gundeep");
            }
        };

        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}
