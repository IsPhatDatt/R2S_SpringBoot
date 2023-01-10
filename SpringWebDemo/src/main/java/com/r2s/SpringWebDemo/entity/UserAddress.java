package com.r2s.SpringWebDemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USER_ADDRESS")
public class UserAddress {

    //@EmbeddedId để đánh dấu khóa chính,
    // đây là một phiên bản của lớp UserAddressKey
    @EmbeddedId
    private UserAddressKey id;

    //@MapsId có nghĩa là chúng tôi liên kết các trường đó
    // với một phần của khóa và chúng là khóa ngoại của mối quan hệ nhiều-một.
    // Chúng tôi cần nó bởi vì, như chúng tôi đã đề cập,
    // chúng tôi không thể có các thực thể trong khóa tổng hợp.
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private Address address;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted;

    @Column(name = "IS_DEFAULTED")
    private Boolean isDefaulted;
}
