package com.danielandreassi.todosimple.services;

import com.danielandreassi.todosimple.models.User;
import com.danielandreassi.todosimple.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow( () -> new RuntimeException(
                "Usuario nao encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create (User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new RuntimeException("Nao e possivel excluir pois ha entidades relacionadas");
        }
    }


}


