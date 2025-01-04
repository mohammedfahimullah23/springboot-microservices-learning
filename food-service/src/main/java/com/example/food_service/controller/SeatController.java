package com.example.food_service.controller;


import com.example.food_service.dto.SeatDTO;
import com.example.food_service.exception.SeatNotFoundException;
import com.example.food_service.service.SeatService;
import com.example.food_service.wrappers.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<?>> getAllSeats() {
        List<SeatDTO> seats = seatService.getAllSeats();
        ResponseWrapper<List<SeatDTO>> successResponse = new ResponseWrapper<>(true, seats);
        return ResponseEntity.ok(successResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> getSeatById(@PathVariable String id) {
        SeatDTO seat = seatService.getSeatById(id);
        ResponseWrapper<SeatDTO> successResponse = new ResponseWrapper<>(true, seat);
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<?>> createSeat(@RequestBody SeatDTO seatDTO) {
        SeatDTO createdSeat = seatService.createSeat(seatDTO);
        ResponseWrapper<SeatDTO> successResponse = new ResponseWrapper<>(true, createdSeat);
        return ResponseEntity.status(201).body(successResponse);
    }

    @GetMapping("/reserve/{seatId}")
    public ResponseEntity<ResponseWrapper<String>> reserveSeat(@PathVariable String seatId) {
        boolean isReserved = seatService.reserveSeat(seatId);
        if (isReserved) {
            ResponseWrapper<String> successResponse = new ResponseWrapper<>(true, "Seat reserved successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        }
        ResponseWrapper<String> failureResponse = new ResponseWrapper<>(false, "Seat is already reserved.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failureResponse);
    }

    @GetMapping("/check-reserve/{seatId}")
    public ResponseEntity<ResponseWrapper<Boolean>> checkReservationStatus(@PathVariable String seatId) {
        try {
            boolean isReserved = seatService.checkReservationStatus(seatId);
            ResponseWrapper<Boolean> response = new ResponseWrapper<>(isReserved);
            return ResponseEntity.ok(response);
        } catch (SeatNotFoundException e) {
            ResponseWrapper<Boolean> errorResponse = new ResponseWrapper<>(false, List.of("Seat not found: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            ResponseWrapper<Boolean> errorResponse = new ResponseWrapper<>(false,List.of("Seat not found: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PutMapping("/cancel/{id}")
    public ResponseEntity<ResponseWrapper<?>> cancelReservation(@PathVariable String id) {
        boolean isCancelled = seatService.cancelReservation(id);
        if (isCancelled) {
            ResponseWrapper<String> successResponse = new ResponseWrapper<>(true, "Reservation cancelled.");
            return ResponseEntity.ok(successResponse);
        }
        ResponseWrapper<String> failureResponse = new ResponseWrapper<>(false, "Reservation not found.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failureResponse);
    }
}

