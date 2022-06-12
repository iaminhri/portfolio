package shadow.practice.portfolio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles getByRoleName(String userRole);
}
