package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.BuyTicketRequest;
import com.example.demo.dto.HistoryBuyTicket;
import com.example.demo.service.BuyTicketService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/buyticket")
public class BuyTicketController {

    private static final Logger logger = LoggerFactory.getLogger(BuyTicketController.class);

    @Autowired
    private BuyTicketService buyTicketService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/getHistory/{id}")
    public List<HistoryBuyTicket> getHistory(@PathVariable int id) {
        return buyTicketService.getAllHistory(id);
    }
    
    @PostMapping("/createBuyTicket")
    public ResponseEntity<String> insertBuyTicket(@RequestBody BuyTicketRequest buyTicketRequest) {
        Logger logger = LoggerFactory.getLogger(BuyTicketController.class);
        logger.info("Received BuyTicketRequest: {}", buyTicketRequest);
        
        try {
            // Thêm kiểm tra hợp lệ dữ liệu từ buyTicketRequest
            if (buyTicketRequest.getUserId() <= 0 || buyTicketRequest.getMovieId() <= 0
                    || buyTicketRequest.getQuantity() <= 0 || buyTicketRequest.getShowtimeId() <= 0) {
                return ResponseEntity.badRequest().body("UserId, MovieId, Quantity và ShowtimeId phải là số dương.");
            }
            if (buyTicketRequest.getSeatIDs() == null || buyTicketRequest.getSeatIDs().isEmpty()) {
                return ResponseEntity.badRequest().body("Danh sách SeatIDs không được rỗng.");
            }
            
            // Lấy dữ liệu từ RequestBody
            int userId = buyTicketRequest.getUserId();
            int movieId = buyTicketRequest.getMovieId();
            int quantity = buyTicketRequest.getQuantity();
            double totalPrice = buyTicketRequest.getTotalPrice();
            int showtimeId = buyTicketRequest.getShowtimeId();
            List<Integer> seatIDs = buyTicketRequest.getSeatIDs();
            
            // Chuyển danh sách seatIDs thành chuỗi
            String seatIDsString = seatIDs.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
            
            // Ghi log các tham số chuẩn bị cho stored procedure
            logger.info("Inserting ticket: userId={}, movieId={}, quantity={}, totalPrice={}, showtimeId={}, seatIDs={} ",
                    userId, movieId, quantity, totalPrice, showtimeId, seatIDsString);
            
            // Gọi stored procedure
            jdbcTemplate.update("EXEC InsertBuyTicket ?, ?, ?, ?, ?, ?", userId, movieId, quantity, totalPrice, showtimeId, seatIDsString);
            
            logger.info("Insert success!");
            return ResponseEntity.ok("Insert success!");
            
        } catch (DataAccessException dae) {
            logger.error("Database error: ", dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error: " + dae.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

}