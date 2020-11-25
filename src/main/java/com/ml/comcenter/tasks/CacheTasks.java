package com.ml.comcenter.tasks;


import com.ml.comcenter.services.ConstanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheTasks {

    @Autowired
    private ConstanteService constanteService;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    private void limpiarCache(){

        constanteService.limpiarConstantes();
    }
}
