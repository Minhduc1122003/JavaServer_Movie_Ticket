package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.HistoryBuyTicket;
import com.example.demo.repository.BuyTicketRepository;

import jakarta.persistence.Tuple;

@Service
public class BuyTicketService {
	
	@Autowired
	private BuyTicketRepository buyticketRepository;
	
	public List<HistoryBuyTicket> getAllHistory(int userId){
		List<Tuple> tuples = buyticketRepository.getHistory(userId);
		
		if(tuples.isEmpty()) {
			return null;
		}
		
		return tuples.stream().map(tuple -> {
			HistoryBuyTicket dto = new HistoryBuyTicket();
			dto.setTitle(tuple.get("title", String.class));
			dto.setTotalPrice(tuple.get("totalPrice", Double.class));
			dto.setQuantity(tuple.get("Quantity", Integer.class));
			dto.setCreateDate(tuple.get("createDate", Date.class));
			
			return dto;
		}).collect(Collectors.toList());
	}
}
