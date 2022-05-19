package com.example.crudreservations.controllers;

import com.example.crudreservations.models.Reservation;
import com.example.crudreservations.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;

    @GetMapping(value = "/reservations/renal-place")
    public List<Reservation> getAllReservationsForGivenRentalObject(@RequestParam int id) {
        return reservationRepository.findAllByRentalPlaceId(id);
    }

    @GetMapping(value = "/reservations/tenants")
    public List<Reservation> getAllReservationsForGivenTenant(@RequestParam String name) {
        return reservationRepository.findAllByTenantName(name);
    }

}
