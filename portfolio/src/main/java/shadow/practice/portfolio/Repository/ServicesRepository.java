package shadow.practice.portfolio.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Services;

import java.util.List;

@Repository
/**                                        @Corresponded_class_type, @Primary_KeyType */
public interface ServicesRepository extends CrudRepository<Services, String> {

}



/** Repository Class using Java Database Connectivity */
//@Repository
//public class ServicesRepository {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public ServicesRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Services> findAllServices(){
//        String sql = "select * from services";
//        var rowMapper = BeanPropertyRowMapper.newInstance(Services.class);
//
//        return jdbcTemplate.query(sql, rowMapper);
//    }
//}
