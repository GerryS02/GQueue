package com.gerry.gqueue.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gerry.gqueue.entity.Item;

@DataJpaTest 
public class UpdateItemTest {
    
    @Autowired 
    private ItemRepository itemRepository;
    private Long itemId;

    @BeforeEach 
    public void setup(){
        Item i = new Item("Tennis shoes", 10, 29.99);
        itemRepository.save(i);
        this.itemId = i.getId();
    }

    @Test
    public void updateItem_Success(){
        Item i = itemRepository.findById(itemId).orElseThrow();

        //change stock and price
        i.setStockQuantity(25);
        i.setPrice(24.99);
        itemRepository.save(i);

        //verify updated item persisted
        Item updatedItem = itemRepository.findById(itemId).orElseThrow();

        assertEquals(25, updatedItem.getStockQuantity());
        assertEquals(24.99, updatedItem.getPrice());
    }
}
