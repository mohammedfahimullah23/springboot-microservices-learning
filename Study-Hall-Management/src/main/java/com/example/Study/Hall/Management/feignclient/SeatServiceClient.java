package com.example.Study.Hall.Management.feignclient;

import com.example.Study.Hall.Management.feignclientdto.SeatDTO;
import com.example.Study.Hall.Management.wrappers.ResponseWrapper;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "seat")
public interface SeatServiceClient {

    @GetMapping("/seats/check-reserve/{seatId}")
    @Headers("Accept: application/json")
    ResponseEntity<ResponseWrapper<Boolean>> checkReservationStatus(@PathVariable String seatId);


    @GetMapping("/seats/reserve/{seatId}")
    ResponseWrapper<Object> reserveSeat(@PathVariable  String seatId);
}

