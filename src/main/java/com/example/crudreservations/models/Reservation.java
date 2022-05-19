package com.example.crudreservations.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private java.sql.Date startOfRental;
    @NotNull
    private java.sql.Date endOfRental;
    @ManyToOne
    @NotNull
    private Customer landlord;
    @ManyToOne
    @NotNull
    private Customer tenant;
    @ManyToOne
    @NotNull
    private RentalPlace rentalPlace;
    @NotNull
    private Float cost;

}
