package com.teset.dto.cliente;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalleResponseDTO {
    Long id;
    String codCom;
    String fechaEmision;
    int secuencia;
    Double importe;

    String cuota;
    Double importePxVto;
    String fechadeProximoVencimiento;

}
