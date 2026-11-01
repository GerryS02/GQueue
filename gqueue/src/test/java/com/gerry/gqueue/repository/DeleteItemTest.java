package com.gerry.gqueue.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import com.gerry.gqueue.entity.Item;

@DataJpaTest 
public class DeleteItemTest {
    
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
    public void deleteItem_Success(){
        itemRepository.deleteById(itemId);

        Optional<Item> deleted = itemRepository.findById(itemId);
        assertFalse(deleted.isPresent());
    }

}
