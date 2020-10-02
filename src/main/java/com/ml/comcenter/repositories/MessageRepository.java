package com.ml.comcenter.repositories;

import com.ml.comcenter.entities.MessageEntity;
import com.ml.comcenter.entities.SatelliteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author elizcano
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {

    /**
     * Método para recuperar objeto satelite de la base de datos
     *
     * @param satellite
     * @return
     */
    public List<MessageEntity> findBySatelliteOrderByMessageDateDesc(SatelliteEntity satellite);
}
