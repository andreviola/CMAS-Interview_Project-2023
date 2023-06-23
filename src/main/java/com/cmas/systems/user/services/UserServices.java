package com.cmas.systems.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmas.systems.user.entities.User;
import com.cmas.systems.user.repositories.UserRepository;

@Service
public class UserServices {
    
    @Autowired
    private UserRepository repository;

    public void SetRepository(UserRepository ur){
        this.repository = ur;
    }
    
    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(Long id){
        Optional<User> obj = repository.findById(id);
		return obj.orElse(null);
    }

    public User Insert(User obj){
        return repository.save(obj);
    }

    public void Delete(Long id){
        repository.deleteById(id);
    }

    public User Update(User obj){
        User newObj = repository.findById(obj.getId()).get();
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        newObj.setFirstName(newObj.getFirstName()==null ? 
                                obj.getFirstName() : newObj.getFirstName());

        newObj.setLastName(newObj.getLastName()==null ? 
                                obj.getLastName() : newObj.getLastName());

        newObj.setEmail(newObj.getEmail()==null ? 
                                obj.getEmail() : newObj.getEmail());

        newObj.setAge(newObj.getAge()==null ? 
                                obj.getAge() : newObj.getAge());
                                
        newObj.setActive(newObj.getActive()==null ? 
                                obj.getActive() : newObj.getActive());
    }

}
