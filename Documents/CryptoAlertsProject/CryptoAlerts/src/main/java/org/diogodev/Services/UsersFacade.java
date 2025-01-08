package org.diogodev.Services;

import org.diogodev.Model.ResponseDTO;
import org.diogodev.Model.Users;
import org.diogodev.Model.UsersDTO;
import org.diogodev.Repository.UsersRepository;
import org.diogodev.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersFacade {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;


    public ResponseDTO login(String email, String password) {
        Users user = this.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenService.generateToken(user);
        return new ResponseDTO(user.getName(), token);
    }
    public ResponseDTO criar(UsersDTO usersDTO) {
        // Verificar se o email já está registrado
        Optional<Users> existingUser = findByEmail(usersDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Criar o novo usuário
        Users newUser = new Users();
        newUser.setName(usersDTO.getName());
        newUser.setEmail(usersDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(usersDTO.getPassword())); // Codificar a senha
        newUser.setCreatedAt(LocalDateTime.now());
        repository.save(newUser);

        // Gerar o token para o novo usuário
        String token = tokenService.generateToken(newUser);

        return new ResponseDTO(newUser.getName(), token);
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

    public Optional<Users> findByEmail(String email) {
        List<Users> users = repository.findAll();
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public UsersDTO findByEmailAsDTO(String email) {
        return findByEmail(email)
                .map(this::converter)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }


}
