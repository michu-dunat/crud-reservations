package com.example.crudreservations.repositories;

import com.example.crudreservations.models.RentalPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalPlaceRepository extends JpaRepository<RentalPlace, Integer>  {
}
