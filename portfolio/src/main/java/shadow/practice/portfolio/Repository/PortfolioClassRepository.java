package shadow.practice.portfolio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.PortfolioClass;

@Repository
public interface PortfolioClassRepository extends JpaRepository<PortfolioClass, Integer> {

}
