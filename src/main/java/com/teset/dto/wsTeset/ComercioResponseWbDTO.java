package com.teset.dto.wsTeset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComercioResponseWbDTO {
    @JsonProperty("Codcom")
    private String codcom;

    @JsonProperty("Cnombre")
    private String cnombre;

    @JsonProperty("Crubro")
    private String crubro;

    @JsonProperty("Cdomici")
    private String cdomici;

    @JsonProperty("Ctelcarac")
    private String ctelcarac;

    @JsonProperty("Ctelnumero")
    private String ctelnumero;

    @JsonProperty("Clocali")
    private String clocali;

    @JsonProperty("Urllogo")
    private String urllogo;

    @JsonProperty("Urlpagweb")
    private String urlpagweb;

    @JsonProperty("Urlgmaps")
    private String Urlgmaps;


}
