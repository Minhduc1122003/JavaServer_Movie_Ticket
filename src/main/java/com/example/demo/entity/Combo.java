package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ComBo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Combo {
	@Id
	@Column(name = "ComboID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int comboID;
	
	private int quantity;
	private double price;
	
	@OneToMany(mappedBy = "combo")
	@JsonManagedReference
	private List<BuyTicketInfo> buyTicketInfo;
}
