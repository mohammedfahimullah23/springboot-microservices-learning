package com.example.food_service.service;


import com.example.food_service.dto.SeatDTO;
import com.example.food_service.model.Seat;
import com.example.food_service.repository.SeatRepository;
import com.example.food_service.wrappers.SeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatDTO> getAllSeats() {
        List<Seat> seats = seatRepository.findAll();
        return SeatMapper.toDTOList(seats);
    }

    public SeatDTO getSeatById(String id) {
        Optional<Seat> seat = seatRepository.findById(id);
        return seat.map(SeatMapper::toDTO).orElse(null);
    }

    public boolean reserveSeat(String seatId) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isPresent() && seat.get().getStatus().equals("AVAILABLE")) {
            Seat updatedSeat = seat.get();
            updatedSeat.setStatus("RESERVED");
            seatRepository.save(updatedSeat);
            return true;
        }
        return false;
    }

    public boolean checkReservationStatus(String seatId) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        return seat.isEmpty() || !seat.get().getStatus().equals("AVAILABLE");
    }

    public boolean cancelReservation(String id) {
        Optional<Seat> seat = seatRepository.findById(id);
        if (seat.isPresent() && seat.get().getStatus().equals("RESERVED")) {
            Seat updatedSeat = seat.get();
            updatedSeat.setStatus("AVAILABLE");
            seatRepository.save(updatedSeat);
            return true;
        }
        return false;
    }
    public SeatDTO createSeat(SeatDTO seatDTO) {
        Seat seat = new Seat();
        seat.setStatus("AVAILABLE");
        seat.setSeatName(seatDTO.getSeatName());
        seat = seatRepository.save(seat); // Save to MongoDB
        return new SeatDTO(seat.getId(), seat.getStatus(),seat.getSeatName()); // Convert to DTO for response
    }

}

