package shadow.practice.portfolio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    /**
     * runs a select query on the database with the table name email
     * @param email table from the database.
     * @return person object
     */
    Person readByEmail(String email);
}
