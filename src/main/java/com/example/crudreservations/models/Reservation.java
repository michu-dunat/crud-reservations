package com.example.crudreservations.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
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

    public Reservation(Date startOfRental, Date endOfRental, Customer landlord, Customer tenant, RentalPlace rentalPlace, Float cost) {
        this.startOfRental = startOfRental;
        this.endOfRental = endOfRental;
        this.landlord = landlord;
        this.tenant = tenant;
        this.rentalPlace = rentalPlace;
        this.cost = cost;
    }
}
