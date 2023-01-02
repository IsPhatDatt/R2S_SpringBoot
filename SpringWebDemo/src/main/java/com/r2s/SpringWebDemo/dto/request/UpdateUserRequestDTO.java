package com.r2s.SpringWebDemo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDTO implements Serializable {

    @JsonProperty("firstNameUser")
    private String firstName;

    @JsonProperty("lastNameUser")
    private String lastName;

    private String username;
}
