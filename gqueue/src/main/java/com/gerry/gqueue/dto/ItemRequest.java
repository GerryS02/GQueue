package com.gerry.gqueue.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class ItemRequest {

    @NotNull(message = "Item name cannot be blank") 
    private String name;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQuantity;

    @Min(value = 0, message = "price cannot be negative")
    private double price;
}
