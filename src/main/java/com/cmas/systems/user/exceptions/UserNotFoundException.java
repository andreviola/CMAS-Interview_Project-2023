package com.cmas.systems.user.exceptions;

/**Could not return User. User may not exist.*/
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
    public UserNotFoundException() {
        super();
    }
}
