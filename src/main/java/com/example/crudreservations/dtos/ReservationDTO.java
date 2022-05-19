package com.example.crudreservations.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservationDTO {

    private java.sql.Date startOfRental;
    private java.sql.Date endOfRental;
    private int landlordId;
    private int tenantId;
    private int rentalPlaceId;
    private Float cost;

}
