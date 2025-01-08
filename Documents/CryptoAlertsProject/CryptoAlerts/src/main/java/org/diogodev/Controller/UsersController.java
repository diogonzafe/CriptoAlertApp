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

    /**
     *To-DO
     * Auth-login controller and config all
     */


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
    @ResponseBody
    public UsersDTO atualizar(@PathVariable("usersId")Long usersId,
                              @RequestBody UsersDTO usersDTO){
        return usersFacade.atualizar(usersDTO, usersId);
    }

    @GetMapping
    @ResponseBody
    public List<UsersDTO> getAll() {
        return usersFacade.getAll();
    }

    @DeleteMapping("/{usersId}")
    @ResponseBody
    public String deletar(@PathVariable("usersId")Long usersId){
        return usersFacade.delete(usersId);
    }
}
