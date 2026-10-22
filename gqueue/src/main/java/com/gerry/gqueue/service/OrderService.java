package com.gerry.gqueue.service;
import org.springframework.stereotype.Service;

import com.gerry.gqueue.dto.OrderEvent;
import com.gerry.gqueue.dto.OrderRequest;
import com.gerry.gqueue.entity.Item;
import com.gerry.gqueue.entity.Order;
import com.gerry.gqueue.exception.ResourceNotFoundException;
import com.gerry.gqueue.kafka.OrderProducer;
import com.gerry.gqueue.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class OrderService {
    
    private final ItemRepository itemRepository;
    private final OrderProducer orderProducer;  //inject producer

    public Order createOrder(OrderRequest request){
        //check if the item exists
        Item i = itemRepository.findById(request.getItemId())
            .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        //check stock availability
        if(i.getStockQuantity() <= 0){
            throw new IllegalStateException("Item " + i.getName() + " is out of stock");
        }

        //save initial order as pending
        Order o = new Order(request.getUserId(), request.getItemId(), "Pending");
        
        //emit event to kafka asynchronously
        OrderEvent event = new OrderEvent(o.getId(), o.getUserId(), o.getItemId());
        orderProducer.sendOrderEvent(event);

        return o;
    }
    
}
