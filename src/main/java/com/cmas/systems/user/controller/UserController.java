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


    public void SetService(UserServices ser){
        this.service = ser;
    }
    public void SetModelAssembler(UserModelAssembler modelAssembler){
        this.userModelAssembler = modelAssembler;
    }

    @GetMapping()
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> users = service.findAll().stream().map(userModelAssembler::toModel).collect(Collectors.toList());
        //return new ResponseEntity<>(userModelAssembler.toCollectionModel(users), HttpStatus.OK);
        return ResponseEntity.ok().body(users);

    }

    @GetMapping(value = { "/{id}" })
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        User obj = service.findById(id);
        if (obj == null) {
            throw new UserNotFoundException(String.format("User %s not found", id));
        }

        return new ResponseEntity<>(userModelAssembler.toModel(obj), HttpStatus.OK);
    }

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

    @DeleteMapping(value = { "/{id}" })
    public ResponseEntity<UserModel> Delete(@PathVariable Long id) {
        findById(id);
        service.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = { "/{id}" })
    public ResponseEntity<UserModel> Update(@RequestBody User obj, @PathVariable Long id) {
        findById(id);
        obj.setId(id);
        obj = service.Update(obj);

        return new ResponseEntity<>(userModelAssembler.toModel(obj), HttpStatus.OK);
    }

    private Boolean hasAllArgs(User obj) {
        if (obj.getFirstName() != null && obj.getLastName() != null && obj.getEmail() != null
                && obj.getAge() != null && obj.getActive() != null) {
            return true;
        }
        return false;
    }

}
