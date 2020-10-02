package com.ml.comcenter.repositories;

import com.ml.comcenter.entities.SatelliteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author elizcano
 */
@Repository
public interface SatelliteRepository extends JpaRepository<SatelliteEntity, Long> {

    /**
     * Método para recuperar objeto satelite de la base de datos
     *
     * @param name
     * @return
     */
    public Optional<SatelliteEntity> findBySatelliteName(String name);
}
