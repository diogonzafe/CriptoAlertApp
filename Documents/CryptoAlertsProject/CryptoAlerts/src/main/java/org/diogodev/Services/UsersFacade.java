package org.diogodev.Services;

import org.apache.tomcat.jni.Local;
import org.apache.tomcat.jni.User;
import org.diogodev.Model.Users;
import org.diogodev.Model.UsersDTO;
import org.diogodev.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersFacade {

    @Autowired
    private UsersRepository repository;

    public UsersDTO criar(UsersDTO usersDTO) {
        Users users = new Users();
        users.setName(usersDTO.getName());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        users.setCreatedAt(LocalDateTime.now());
        repository.save(users);
        users.setId(usersDTO.getId());
        return usersDTO;
    }

    public UsersDTO atualizar (UsersDTO usersDTO,Long usersId) {
        Users usersData = repository.getOne(usersId);
        usersData.setName(usersDTO.getName());
        usersData.setEmail(usersDTO.getEmail());
        usersData.setPassword(usersDTO.getPassword());
        usersData.setUpdatedAt(LocalDateTime.now());
        repository.save(usersData);
        return usersDTO;
    }

    private UsersDTO converter (Users users){
        UsersDTO result = new UsersDTO();
        result.setId(users.getId());
        result.setName(users.getName());
        result.setEmail(users.getEmail());
        result.setPassword(users.getPassword());
        result.setCreatedAt(users.getCreatedAt());
        result.setUpdatedAt(users.getUpdatedAt());
        return result;
    }

    public List<UsersDTO> getAll(){
        return repository
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public String delete (Long usersId){
        repository.deleteById(usersId);
        return "DELETED";
    }


}
