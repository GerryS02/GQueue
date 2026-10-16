package com.gerry.gqueue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gerry.gqueue.dto.ItemRequest;
import com.gerry.gqueue.entity.Item;
import com.gerry.gqueue.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ItemService {
    private final ItemRepository itemRepository;

    public Item createItem(ItemRequest request){
        Item i = new Item(request.getName(),
                            request.getStockQuantity(),
                            request.getPrice());

        return itemRepository.save(i);
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Item getItemById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }
}
