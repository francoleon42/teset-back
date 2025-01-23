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
public class ClienteResponseWbDTO {
    String nombre;
    //    String email;
    BigDecimal disponible;
    BigDecimal saldoAPagar;
    BigDecimal importePxVto;


}
