package ru.tsystems.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.TariffDTO;
import ru.tsystems.service.TariffService;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
public class TariffController {
    @Autowired
    private TariffService tariffService;

    @GetMapping({"","/"})
    public ResponseEntity<List<TariffDTO>> getTariffs(){
        return new ResponseEntity<>(tariffService.getAllTariffs(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TariffDTO> getTariff(@PathVariable long id){
        return new ResponseEntity<>(tariffService.get(id), HttpStatus.OK);
    }

    @PostMapping({"","/"})
    public ResponseEntity<String> createTariff(@RequestBody TariffDTO tariffDTO){
        tariffService.addNewTariff(tariffDTO);
        return new ResponseEntity<>("Tariff was added", HttpStatus.OK);
    }

    @GetMapping("/hot")
    public ResponseEntity<List<TariffDTO>> getHotTariffs(){
        return new ResponseEntity<>(tariffService.getHotTariffs(), HttpStatus.OK);
    }


}
