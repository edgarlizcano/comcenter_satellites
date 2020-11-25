package com.ml.comcenter.repositories;

import com.ml.comcenter.entities.ConstanteEntity;
import com.ml.comcenter.entities.MessageEntity;
import com.ml.comcenter.entities.SatelliteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author elizcano
 */
@Repository
public interface ConstanteRepository extends JpaRepository<ConstanteEntity, String> {

}
