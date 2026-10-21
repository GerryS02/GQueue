package com.gerry.gqueue.dto;

import lombok.*;

@Data
@NoArgsConstructor 
@AllArgsConstructor 
public class OrderEvent {

    private Long orderId;
    private Long userId;
    private Long itemId;

}
