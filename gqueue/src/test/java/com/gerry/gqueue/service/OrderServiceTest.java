package com.gerry.gqueue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gerry.gqueue.dto.OrderRequest;
import com.gerry.gqueue.entity.Item;
import com.gerry.gqueue.entity.Order;
import com.gerry.gqueue.exception.ResourceNotFoundException;
import com.gerry.gqueue.kafka.OrderProducer;
import com.gerry.gqueue.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    private ItemRepository itemRepository;

    @Mock 
    private OrderProducer orderProducer;

    @InjectMocks 
    private OrderService orderService;

    //to check a valid order is created and a Kafka event is published
    @Test 
    public void createOrder_Success(){

        //create a sample iteam
        Item i = new Item("Notebook", 10, 4.99);
        i.setId(1L);

        //comment
        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));

        OrderRequest orderRequest = new OrderRequest(1L, 10L);

        //create order
        Order ord = orderService.createOrder(orderRequest);

        //verify the order and kafka event
        assertNotNull(ord);
        assertEquals(10L, ord.getUserId());
        assertEquals(1L, ord.getItemId());
        assertEquals("Pending", ord.getStatus());

        //comment -> verify that ...
        verify(orderProducer, times(1))
                .sendOrderEvent(any());
    }

    @Test 
    public void createOrder_ItemNotFound(){
        //requested item does not exist
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());

        OrderRequest req = new OrderRequest(99L, 10L);

        //throw exception
        assertThrows(ResourceNotFoundException.class, 
            ()-> orderService.createOrder(req));

        //no event should be published
        verify(orderProducer, never()).sendOrderEvent(any());
    }

    @Test
    public void createOrder_OutOfStock(){
        //item exists but has no stock
        Item i = new Item("Doll", 0, 9.99);
        i.setId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));

        OrderRequest req = new OrderRequest(1L, 10L);

        //out of order exception should be thrown
        IllegalStateException exception = assertThrows(
            IllegalStateException.class, 
            ()-> orderService.createOrder(req));

        assertEquals(
                "Requested Item is out of stock",
                exception.getMessage()
        );

        //no Kafka event should be published
        verify(orderProducer, never())
                .sendOrderEvent(any());

    }
}
