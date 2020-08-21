package com.zhangchun.springboot.propertiesMapping;

import com.zhangchun.springboot.propertiesMapping.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangchun
 */
@SpringBootApplication
//@EnableConfigurationProperties(Person.class)
@RestController
public class PropertiesMappingApplication {

    @Autowired
    private Person person;

    public static void main(String[] args) {
        SpringApplication.run(PropertiesMappingApplication.class, args);
    }
    @RequestMapping("getInfo")
    public ResponseEntity<Person> getInfo(){
        return ResponseEntity.ok(person);
    }
}
