package com.teset.dto.wsTeset;

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
public class ComercioResponseWbDTO {
    private String codcom;
    private String cnombre;
    private String cordench;
    private String crazonsoc;
    private String cdomici;
    private String clocali;
    private int ccodpos;
    private String ctelcarac;
    private String ctelnumero;
    private String ccodprov;
    private String cencarga;
    private String crubro;
    private String cimpcheq;
    private String cactivo;
    private String civatribut;
    private String civareten;
    private String ccodgan;
    private String cnrocuit;
    private String cjuringbr;
    private String cnroingbr;
    private BigDecimal cdescuen;
    private int cciclopag;
    private String ctipopag;
    private String ccodban;
    private String cctaban;
    private LocalDateTime cfechaing;
    private boolean cobratodo;
    private String excluyea;
}
