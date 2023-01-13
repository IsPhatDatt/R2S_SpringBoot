package com.r2s.SpringWebDemo.dto.response;

import com.r2s.SpringWebDemo.entity.Address;
import com.r2s.SpringWebDemo.entity.Employer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressResponseDTO implements Serializable {

    private Employer userId;

    private Address addressId;

    private Boolean isDefaulted;
}
