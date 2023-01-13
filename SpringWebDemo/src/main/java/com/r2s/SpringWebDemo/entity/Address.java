package com.r2s.SpringWebDemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "APARTMENT_NUMBER")
    private String apartmentNumber;

    @Column(name = "STREET")
    private String street;

    @Column(name = "WARD")
    private String ward;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "addressId")
    private Set<UserAddress> users;
}
