package com.teset.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalleResponseDTO {
    String id;
    LocalDateTime fecha;
    String cuota;
    Double importe;
}
