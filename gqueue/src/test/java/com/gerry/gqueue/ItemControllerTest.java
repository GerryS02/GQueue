package com.gerry.gqueue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerry.gqueue.controller.ItemController;
import com.gerry.gqueue.dto.ItemRequest;
import com.gerry.gqueue.entity.Item;
import com.gerry.gqueue.service.ItemService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    
    @Autowired 
    private MockMvc mockMvc;

    @MockBean 
    private ItemService itemService;

    @Autowired 
    private ObjectMapper objectMapper;

    @Test 
    public void createItem_Success() throws Exception{

        //create the request
        ItemRequest request = new ItemRequest("PS5", 5, 499.9);

        //create expected service response
        Item sampleItem = new Item("Ps5", 5, 499.99);
        sampleItem.setId(1L);

        //mock the service
        when(itemService.createItem(any(ItemRequest.class))).thenReturn(sampleItem);

        //send POST request and assert the response
        mockMvc.perform(
            post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("PS5"));
    }

    @Test 
    public void createItem_InvalidInput() throws Exception{

        //create invalid request
        ItemRequest request = new ItemRequest("", 5, 499.9);

        mockMvc.perform(
            post("/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest());
    }
}
