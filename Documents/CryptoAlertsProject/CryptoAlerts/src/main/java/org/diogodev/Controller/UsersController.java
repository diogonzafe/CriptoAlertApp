package org.diogodev.Controller;

import org.diogodev.Model.ResponseDTO;
import org.diogodev.Model.UsersDTO;
import org.diogodev.Services.UsersFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

    @Autowired
    private UsersFacade usersFacade;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UsersDTO body) {
        try {
            ResponseDTO response = usersFacade.criar(body);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Error", ex.getMessage()));
        }
    }

    @PutMapping("/{usersId}")
    public ResponseEntity<UsersDTO> atualizar(@PathVariable("usersId") Long usersId,
                                              @RequestBody UsersDTO usersDTO) {
        try {
            UsersDTO updatedUser = usersFacade.atualizar(usersDTO, usersId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAll() {
        List<UsersDTO> users = usersFacade.getAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{usersId}")
    public ResponseEntity<String> deletar(@PathVariable("usersId") Long usersId) {
        try {
            String response = usersFacade.delete(usersId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Error deleting user");
        }
    }
}