package com.example.crudreservations.controllers;

import com.example.crudreservations.dtos.ReservationDTO;
import com.example.crudreservations.models.Reservation;
import com.example.crudreservations.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/reservations/delete/{id}")
    public ResponseEntity<Integer> deleteReservation(@PathVariable int id) {
        try {
            reservationRepository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(500, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    @PostMapping("/reservations/add")
    public ResponseEntity<Integer> bookReservation(@RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

    @PutMapping("/reservations/update/{id}")
    public ResponseEntity<Integer> updateReservation(@PathVariable int id, @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(200, HttpStatus.OK);
    }

}
