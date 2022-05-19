package com.example.crudreservations.repositories;

import com.example.crudreservations.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByRentalPlaceId(int id);
    List<Reservation> findAllByTenantName(String name);
}
