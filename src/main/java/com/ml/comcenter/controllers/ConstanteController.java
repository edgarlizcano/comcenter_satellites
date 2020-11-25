package com.ml.comcenter.controllers;

import com.ml.comcenter.entities.ConstanteEntity;
import com.ml.comcenter.services.ConstanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author elizcano
 */
@RestController
@RequestMapping("constantes")
public class ConstanteController {

    @Autowired
    private ConstanteService constanteService;


    /**
     * @return
     */
    @GetMapping("info")
    public String info() {
        return "constantes";
    }

    @GetMapping("getConstante/{codigo}")
    public ResponseEntity<ConstanteEntity> getConstante(@PathVariable("codigo") String codigo){
        ConstanteEntity constanteEntity = constanteService.getConstante(codigo);
        return ResponseEntity.status(constanteEntity == null? HttpStatus.NO_CONTENT : HttpStatus.OK).body(constanteEntity);
    }

    @PostMapping("saveConstante")
    public ResponseEntity<ConstanteEntity> saveConstante(@RequestBody ConstanteEntity constante){
        ConstanteEntity constanteEntity = constanteService.saveConstante(constante);
        return ResponseEntity.status(constanteEntity == null? HttpStatus.NO_CONTENT : HttpStatus.CREATED).body(constanteEntity);
    }

    @GetMapping("limpiarConstantes")
    public ResponseEntity limpiarConstantes(){
        constanteService.limpiarConstantes();
        return ResponseEntity.ok().build();
    }

}
