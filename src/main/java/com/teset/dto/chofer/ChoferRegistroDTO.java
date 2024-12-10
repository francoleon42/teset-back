package com.teset.dto.chofer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoferRegistroDTO {
    String username;
    String password;
    String nombre;
}
