package com.cmas.systems.user.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;

import com.cmas.systems.user.entities.User;
import com.cmas.systems.user.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Initializer for the project. Fills the repository and more. Mainly for convenience sake
 */
@Configuration
public class initializer implements CommandLineRunner, InitializingBean {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        User maria = new User("Maria", "Brown", "maria@gmail.com", 20, true);
        User alex = new User("Alex", "Green", "alex@gmail.com", 18, true);
        User bob = new User("Bob", "Grey", "bob@gmail.com", 25, false);

        List<User> users = userRepository.saveAll(Arrays.asList(maria,alex,bob));

        System.out.println(users);
        

    }

    @Override
    public void afterPropertiesSet() {
        objectMapper.registerModules(new ProblemModule());
    }
}
