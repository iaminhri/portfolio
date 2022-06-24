package shadow.practice.portfolio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Courses;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

}
