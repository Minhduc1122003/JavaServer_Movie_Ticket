package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuyTicketRequest {
	private int buyTicketId;
    private int userId;
    private int movieId;
    private int quantity;
    private float totalPrice;
    private int showtimeId;
    private List<Integer> seatIDs;
}
