package ru.tsystems.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.OptionDTO;
import ru.tsystems.service.OptionService;
import org.apache.commons.lang3.tuple.Pair;

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

    @GetMapping("/rules/require")
    public ResponseEntity<List<Pair<OptionDTO, OptionDTO>>> getRequiringRules(){
        return new ResponseEntity<>(optionService.getRequiringRules(), HttpStatus.OK);
    }

    @GetMapping("/rules/forbid")
    public ResponseEntity<List<Pair<OptionDTO, OptionDTO>>> getForbiddingRules(){
        return new ResponseEntity<>(optionService.getForbiddingRules(), HttpStatus.OK);
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
        optionService.addForbiddingOptions(id1, id2);
        return new ResponseEntity<>("Rule was added", HttpStatus.OK);
    }

    @DeleteMapping("/{id1}/forbid/{id2}")
    public ResponseEntity<String> deleteForbiddingRule(@PathVariable long id1, @PathVariable long id2){
        optionService.deleteForbiddingRule(id1, id2);
        return new ResponseEntity<>("Rule was deleted", HttpStatus.OK);
    }

    @PutMapping("/{id1}/require/{id2}")
    public ResponseEntity<String> addRequiringRule(@PathVariable long id1, @PathVariable long id2){
        optionService.addRequiredOption(id1, id2);
        return new ResponseEntity<>("Rule was added", HttpStatus.OK);
    }

    @DeleteMapping("/{id1}/require/{id2}")
    public ResponseEntity<String> deleteRequiringRule(@PathVariable long id1, @PathVariable long id2){
        optionService.deleteRequiringRule(id1, id2);
        return new ResponseEntity<>("Rule was deleted", HttpStatus.OK);
    }
}
