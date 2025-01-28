package com.teset.dto.cliente;

import com.teset.utils.enums.EstadoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    String nombre;
    Double saldoDisponible;
    Double saldoAPagar;
    Double importePxVto;
    String fechadeProximoVencimiento;
    String dni;
    String email;
    EstadoCliente estado;
}
