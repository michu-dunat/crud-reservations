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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final RentalPlaceRepository rentalPlaceRepository;

    public ResponseEntity<String> saveReservation(ReservationDTO reservationDTO, int reservationIdToBeUpdated) {
        if(checkIfTenantAndLandlordAreTheSamePerson(reservationDTO)) {
            return new ResponseEntity<>("Tenant and landlord are the same person", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(checkIfDatesAreValid(reservationDTO)) {
            return new ResponseEntity<>("Dates are not valid", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(reservationDTO.getCost() < 0) {
            return new ResponseEntity<>("Cost is negative", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(checkIfIdsOfCustomersAndRentalPlaceExist(reservationDTO)) {
            return new ResponseEntity<>("Tenant, landlord or rental place does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ArrayList<Reservation> reservations =
                new ArrayList<>(reservationRepository.getReservationsOfRentalPlaceWhereGivenDatesAreBetweenSavedDates(
                        reservationDTO.getRentalPlaceId(), reservationDTO.getStartOfRental(),
                        reservationDTO.getEndOfRental()));

        if(reservationIdToBeUpdated > 0) {
            if(reservations.size() > 1) {
                return new ResponseEntity<>("This place is already rented between given dates", HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (reservations.size() == 1 && reservations.get(0).getId() != reservationIdToBeUpdated) {
                    return new ResponseEntity<>("This place is already rented between given dates", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            if(reservations.size() > 0) {
                return new ResponseEntity<>("This place is already rented between given dates", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Reservation reservation = createReservation(reservationDTO);
        setReservationIdForUpdating(reservation, reservationIdToBeUpdated);

        try {
            reservationRepository.save(reservation);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while saving to database", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    private Reservation createReservation(ReservationDTO reservationDTO) {
        Customer tenant = new Customer(reservationDTO.getTenantId());
        Customer landlord = new Customer(reservationDTO.getLandlordId());
        RentalPlace rentalPlace = new RentalPlace(reservationDTO.getRentalPlaceId());

        return new Reservation(reservationDTO.getStartOfRental(), reservationDTO.getEndOfRental(),
                tenant, landlord, rentalPlace, reservationDTO.getCost());
    }

    private void setReservationIdForUpdating(Reservation reservation, int id) {
        if(id > 0) {
            reservation.setId(id);
        }
    }

    private boolean checkIfTenantAndLandlordAreTheSamePerson(ReservationDTO reservationDTO) {
        return reservationDTO.getLandlordId() == reservationDTO.getTenantId();
    }

    private boolean checkIfDatesAreValid(ReservationDTO reservationDTO) {
        return reservationDTO.getStartOfRental().after(reservationDTO.getEndOfRental()) ||
                reservationDTO.getStartOfRental().equals(reservationDTO.getEndOfRental());
    }

    private boolean checkIfIdsOfCustomersAndRentalPlaceExist(ReservationDTO reservationDTO) {
        return !customerRepository.existsById(reservationDTO.getLandlordId()) ||
                !customerRepository.existsById(reservationDTO.getTenantId()) ||
                !rentalPlaceRepository.existsById(reservationDTO.getRentalPlaceId());
    }

    public List<Reservation> getReservationList(int rentalPlaceId) {
        return reservationRepository.findAllByRentalPlaceId(rentalPlaceId);
    }

    public List<Reservation> getReservationList(String tenantName) {
        return reservationRepository.findAllByTenantName(tenantName);
    }

    public boolean deleteReservation(int id) {
        try {
            reservationRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
