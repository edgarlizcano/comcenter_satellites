package com.ml.comcenter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author elizcano
 */
@Entity
@Table(name = "constantes")
@Data
@NoArgsConstructor
public class ConstanteEntity implements Serializable {

    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "valor")
    private String valor;

}
