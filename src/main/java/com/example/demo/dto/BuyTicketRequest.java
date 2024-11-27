package com.example.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuyTicketRequest {

	@JsonProperty("buyTicketId")
    private String buyTicketId;  // Chứa BuyTicketId, kiểu dữ liệu VARCHAR(100)

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("movieId")
    private int movieId;
    
    @JsonProperty("totalPriceAll")
    private int totalPriceAll;

    @JsonProperty("showtimeId")
    private int showtimeId;

    @JsonProperty("seatIDs")
    private List<Integer> seatIDs;  // Danh sách các ghế ngồi

    @JsonProperty("comboIDs")
    private List<Integer> comboIDs;
}
