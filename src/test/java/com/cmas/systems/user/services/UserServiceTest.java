package com.cmas.systems.user.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cmas.systems.user.entities.User;
import com.cmas.systems.user.exceptions.EmptyFieldException;
import com.cmas.systems.user.exceptions.UserNotFoundException;
import com.cmas.systems.user.repositories.UserRepository;


//@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    private UserRepository userRepository;

    private UserServices userServices;
    
    private User obj;

    @BeforeEach
    void setup() {
        obj = new User("Jonh", "wawa", "jwawa@gmail.com", 50, true);
        userRepository = Mockito.mock(UserRepository.class);
        userServices = new UserServices();
        userServices.SetRepository(userRepository);
    }
    

    //FIND ALL
    @Test
    public void FindAllTest(){
        //given
        given(userRepository.findAll()).willReturn(anyList());
        userServices.Insert(obj);

        //when
        userServices.findAll();

        //then
        then(userRepository).should().findAll();
    }


    //FIND BY ID
    @Test
    public void findbyCorrectIdTest(){
        User user = new User();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        User expected = userServices.findById(anyLong());

        then(userRepository).should().findById(anyLong());
        assertThat(expected).isEqualTo(user);
    }
    @Test
    public void findNonExistentbyIdTest(){
        willThrow(new UserNotFoundException("exception test")).given(userRepository).findById(anyLong());
        Exception error = null;

        try{
            userServices.findById(anyLong());
        }catch(UserNotFoundException ex){
            error = ex;
        }

        then(userRepository).should().findById(anyLong());
        assertThat(error).isNotNull();
    }


    //INSERT USER
    @Test   
    public void InsertUserTest(){
        given(userRepository.save(any(User.class))).willReturn(obj);

        User expected = userServices.Insert(obj);

        then(userRepository).should().save(any(User.class));
        assertThat(expected).isEqualTo(obj);
    }
    @Test   
    public void InsertNullUserTest(){
        User newobj = obj;
        newobj.setFirstName(null);
        willThrow(new EmptyFieldException("exception test")).given(userRepository).save(newobj);
        Exception error = null;

        try{
            userServices.Insert(newobj);
        }catch(EmptyFieldException ex){
            error = ex;
        }
        
        then(userRepository).should().save(any(User.class));
        assertThat(error).isNotNull();
    }


    //DELETE USER
    @Test
    public void DeleteUserTest(){
        long id = 1L;

        userServices.Delete(id);

        then(userRepository).should(times(1)).deleteById(anyLong());
    }
    @Test
    public void DeleteUserByNonExistentIdTest(){
        long id = 1L;
        willThrow(new UserNotFoundException("exception test")).given(userRepository).deleteById(anyLong());

        Exception error = null;

        try{
            userServices.Delete(id);
        }catch(UserNotFoundException ex){
            error = ex;
        }

        then(userRepository).should(times(1)).deleteById(anyLong());
        assertThat(error).isNotNull();
    }


    //UPDATE
    @Test
    public void UpdateUserTest(){
        long id = 1L;
        
        User oldUser = new User();
        oldUser.setId(id);
        

        User newUser = new User();
        newUser.setId(id);
        newUser.setFirstName("André");
        newUser.setAge(25);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(newUser));
        given(userRepository.save(any(User.class))).willReturn(newUser);


        User actualuser = userServices.Update(oldUser);


        then(userRepository).should(times(1)).save(any(User.class));
        assertThat(actualuser).isEqualTo(newUser);
    }
    @Test
    public void UpdateNonExistentUserTest(){
        long id = 1L;
        
        User oldUser = new User();
        oldUser.setId(id);
        

        User newUser = new User();
        newUser.setId(id);
        newUser.setFirstName("André");
        newUser.setAge(25);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(newUser));
        given(userRepository.save(any(User.class))).willReturn(newUser);
        willThrow(new UserNotFoundException("exception test")).given(userRepository).save(any(User.class));

        Exception error = null;

        try{
            userServices.Update(oldUser);
        }catch(UserNotFoundException ex){
            error = ex;
        }

        then(userRepository).should(times(1)).save(any(User.class));
        assertThat(error).isNotNull();
    }   


}
