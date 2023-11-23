package ru.project.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.project.spring.models.Person;

import java.util.List;

/**
 * Data Access Object (DAO) for interacting with the Person entity in the database.
 */
@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieve a list of all people from the database.
     *
     * @return List of Person objects.
     */
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    /**
     * Retrieve details of a specific person from the database.
     *
     * @param id The ID of the person to retrieve.
     * @return The Person object with the specified ID or null if not found.
     */
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    /**
     * Save a new person to the database.
     *
     * @param person The Person object to save.
     */
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age, email) VALUES(?, ?, ?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    /**
     * Update details of an existing person in the database.
     *
     * @param id             The ID of the person to update.
     * @param updatedPerson The updated Person object.
     */
    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    /**
     * Delete a person from the database.
     *
     * @param id The ID of the person to delete.
     */
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
