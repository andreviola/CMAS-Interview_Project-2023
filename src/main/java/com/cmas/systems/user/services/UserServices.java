package com.cmas.systems.user.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmas.systems.user.entities.User;
import com.cmas.systems.user.repositories.UserRepository;

/**
 * Class offered as an interface that stands alone in the model.
 */
@Service
public class UserServices {
    
    @Autowired
    private UserRepository repository;

    /**
     * Set UserServices' UserRepository
     * @param ur
     */
    public void SetRepository(UserRepository ur){
        this.repository = ur;
    }
    
    /**
     * Return All User Instances
     * @return  All Users
     */
    public List<User> findAll(){
        return repository.findAll();
    }

    /**
     * Retrieves an entity by its id.
     * @param id must not be null.
     * @return the User with the given id or null if none found.
     */
    public User findById(Long id){
        Optional<User> obj = repository.findById(id);
		return obj.orElse(null);
    }

    /**
     * Saves a given entity.
     * @param obj
     * @return the saved entity; will never be null.
     */
    public User Insert(User obj){
        return repository.save(obj);
    }

    /**
     * Deletes the entity with the given id.
     * @param id must not be null.
     */
    public void Delete(Long id){
        repository.deleteById(id);
    }

    /**
     * Updates a given entity.
     * @param obj must not be null.
     * @return the saved entity; will never be null.
     */
    public User Update(User obj){
        User newObj = repository.findById(obj.getId()).get();
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    /**
     * Update the data in an User entity;
     * if a field is empty or null it will take the old value
     * @param newObj the updated body
     * @param obj the old User body
     */
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
