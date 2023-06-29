package com.cmas.systems.user.entities;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



/**
* User Entity
*/
@Entity
@Builder
@Table(name = "users")
@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString()
public class User extends RepresentationModel<User> implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Boolean active;
    
    /**
     * User Entity Constructor
     * @param String firstName 
     * @param String lastName
     * @param String email
     * @param Integer age
     * @param Boolean active
     * 
     */
    public User(String firstName, String lastName, String email, Integer age, Boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.active = active;
    }

    /**
     * Returns True if the user is Active and False if it isn't
     * @return Boolean
     */
    public Boolean isActive() {
        return active;
    }
    
    /**
     * Sets the active state of the specified user
     * @param Boolean active
     * 
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
   
}
