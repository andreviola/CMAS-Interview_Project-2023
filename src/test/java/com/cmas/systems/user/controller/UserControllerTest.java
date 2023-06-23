package com.cmas.systems.user.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cmas.systems.user.entities.User;
import com.cmas.systems.user.entities.UserModel;
import com.cmas.systems.user.entities.UserModelAssembler;
import com.cmas.systems.user.services.UserServices;

public class UserControllerTest {
    
    private UserServices service;

    private UserModelAssembler userModelAssembler;

    private UserController controller;
    
    private User obj;

    @BeforeEach
    public void setup() {
        obj = new User("Jonh", "wawa", "jwawa@gmail.com", 50, true);
        service = Mockito.mock(UserServices.class);
        userModelAssembler = Mockito.mock(UserModelAssembler.class);
        controller = new UserController();
        controller.SetService(service);
        controller.SetModelAssembler(userModelAssembler);
    }

    //GET ALL
    @Test
    public void testFindAll() {
        User user1 = obj;

        List<User> usersList = new ArrayList<>();
        usersList.add(user1);
        given(service.findAll()).willReturn(usersList);

        List<UserModel> userModelList = new ArrayList<>();
        userModelList.add(new UserModel());
        given(userModelAssembler.toModel(user1)).willReturn(new UserModel());

        ResponseEntity<List<UserModel>> response = controller.findAll();
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userModelList);
    }

    //GET BY ID
    @Test
    public void testFindById() {
        User user = obj;
        given(service.findById(anyLong())).willReturn(user);

        UserModel userModel = new UserModel();
        given(userModelAssembler.toModel(any(User.class))).willReturn(userModel);

        ResponseEntity<UserModel> response = controller.findById(1L);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userModel);
    }

    //INSERT
    @Test
    public void testInsert() {
        User user = obj;
        given(service.Insert(any(User.class))).willReturn(user);

        UserModel userModel = new UserModel();
        given(userModelAssembler.toModel(any(User.class))).willReturn(userModel);

         ResponseEntity<UserModel> response = controller.Insert(user);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(userModel);
    }

    //DELETE
    @Test
    public void testDelete() {
        User user = obj;
        user.setId(1L);
        given(service.findById(1L)).willReturn(user);

        controller.Delete(1L);

        ResponseEntity<UserModel> response = controller.Delete(1L);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        then(service).should(times(2)).Delete(user.getId());
    }

    //UPDATE
    @Test
    public void testUpdate() {
        User user = obj;

        given(service.findById(1L)).willReturn(user);
        given(service.Update(any(User.class))).willReturn(user);

        UserModel userModel = new UserModel();
        given(userModelAssembler.toModel(any(User.class))).willReturn(userModel);

        ResponseEntity<UserModel> response = controller.Update(new User(), 1L);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userModel);
    }
}