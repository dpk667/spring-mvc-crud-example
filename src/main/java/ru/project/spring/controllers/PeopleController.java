package ru.project.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.project.spring.dao.PersonDAO;
import ru.project.spring.models.Person;

import javax.validation.Valid;

/**
 * Controller for handling people-related requests.
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    /**
     * Handle GET request to show a list of people.
     *
     * @param model The Model object.
     * @return The view name for the list of people.
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    /**
     * Handle GET request to show details of a specific person.
     *
     * @param id    The ID of the person to show.
     * @param model The Model object.
     * @return The view name for showing details of a person.
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    /**
     * Handle GET request for creating a new person.
     *
     * @param person The Person object.
     * @return The view name for the new person form.
     */
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    /**
     * Handle POST request to create a new person.
     *
     * @param person         The Person object.
     * @param bindingResult  The BindingResult object.
     * @return The view name for the list of people or the new person form in case of errors.
     */
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    /**
     * Handle GET request to show the form for editing a person.
     *
     * @param model The Model object.
     * @param id    The ID of the person to edit.
     * @return The view name for the person edit form.
     */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    /**
     * Handle PATCH request to update a person.
     *
     * @param person         The updated Person object.
     * @param bindingResult  The BindingResult object.
     * @param id             The ID of the person to update.
     * @return The view name for the list of people or the person edit form in case of errors.
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    /**
     * Handle DELETE request to delete a person.
     *
     * @param id The ID of the person to delete.
     * @return The view name for the list of people.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
