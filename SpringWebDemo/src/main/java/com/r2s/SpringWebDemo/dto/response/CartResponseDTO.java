package com.r2s.SpringWebDemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO implements Serializable {

    private Integer id;

    private Date createdDate;

    private Double totalPrice;

    private List<CartItemResponseDTO> listCartLineItemResponseDTO;
}
