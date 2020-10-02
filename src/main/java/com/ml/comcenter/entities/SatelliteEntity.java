package com.ml.comcenter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author elizcano
 */
@Entity
@Table(name = "satellites_tb")
@Data
@NoArgsConstructor
public class SatelliteEntity implements Serializable {

    @Id
    @Column(name = "sat_id")
    private Long satelliteId;

    @Column(name = "sat_name")
    private String satelliteName;

    @Column(name = "sat_posx")
    private Double positionX;

    @Column(name = "sat_posy")
    private Double positionY;
}
