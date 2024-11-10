package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BuyTicket;

import jakarta.persistence.Tuple;

@Repository
public interface BuyTicketRepository extends JpaRepository<BuyTicket, Integer>{
	@Query(value = """
			SELECT m.title, bi.totalPrice, bi.Quantity, bi.createDate
			FROM BuyTicket b
			JOIN BuyTicketInfo bi ON b.BuyTicketId = bi.BuyTicketId
			JOIN Movies m ON b.MovieID = m.MovieID
			WHERE b.UserId = :userId
			ORDER BY bi.createDate DESC;
			""", nativeQuery = true)
	List<Tuple> getHistory(@Param("userId") Integer userId);
}