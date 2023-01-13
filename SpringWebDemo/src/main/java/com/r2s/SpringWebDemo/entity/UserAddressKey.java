package com.r2s.SpringWebDemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

//Chúng ta phải đánh dấu nó bằng @Embeddable .
//Nó phải triển khai java.io.Serializable .
//Chúng ta cần cung cấp triển khai các phương thức hashcode() và equals() .
//Không có trường nào có thể là một thực thể.
@Embeddable
public class UserAddressKey implements Serializable {

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "ADDRESS_ID")
    private Integer addressId;
}
