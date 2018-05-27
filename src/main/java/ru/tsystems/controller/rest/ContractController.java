package ru.tsystems.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.ContractDTO;
import ru.tsystems.service.ContractService;
import ru.tsystems.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<ContractDTO>> getContracts() {
        return new ResponseEntity<>(contractService.getAllContracts(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable long id) {
        return new ResponseEntity<>(contractService.findContractById(id), HttpStatus.OK);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<ContractDTO> getContractByNumber(@PathVariable String number) {
        return new ResponseEntity<>(contractService.findContractByNumber(number), HttpStatus.OK);
    }

    @GetMapping("/prefix/{pattern}")
    public ResponseEntity<List<ContractDTO>> getContractsByNumberPattern(@PathVariable String pattern) {
        return new ResponseEntity<>(contractService.findContractByNumberPattern(pattern), HttpStatus.OK);
    }


    @PostMapping({"", "/"})
    public ResponseEntity<String> createContract(@RequestBody ContractDTO contractDTO) {
        contractService.addNewContract(contractDTO);
        return new ResponseEntity<>("Contract was created", HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ContractDTO>> getAllPrincipalsContracts(Principal principal) {
        return new ResponseEntity<>(userService.getAllClientsContracts(principal.getName()), HttpStatus.OK);
    }
}
