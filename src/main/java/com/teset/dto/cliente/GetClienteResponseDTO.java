package com.teset.dto.cliente;

import com.teset.utils.enums.EstadoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetClienteResponseDTO {

    String dni;
    String email;
    EstadoCliente estado;
}
