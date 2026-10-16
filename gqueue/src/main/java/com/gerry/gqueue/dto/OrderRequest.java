package com.gerry.gqueue.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class OrderRequest {
    
    @NotNull(message = "Item id is required") 
    private Long itemId;

    @NotNull(message = "User id is required")
    private Long userId;
}
