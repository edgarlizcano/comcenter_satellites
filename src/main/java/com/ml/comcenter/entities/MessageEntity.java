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
@Table(name = "messages_tb")
@Data
@NoArgsConstructor
public class MessageEntity implements Serializable {

    @Id
    @Column(name = "messg_id")
    private String messageId;

    @Column(name = "messg_data")
    private String messageData;

    @Column(name = "messg_date")
    private Date messageDate;

    @Column(name = "messg_distance")
    private Double messageDistance;

    @ManyToOne
    @JoinColumn(name = "messg_idsatellite")
    private SatelliteEntity satellite;

}
