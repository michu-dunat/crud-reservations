package com.example.crudreservations.services;

import com.example.crudreservations.dtos.ReservationDTO;
import com.example.crudreservations.models.Customer;
import com.example.crudreservations.models.RentalPlace;
import com.example.crudreservations.models.Reservation;
import com.example.crudreservations.repositories.CustomerRepository;
import com.example.crudreservations.repositories.RentalPlaceRepository;
import com.example.crudreservations.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RentalPlaceRepository rentalPlaceRepository;

    public ResponseEntity<Integer> makeReservation(ReservationDTO reservationDTO) {
        if(reservationDTO.getLandlordId() == reservationDTO.getTenantId()) {
            return new ResponseEntity<>(500, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(reservationDTO.getStartOfRental().after(reservationDTO.getEndOfRental()) || reservationDTO.getStartOfRental().equals(reservationDTO.getEndOfRental())) {
            return new ResponseEntity<>(500, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(reservationDTO.getCost() < 0) {
            return new ResponseEntity<>(500, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(!customerRepository.existsById(reservationDTO.getLandlordId()) ||
                !customerRepository.existsById(reservationDTO.getTenantId()) ||
        !rentalPlaceRepository.existsById(reservationDTO.getRentalPlaceId())) {
            return new ResponseEntity<>(500, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(reservationRepository.check(reservationDTO.getRentalPlaceId(), reservationDTO.getStartOfRental(), reservationDTO.getEndOfRental()) > 0) {
            return new ResponseEntity<>(500, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Customer tenant = new Customer(reservationDTO.getTenantId());
        Customer landlord = new Customer(reservationDTO.getLandlordId());
        RentalPlace rentalPlace = new RentalPlace(reservationDTO.getRentalPlaceId());

        Reservation reservation = new Reservation(reservationDTO.getStartOfRental(), reservationDTO.getEndOfRental(),
                tenant, landlord, rentalPlace, reservationDTO.getCost());

        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            return new ResponseEntity<>(500, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(200, HttpStatus.OK);
    }

}
