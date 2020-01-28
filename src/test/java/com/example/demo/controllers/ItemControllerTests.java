package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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
public class ItemControllerTests {

    @InjectMocks
    ItemController itemController;

    @Mock
    ItemRepository itemRepository;

    @Test
    public void getItemUnknownById() {
        ResponseEntity<Item> itemResponseEntity = itemController.getItemById(1l);
        assertNull(itemResponseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, itemResponseEntity.getStatusCode());
    }

    @Test
    public void getItemHappyPathById() {
        Mockito.when(itemRepository.findById(1l)).thenReturn(java.util.Optional.of(new Item()));
        ResponseEntity<Item> itemResponseEntity = itemController.getItemById(1l);
        assertNotNull(itemResponseEntity.getBody());
        assertEquals(HttpStatus.OK, itemResponseEntity.getStatusCode());
    }

    @Test
    public void getItemUnknownByName() {
        ResponseEntity<List<Item>> itemResponseEntity = itemController.getItemsByName("item1");
        assertNull(itemResponseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, itemResponseEntity.getStatusCode());
    }

    @Test
    public void getItemHappyPathByName() {
        Mockito.when(itemRepository.findByName("item1")).thenReturn(new ArrayList<Item>() {
            {
                add(new Item() {
                    {
                        setName("item1");
                    }
                });
            }
        });
        ResponseEntity<List<Item>> itemResponseEntity = itemController.getItemsByName("item1");
        assertNotNull(itemResponseEntity.getBody());
        assertEquals(HttpStatus.OK, itemResponseEntity.getStatusCode());
    }
}
