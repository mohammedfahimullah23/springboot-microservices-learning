package com.example.food_service.repository;


import com.example.food_service.model.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SeatRepository extends MongoRepository<Seat, String> {
}
