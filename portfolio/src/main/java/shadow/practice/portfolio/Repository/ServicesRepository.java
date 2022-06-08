package shadow.practice.portfolio.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Services;

import java.util.List;

@Repository
public class ServicesRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ServicesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Services> findAllServices(){
        String sql = "SELECT * FROM SERVICES";
        var rowMapper = BeanPropertyRowMapper.newInstance(Services.class);

        return jdbcTemplate.query(sql, rowMapper);
    }

}
