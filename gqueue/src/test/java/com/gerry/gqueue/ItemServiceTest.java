package com.gerry.gqueue;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gerry.gqueue.entity.Item;
import com.gerry.gqueue.exception.ResourceNotFoundException;
import com.gerry.gqueue.repository.ItemRepository;
import com.gerry.gqueue.service.ItemService;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    
    @Mock 
    private ItemRepository itemRepository;

    @InjectMocks 
    private ItemService itemService;

    @Test
    public void getItemById_Success(){

        //create a dummy item
        Item i1 = new Item("phone", 1, 999.99);
        i1.setId(1L);

        //configure the mock repository
        when(itemRepository.findById(1L)).thenReturn(Optional.of(i1));

        //call the service method
        Item result = itemService.getItemById(1L);

        //check the returned item
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("phone", result.getName());
        Assertions.assertEquals(1, result.getStockQuantity());
        Assertions.assertEquals(999.99, result.getPrice(), 0.001);
        
    }

    //if item doesn't exist
    @Test 
    public void getItemById_ThrowResourceNotFoundException(){

        //return empty optional if item doesn't exist
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
            ResourceNotFoundException.class,
            () -> itemService.getItemById(99L)
        );
    }
}
