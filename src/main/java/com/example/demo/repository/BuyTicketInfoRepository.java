package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BuyTicketInfo;

import jakarta.transaction.Transactional;

@Repository
public interface BuyTicketInfoRepository extends JpaRepository<BuyTicketInfo, Integer>{
	@Modifying
    @Transactional
    @Query(value = "UPDATE BuyTicketInfo SET status = :status WHERE BuyTicketInfoId = :id;", nativeQuery = true)
    void updateStatusById(@Param("id") Integer id, @Param("status") String status);
}
