package com.example.crudreservations.repositories;

import com.example.crudreservations.dtos.ReservationDTO;
import com.example.crudreservations.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByRentalPlaceId(int id);
    List<Reservation> findAllByTenantName(String name);

    @Query(value = "SELECT COUNT(id) FROM reservation WHERE rental_place_id=?1 and " +
            "((?2 BETWEEN start_of_rental AND DATEADD(day, -1, end_of_rental)) OR (?3 BETWEEN DATEADD(day, 1, start_of_rental) AND end_of_rental) )", nativeQuery = true)
    Integer check(int renalPlaceId, java.sql.Date startDate, java.sql.Date endDate);

}
