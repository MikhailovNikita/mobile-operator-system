package ru.tsystems.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.service.ContractService;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping({"","/"})
    public ResponseEntity<List<ContractDTO>> getContracts(){
        return new ResponseEntity<>(contractService.getAllContracts(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable long id){
        return new ResponseEntity<>(contractService.findContractById(id), HttpStatus.OK);
    }

    @PostMapping({"","/"})
    public ResponseEntity<String> createContract(@RequestBody ContractDTO contractDTO){
        contractService.addNewContract(contractDTO);
        return new ResponseEntity<>("Tariff was added", HttpStatus.OK);
    }
}
