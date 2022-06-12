package shadow.practice.portfolio.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Address;
import shadow.practice.portfolio.Model.BaseEntity;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
