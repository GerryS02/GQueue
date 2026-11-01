package com.gerry.gqueue.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gerry.gqueue.entity.Item;

@DataJpaTest 
public class AddItemTest {
    
    @Autowired 
    private ItemRepository itemRepository;

    @Test
    public void addItem_Success(){
        Item i = new Item("Tennis Shoes", 10, 29.99);

        Item saved = itemRepository.save(i);

        assertNotNull(saved.getId());
        assertEquals("Tennis Shoes", saved.getName());
        assertEquals(10, saved.getStockQuantity());
        assertEquals(29.99, saved.getPrice());
    }
}
