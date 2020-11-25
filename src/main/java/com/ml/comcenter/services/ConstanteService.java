package com.ml.comcenter.services;

import com.ml.comcenter.entities.ConstanteEntity;
import com.ml.comcenter.repositories.ConstanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author elizcano
 */
@Service
public class ConstanteService {

    private Logger LOGGER = LoggerFactory.getLogger(ConstanteService.class);

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private ConstanteRepository constanteRepository;


    @Cacheable(cacheNames = "constantes", key = "#codigo")
    public ConstanteEntity getConstante(String codigo){
        return constanteRepository.findById(codigo).orElse(null);
    }

    @CachePut(cacheNames = "constantes", key = "#constante.codigo")
    public ConstanteEntity saveConstante(ConstanteEntity constante){
        return constanteRepository.save(constante);
    }

    public void limpiarConstantes(){
        LOGGER.info("Limpiando constantes");
        Objects.requireNonNull(cacheManager.getCache("constantes")).clear();
    }

}
