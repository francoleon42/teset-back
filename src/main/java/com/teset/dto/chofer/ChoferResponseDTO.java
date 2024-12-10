package com.teset.dto.chofer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoferResponseDTO {
    Integer idChofer;
    String patente;
    String nombre;
    Integer idVehiculo;
    String estadoChofer;
}
