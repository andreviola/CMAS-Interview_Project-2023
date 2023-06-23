package com.cmas.systems.user.entities;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = false)
public class UserModel extends RepresentationModel<UserModel>{
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Boolean active;
}
