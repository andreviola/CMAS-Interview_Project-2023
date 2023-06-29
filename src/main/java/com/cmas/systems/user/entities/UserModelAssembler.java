package com.cmas.systems.user.entities;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.cmas.systems.user.controller.UserController;

/**
 * Will add links to UserModel instances and make sure a self-link is always added.
 */
@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {
    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User user) {
        // https://howtodoinjava.com/spring/spring-hateoas-tutorial/
        UserModel userModel = instantiateModel(user);

        //self
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .findById(user.getId()))
                .withSelfRel());

        //Update
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .Update(user,user.getId()))
                .withRel("Update"));

        //Delete
        userModel.add(linkTo(
                methodOn(UserController.class)
                        .Delete(user.getId()))
                .withRel("Delete"));

        userModel.setId(user.getId());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setEmail(user.getEmail());
        userModel.setAge(user.getAge());
        userModel.setActive(user.isActive());

        return userModel;
    }
}
