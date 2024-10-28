package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TicketSeat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketSeat {
	@Id
	@Column(name = "TicketSeatID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ticketSeatID;
	
	@ManyToOne
	@JoinColumn(name = "BuyTicketId", referencedColumnName = "BuyTicketId")
	@JsonBackReference(value = "buyticket-ticketseat")
	private BuyTicket buyticket;
	
	@ManyToOne
	@JoinColumn(name = "SeatID", referencedColumnName = "SeatID")
	@JsonBackReference(value = "seat-ticketseat")
	private Seat seat;
}
