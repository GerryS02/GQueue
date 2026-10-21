package com.gerry.gqueue.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.gerry.gqueue.dto.OrderEvent;
import com.gerry.gqueue.entity.Item;
import com.gerry.gqueue.entity.Order;
import com.gerry.gqueue.repository.ItemRepository;
import com.gerry.gqueue.repository.OrderRepository;

@Service
public class OrderConsumer {
    
    //repositories to retrieve and update items and orders in the database
    private final ItemRepository itemRepository;
    private OrderRepository orderRepository;
    
    public OrderConsumer(ItemRepository itemRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "order-events", groupId = "gqueue-group")
    public void consumerOrderEvent(OrderEvent event){
        System.out.println("Received Kafka Event for Order Id: " + event.getOrderId());

        //extract the order and item IDs from the received Kafka event
        Long orderId = event.getOrderId();
        Long itemId = event.getItemId();

        //retrieve the order from the database.
        //throw an exception if no order exists with the provided ID.
        Order o = orderRepository.findById(orderId)
                    .orElseThrow(()-> new RuntimeException("Order not found"));

        //retrieve the item associated with this order.
        //throw an exception if the item does not exist.
        Item i = itemRepository.findById(itemId)
                    .orElseThrow(()-> new RuntimeException("Item not found"));
        
        //get item's current staock quantity
        int stock = i.getStockQuantity();

        //process stock deduction
        if(stock > 0){
            stock--;

            //update the item entity and save the new stock quantity
            i.setStockQuantity(stock);
            itemRepository.save(i);

            //mark the order as Confirmed and persist the updated status.
            o.setStatus("Confirmed");
            orderRepository.save(o);

            System.out.println("Order " + orderId + " Confirmed. Remaining stock: " + stock);
        } else{
            //if no stock is available, mark the order as failed
            o.setStatus("Failed");
            orderRepository.save(o);

            System.out.println("Order " + orderId + " Failed. Out of stock");
        }
    }
    
}
