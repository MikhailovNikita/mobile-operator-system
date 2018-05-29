package ru.tsystems.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.TariffDTO;
import ru.tsystems.service.TariffService;
import ru.tsystems.utils.JMSProducer;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
public class TariffController {
    @Autowired
    private TariffService tariffService;
    @Autowired
    private JMSProducer jmsProducer;

    @GetMapping({"", "/"})
    public ResponseEntity<List<TariffDTO>> getTariffs() {
        return new ResponseEntity<>(tariffService.getAllTariffs(), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<TariffDTO>> getActiveTariffs() {
        return new ResponseEntity<>(tariffService.getActiveTariffs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffDTO> getTariff(@PathVariable long id) {
        return new ResponseEntity<>(tariffService.get(id), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<String> createTariff(@RequestBody TariffDTO tariffDTO) {
        tariffService.addNewTariff(tariffDTO);
        return new ResponseEntity<>("Tariff was added", HttpStatus.OK);
    }

    @PutMapping({"", "/"})
    public ResponseEntity<String> updateTariff(@RequestBody TariffDTO tariffDTO){
        tariffService.updateOptions(tariffDTO);
        return new ResponseEntity<>("Tariff is updated", HttpStatus.OK);
    }

    @GetMapping("/hot")
    public ResponseEntity<List<TariffDTO>> getHotTariffs() {
        return new ResponseEntity<>(tariffService.getHotTariffs(), HttpStatus.OK);
    }

    @PutMapping("/hot/{id}")
    public ResponseEntity<String> changeHotStatus(@PathVariable long id) {
        tariffService.changeHotStatus(id);
        jmsProducer.sendMessage();
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }

    @PutMapping("/archive/{id}")
    public ResponseEntity<String> archiveTariff(@PathVariable long id) {
        tariffService.archive(id);
        jmsProducer.sendMessage();
        return new ResponseEntity<>("Success.", HttpStatus.OK);
    }


}
