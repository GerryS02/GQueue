package com.gerry.gqueue.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gerry.gqueue.entity.Item;

@DataJpaTest 
public class RetrieveItemTest {

    @Autowired 
    private ItemRepository itemRepository;
    private Long itemId;

    @BeforeEach 
    public void setup(){
        Item i = new Item("Tennis Shoes", 10, 29.99);
        itemRepository.save(i);
        this.itemId = i.getId();
    }

    @Test
    public void retrieveItem_Success(){
        Optional<Item> foundItem = itemRepository.findById(itemId);

        assertTrue(foundItem.isPresent());
        assertEquals("Tennis Shoes", foundItem.get().getName());
        assertEquals(10, foundItem.get().getStockQuantity());
        assertEquals(29.99, foundItem.get().getPrice());
    }
}
