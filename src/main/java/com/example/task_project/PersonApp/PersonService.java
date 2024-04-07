package com.example.task_project.PersonApp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void addPerson(PersonEntity personEntity){
        personRepository.save(personEntity);
    }
}
