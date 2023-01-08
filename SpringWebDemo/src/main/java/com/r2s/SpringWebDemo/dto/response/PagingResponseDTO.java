package com.r2s.SpringWebDemo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponseDTO implements Serializable {

    private Integer page;

    private Integer size;

    private String sort;

    @JsonProperty("data-list")
    private List<?> responseObjectList;
}
