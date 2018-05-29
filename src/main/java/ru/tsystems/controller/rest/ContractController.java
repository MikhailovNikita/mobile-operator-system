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

    @PutMapping("/{cid}/tariff/{tid}")
    public ResponseEntity<String> changeTariff(@PathVariable long cid, @PathVariable long tid){
        contractService.changeTariff(cid, tid);
        return new ResponseEntity<>("Tariff was changed", HttpStatus.OK);
    }

    @PutMapping("/{cid}/option/enable/{oid}")
    public ResponseEntity<String> enableOption(@PathVariable long cid, @PathVariable long oid){
        contractService.enableOption(cid, oid);
        return new ResponseEntity<>("Option was enabled", HttpStatus.OK);
    }

    @PutMapping("/{cid}/option/disable/{oid}")
    public ResponseEntity<String> disableOption(@PathVariable long cid, @PathVariable long oid){
        contractService.disableOption(cid, oid);
        return new ResponseEntity<>("Option was disabled", HttpStatus.OK);
    }

    @PutMapping("/user/block/{cid}")
    public ResponseEntity<String> userBlock(@PathVariable long cid){
        contractService.userBlocksContract(cid);
        return new ResponseEntity<>("Contract is successfully blocked", HttpStatus.OK);
    }

    @PutMapping("/user/unblock/{cid}")
    public ResponseEntity<String> userUnblock(@PathVariable long cid){
        contractService.userUnblocksContract(cid);
        return new ResponseEntity<>("Contract is successfully unblocked", HttpStatus.OK);
    }

    @PutMapping("/admin/block/{cid}")
    public ResponseEntity<String> adminBlock(@PathVariable long cid){
        contractService.adminBlocksContract(cid);
        return new ResponseEntity<>("Contract is successfully blocked", HttpStatus.OK);
    }

    @PutMapping("/admin/unblock/{cid}")
    public ResponseEntity<String> adminUnblock(@PathVariable long cid){
        contractService.adminUnblocksContract(cid);
        return new ResponseEntity<>("Contract is successfully unblocked", HttpStatus.OK);
    }

}
