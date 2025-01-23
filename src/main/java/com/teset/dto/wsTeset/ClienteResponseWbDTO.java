package com.teset.dto.wsTeset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseWbDTO {

    @JsonProperty("Nombre")
    private String nombre;

    @JsonProperty("Disponible")
    private Double disponible;

    @JsonProperty("SaldoAPagar")
    private Double saldoAPagar;

    @JsonProperty("ImportePxVto")
    private Double importePxVto;

}
