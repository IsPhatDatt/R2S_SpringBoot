package com.r2s.SpringWebDemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderResponseDTO implements Serializable {

    private Integer id;

    private Double transportationFee;

    private Double totalPrice;
}
