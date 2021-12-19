package com.example.socialgraphservice;

import com.example.socialgraphservice.model.User;
import com.example.socialgraphservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SocialgraphserviceApplication {

    private final static Logger log = LoggerFactory.getLogger(SocialgraphserviceApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SocialgraphserviceApplication.class, args);
//        System.exit(0);
    }

    @Bean
    CommandLineRunner demo(UserRepository userRepository) {
        return args -> {

            userRepository.deleteAll();

            User greg = new User("Greg");
            User roy = new User("Roy");
            User craig = new User("Craig");

            List<User> team = Arrays.asList(greg, roy, craig);

            userRepository.saveAll(team);

//            log.info("Before linking up with Neo4j...");
//
//            team.stream().forEach(person -> log.info("\t" + person.toString()));
//
//            userRepository.save(greg);
//            userRepository.save(roy);
//            userRepository.save(craig);
//
//            greg = userRepository.findByUserTag(greg.getUserTag());
//            greg.isFollowing(roy);
//            greg.isFollowing(craig);
//            userRepository.save(greg);
//
//            roy = userRepository.findByUserTag(roy.getUserTag());
//            roy.isFollowing(craig);
//            // We already know that roy works with greg
//            userRepository.save(roy);
//
//            // We already know craig works with roy and greg
//
//            log.info("Lookup each person by name...");
//            team.stream().forEach(person -> log.info(
//                    "\t" + userRepository.findByUserTag(person.getUserTag()).toString()));
//
//            List<User> teammates = userRepository.findByFollowsUserTag(craig.getUserTag());
////            System.out.println(greg);
//            log.info("The following have Greg as a teammate...");
//            teammates.stream().forEach(person -> log.info("\t" + person.getUserTag()));
        };
    }
}
