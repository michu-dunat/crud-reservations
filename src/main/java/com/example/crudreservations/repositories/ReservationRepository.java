package com.example.crudreservations.repositories;

import com.example.crudreservations.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByRentalPlaceId(int id);
    List<Reservation> findAllByTenantName(String name);

    @Query(value = "SELECT * FROM reservation WHERE rental_place_id=?1 and " +
            "((?2 BETWEEN start_of_rental AND DATEADD(day, -1, end_of_rental)) OR " +
            "(?3 BETWEEN DATEADD(day, 1, start_of_rental) AND end_of_rental) )", nativeQuery = true)
    List<Reservation> getReservationsOfRentalPlaceWhereGivenDatesAreBetweenSavedDates(
            int renalPlaceId, java.sql.Date startDate, java.sql.Date endDate);

}
