package com.r2s.SpringWebDemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressRequestDTO implements Serializable {

    private String apartmentNumber;

    private String street;

    private String ward;

    private String district;

    private String province;
}
