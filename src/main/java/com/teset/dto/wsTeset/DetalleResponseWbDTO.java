package com.teset.dto.wsTeset;

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

    LocalDate fechaEmision;
    int secuencia;
    String codCom;
    BigDecimal importe;
    BigDecimal importePxVto;
    String cuota;
}
