package com.danielandreassi.todosimple.services;

import com.danielandreassi.todosimple.models.User;
import com.danielandreassi.todosimple.models.enums.ProfileEnum;
import com.danielandreassi.todosimple.repositories.UserRepository;
import com.danielandreassi.todosimple.services.excepitions.DataBindingViolationException;
import com.danielandreassi.todosimple.services.excepitions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private UserRepository userRepository;

    public User findById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow( () -> new ObjectNotFoundException(
                "Usuario nao encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create (User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    public void delete(long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new DataBindingViolationException("Nao e possivel excluir pois ha entidades relacionadas");
        }
    }


}


