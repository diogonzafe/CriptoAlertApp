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

    public UsersDTO atualizar(UsersDTO usersDTO, Long usersId) {
        Users usersData = repository.getOne(usersId);
        usersData.setName(usersDTO.getName());
        usersData.setEmail(usersDTO.getEmail());
        usersData.setPassword(usersDTO.getPassword());
        usersData.setUpdatedAt(LocalDateTime.now());
        return usersDTO;
    }

    private UsersDTO converter (Users users){
        UsersDTO result = new UsersDTO();
        users.setName(users.getName());
        users.setEmail(users.getEmail());
        users.setPassword(users.getPassword());
        users.setCreatedAt(users.getCreatedAt());
        users.setUpdatedAt(users.getUpdatedAt());
        return result;
    }

    public List<UsersDTO> getAll(){
        return repository
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public String delete (Long userId){
        repository.deleteById(userId);
        return "DELETED";
    }


}
