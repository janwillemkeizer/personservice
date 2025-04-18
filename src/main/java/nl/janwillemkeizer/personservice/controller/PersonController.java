package nl.janwillemkeizer.personservice.controller;

import nl.janwillemkeizer.personservice.dto.UserDto;
import nl.janwillemkeizer.personservice.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public List<UserDto> getAllUsersWithDetails() {
        return personService.getAllUsersWithDetails();
    }
}