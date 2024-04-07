package com.example.task_project.PersonApp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    private final PersonService personService;

    @PostMapping("/")
    public void addPerson(@RequestBody PersonEntity personEntity){
        personService.addPerson(personEntity);
    }
}
