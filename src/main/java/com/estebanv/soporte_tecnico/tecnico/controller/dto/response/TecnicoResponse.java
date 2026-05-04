package com.estebanv.soporte_tecnico.tecnico.controller.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnicoResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private Boolean isActived;

}
