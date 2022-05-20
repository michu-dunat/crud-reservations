package com.example.crudreservations.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservationDTO {

    private Date startOfRental;
    private Date endOfRental;
    private int landlordId;
    private int tenantId;
    private int rentalPlaceId;
    private Float cost;

}
