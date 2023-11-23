package ru.project.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.project.spring.dao.PersonDAO;
import ru.project.spring.models.Person;

/**
 * Controller for handling admin-related requests.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonDAO personDAO;

    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /**
     * Handle GET request for the admin page.
     *
     * @param model   The Model object.
     * @param person  The Person object.
     * @return The view name for the admin page.
     */
    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDAO.index());

        return "adminPage";
    }

    /**
     * Handle PATCH request to add admin role.
     *
     * @param person  The Person object.
     * @return The redirect view name.
     */
    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("person") Person person) {
        System.out.println(person.getId());

        return "redirect:/people";
    }
}
