package com.cmas.systems.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cmas.systems.user.entities.User;
import com.cmas.systems.user.entities.UserModel;
import com.cmas.systems.user.entities.UserModelAssembler;
import com.cmas.systems.user.exceptions.EmptyFieldException;
import com.cmas.systems.user.exceptions.UserNotFoundException;
import com.cmas.systems.user.services.UserServices;

/** Rest Controller for User Entity Class */
@RestController
@RequestMapping(value = { "/api/v2/users" })
public class UserController {

    @Autowired
    private UserServices service;

    @Autowired
    private UserModelAssembler userModelAssembler;


    /**
     * Set UserController's UserService
     * @param service
     */
    public void SetService(UserServices service){
        this.service = service;
    }
    /**
     * Set UserController's UserModelAssembler
     * @param modelAssembler
     */
    public void SetModelAssembler(UserModelAssembler modelAssembler){
        this.userModelAssembler = modelAssembler;
    }

    /**
     * GET Request of all User Entities
     * @return Response Entity of all Users
     */
    @GetMapping()
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> users = service.findAll().stream().map(userModelAssembler::toModel).collect(Collectors.toList());
        //return new ResponseEntity<>(userModelAssembler.toCollectionModel(users), HttpStatus.OK);
        return ResponseEntity.ok().body(users);

    }

     /**
    * GET Request of an User entity by its id.
    * @param id
    * @return Response Entity with the given id
    * @throws UserNotFoundException when the id isn't assigned to any User
    */
    @GetMapping(value = { "/{id}" })
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        User obj = service.findById(id);
        if (obj == null) {
            throw new UserNotFoundException(String.format("User %s not found", id));
        }

        return new ResponseEntity<>(userModelAssembler.toModel(obj), HttpStatus.OK);
    }

    /**
    * POST Request of an User entity.
    * @param obj
    * @return Response Entity of the inserted entity
    * @throws UnsupportedOperationException when the obj contains an id (all ids are automatically generated)
    * @throws EmptyFieldException when the obj contains one or more empty fields
    */
    @PostMapping
    public ResponseEntity<UserModel> Insert(@RequestBody User obj) {
        if (obj.getId() != null) {// Try to insert with an id
            throw new UnsupportedOperationException("Can not manualy insert id into User");
        }

        if (!hasAllArgs(obj)) {// Try to insert without all args
            throw new EmptyFieldException("All User fields are required to insert!");
        }
        obj = service.Insert(obj);

        return new ResponseEntity<>(userModelAssembler.toModel(obj), HttpStatus.CREATED);
    }

     /**
    * DELETE Request of an User entity by its id.
    * @param id
    * @return Response Entity with NO_CONTENT status.
    */
    @DeleteMapping(value = { "/{id}" })
    public ResponseEntity<UserModel> Delete(@PathVariable Long id) {
        findById(id);
        service.Delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
    * PUT Request of an User entity.
    * @param obj the updated User body
    * @param id the id of the User to be updated
    * @return Response Entity of the updated entity.
    */
    @PutMapping(value = { "/{id}" })
    public ResponseEntity<UserModel> Update(@RequestBody User obj, @PathVariable Long id) {
        findById(id);
        obj.setId(id);
        obj = service.Update(obj);

        return new ResponseEntity<>(userModelAssembler.toModel(obj), HttpStatus.OK);
    }

    /**
    * Check if User contains all args
    * @param obj the User to be checked.
    * @return {@code false} if one or more fields are null; otherwise returns {@code true}
    */
    private Boolean hasAllArgs(User obj) {
        if (obj.getFirstName() != null && obj.getLastName() != null && obj.getEmail() != null
                && obj.getAge() != null && obj.getActive() != null) {
            return true;
        }
        return false;
    }

}
