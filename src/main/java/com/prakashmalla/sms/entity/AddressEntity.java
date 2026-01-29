package com.prakashmalla.sms.entity;

import com.prakashmalla.sms.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity extends BaseEntity {

    private String city;
    private String street;
    private String state;
    private String zip;
}
