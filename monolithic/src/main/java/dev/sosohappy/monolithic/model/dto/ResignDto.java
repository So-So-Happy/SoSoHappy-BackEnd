package dev.sosohappy.monolithic.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResignDto {

    private String email;
    private Boolean success;

}
