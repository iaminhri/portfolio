package shadow.practice.portfolio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Courses;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

    //order by descending...
    List<Courses> findByOrderByNameDesc();

    //Order by ascending... @Default Ascending if not mentioned.
    List<Courses> findByOrderByName();

}
