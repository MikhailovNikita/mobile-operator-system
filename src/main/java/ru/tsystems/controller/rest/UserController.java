package ru.tsystems.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tsystems.dto.UserDTO;
import ru.tsystems.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/clients")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<UserDTO>> getAllClients(){
        return new ResponseEntity<>(userService.getAllClients(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getAllClients(@PathVariable long id){
        return new ResponseEntity<>(userService.getClientById(id), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<String> createClient(@RequestBody UserDTO userDTO){
        userService.registerUser(userDTO);
        return new ResponseEntity<>("Client successfully registered", HttpStatus.OK);
    }

}
