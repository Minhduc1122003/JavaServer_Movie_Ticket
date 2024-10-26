package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BuyTicket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicket {
    @Id
    @Column(name = "BuyTicketId")
    private Integer buyTicketId;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "userId")
    @JsonBackReference(value = "user-buyticket")
    private User user;

    @ManyToOne
    @JoinColumn(name = "MovieID", referencedColumnName = "movieID")
    @JsonBackReference(value = "movie-buyticket")
    private Movie movie;
    
    @OneToMany(mappedBy = "buyticket")
    @JsonManagedReference(value = "buyticket-buyticketInfo")
    private List<BuyTicketInfo> buyTicketInfo;
    
    @OneToMany(mappedBy = "buyticket")
    @JsonManagedReference(value = "buyticket-ticketseat")
    private List<TicketSeat> ticketSeats;
}
