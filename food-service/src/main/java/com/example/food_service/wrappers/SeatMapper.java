package com.example.food_service.wrappers;


import com.example.food_service.dto.SeatDTO;
import com.example.food_service.model.Seat;

import java.util.Collections;
import java.util.List;

public class SeatMapper {

    // Convert Seat entity to SeatDTO
    public static SeatDTO toDTO(Seat seat) {
        if (seat == null) {
            return null;
        }
        return new SeatDTO(seat.getId(), seat.getStatus(),seat.getSeatName());
    }

    // Convert SeatDTO to Seat entity
    public static Seat toEntity(SeatDTO seatDTO) {
        if (seatDTO == null) {
            return null;
        }
        Seat seat = new Seat();
        seat.setId(seatDTO.getId());
        seat.setStatus(seatDTO.getStatus());
        return seat;
    }

    public static List<SeatDTO> toDTOList(List<Seat> seats) {

        return seats.stream().map(seat -> new SeatDTO(seat.getId(), seat.getStatus(),seat.getSeatName())).toList();
    }
}

