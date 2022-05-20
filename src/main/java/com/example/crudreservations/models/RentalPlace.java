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
public class RentalPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @NotNull
    private Float unitPrice;
    @NotNull
    private Float area;
    @NotNull
    @Column(length = 10000)
    private String description;

    @OneToMany(mappedBy = "rentalPlace")
    @ToString.Exclude
    @JsonIgnore
    private List<Reservation> reservations;

    public RentalPlace(int id) {
        this.id = id;
    }
}
