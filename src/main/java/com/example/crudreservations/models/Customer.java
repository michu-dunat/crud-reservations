package com.example.crudreservations.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    @ToString.Exclude
    private List<Reservation> reservationT;
    @OneToMany(mappedBy = "landlord")
    @JsonIgnore
    @ToString.Exclude
    private List<Reservation> reservationL;

    public Customer(int id) {
        this.id = id;
    }
}
