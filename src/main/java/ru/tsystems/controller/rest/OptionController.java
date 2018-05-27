package ru.tsystems.controller.rest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.service.OptionService;

import java.util.List;


@RestController
@RequestMapping("/api/options")
public class OptionController {
    @Autowired
    private OptionService optionService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<OptionDTO>> get(){
        return ResponseEntity.ok(optionService.getAllOptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionDTO> getOption(@PathVariable long id){
        return new ResponseEntity<>(optionService.getOptionById(id), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<String> post(@RequestBody OptionDTO optionDTO){
        optionService.addNewOption(optionDTO);
        return new ResponseEntity<>("Option was added", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOption(@PathVariable long id){
        optionService.deleteOptionById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id1}/forbid/{id2}")
    public ResponseEntity<String> addForbiddingRule(@PathVariable long id1, @PathVariable long id2){
        optionService.addForbiddingOptions(String.valueOf(id1), String.valueOf(id2));
        return new ResponseEntity<>("Rule was added", HttpStatus.OK);
    }

    @PutMapping("/{id1}/require/{id2}")
    public ResponseEntity<String> addRequiringRule(@PathVariable long id1, @PathVariable long id2){
        optionService.addRequiredOption(String.valueOf(id1), String.valueOf(id2));
        return new ResponseEntity<>("Rule was added", HttpStatus.OK);
    }

}
