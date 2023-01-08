package com.r2s.SpringWebDemo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserListResponseDTO implements Serializable {

    @JsonProperty("data-list")
    private List<UpdateUserResponseDTO> responseDTOList;
}
