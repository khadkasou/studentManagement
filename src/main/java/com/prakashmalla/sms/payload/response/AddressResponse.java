package com.prakashmalla.sms.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private Long id;
    private String city;
    private String street;
    private String state;
    private String zip;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
