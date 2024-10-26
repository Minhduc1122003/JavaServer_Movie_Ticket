package com.example.demo.entity;

import java.time.LocalDateTime;

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
@Table(name = "BuyTicketInfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketInfo {
	@Id
	@Column(name = "BuyTicketInfoId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int buyTicketInfoId;
	
	@Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "CreateDate")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "TotalPrice")
    private double totalPrice;
    
    @ManyToOne
    @JoinColumn(name = "BuyTicketId", referencedColumnName = "BuyTicketId")
    @JsonBackReference(value = "buyticket-buyticketInfo")
    private BuyTicket buyticket;
    
    @ManyToOne
    @JoinColumn(name = "ComboID", referencedColumnName = "ComboID")
    @JsonBackReference(value = "combo-buyticketInfo")
    private Combo combo;
    
    @ManyToOne
    @JoinColumn(name = "ShowtimeID", referencedColumnName = "ShowtimeID")
    @JsonBackReference(value = "showtime-buyticketInfo")
    private Showtime showtime;
}
