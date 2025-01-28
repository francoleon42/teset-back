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
public class DetalleResponseWbDTO {

    @JsonProperty("FechaEmision")
    String fechaEmision;

    @JsonProperty("Secuencia")
    int secuencia;

    @JsonProperty("CODCOM")
    String codCom;

    @JsonProperty("Importe")
    Double importe;

    @JsonProperty("ImportePxVto")
    Double importePxVto;

    @JsonProperty("Cuota")
    String cuota;

    // add  fechadeProximoVencimiento
}
