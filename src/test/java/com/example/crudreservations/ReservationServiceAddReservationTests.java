package com.example.crudreservations;

import com.example.crudreservations.dtos.ReservationDTO;
import com.example.crudreservations.models.Reservation;
import com.example.crudreservations.repositories.CustomerRepository;
import com.example.crudreservations.repositories.RentalPlaceRepository;
import com.example.crudreservations.repositories.ReservationRepository;
import com.example.crudreservations.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.Date;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class ReservationServiceAddReservationTests {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RentalPlaceRepository rentalPlaceRepository;

    ReservationDTO incomingReservation;
    ArrayList<Reservation> reservations;

    @BeforeEach
    void setUp() {
        incomingReservation = new ReservationDTO();
        incomingReservation.setLandlordId(1);
        incomingReservation.setTenantId(2);
        incomingReservation.setStartOfRental(Date.valueOf("2022-09-10"));
        incomingReservation.setEndOfRental(Date.valueOf("2022-10-10"));
        incomingReservation.setCost((float) 990.0);
        incomingReservation.setRentalPlaceId(1);

        reservations  = new ArrayList<>();

        when(rentalPlaceRepository.existsById(1)).thenReturn(true);
        when(customerRepository.existsById(1)).thenReturn(true);
        when(customerRepository.existsById(2)).thenReturn(true);
    }

    @Test
    void shouldReturn500IfTenantAndLandlordAreTheSamePerson() {
        incomingReservation.setLandlordId(1);
        incomingReservation.setTenantId(1);

        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("Tenant and landlord are the same person");
    }

    @Test
    void shouldReturn500IfStartDateIsAfterEndDate() {
        incomingReservation.setStartOfRental(Date.valueOf("2022-10-10"));
        incomingReservation.setEndOfRental(Date.valueOf("2022-09-10"));

        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("Dates are not valid");
    }

    @Test
    void shouldReturn500IfStartDatesAreEqual() {
        incomingReservation.setStartOfRental(Date.valueOf("2022-10-10"));
        incomingReservation.setEndOfRental(Date.valueOf("2022-10-10"));

        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("Dates are not valid");
    }

    @Test
    void shouldReturn500IfCostIsNegative() {
        incomingReservation.setCost((float) -990.0);

        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("Cost is negative");
    }

    @Test
    void shouldReturn500IfRentalPlaceDoesNotExist() {
        when(rentalPlaceRepository.existsById(1)).thenReturn(false);

        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("Tenant, landlord or rental place does not exist");
    }

    @Test
    void shouldReturn500IfTenantDoesNotExist() {
        when(customerRepository.existsById(2)).thenReturn(false);

        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("Tenant, landlord or rental place does not exist");
    }

    @Test
    void shouldReturn500IfLandlordDoesNotExist() {
        when(customerRepository.existsById(1)).thenReturn(false);

        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("Tenant, landlord or rental place does not exist");
    }

    @Test
    void shouldReturn500IfThereIsAlreadyReservationForGivenPlaceBetweenGivenDates() {
        reservations.add(new Reservation());

        when(reservationRepository.getReservationsOfRentalPlaceWhereGivenDatesAreBetweenSavedDates(
                        1, Date.valueOf("2022-09-10"), Date.valueOf("2022-10-10"))).thenReturn(reservations);
        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEqualTo("This place is already rented between given dates");
    }

    @Test
    void shouldReturn200IfEverythingIsAlright() {
        ResponseEntity<?> result = reservationService.saveReservation(incomingReservation, -1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo("OK");
    }

}
