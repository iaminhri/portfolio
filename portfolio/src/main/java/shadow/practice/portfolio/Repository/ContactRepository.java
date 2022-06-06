package shadow.practice.portfolio.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Contact;
import shadow.practice.portfolio.RowMappers.ContactRowMapper;
import shadow.practice.portfolio.Service.ContactService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Repository -> Indicates towards the data layer, such it handles the factors of data layer, handling queries,
 * updating, inserting deleting etc.
 */
@Repository
public class ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMsg(Contact contact){
        String sql = "INSERT INTO CONTACT_MSG (NAME, MOBILE_NUM, EMAIL, MESSAGE, STATUS," +
                "CREATED_AT, CREATED_BY) VALUES(?,?,?,?,?,?,?)";

        return jdbcTemplate.update(sql, contact.getName(), contact.getPhoneNum(), contact.getEmail(),
                contact.getMessage(), contact.getStatus(), contact.getCreatedAt(), contact.getCreatedBy());
    }

    public List<Contact> findMsgsWithStatus(String status) {
        String sql = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, status);
            }
        }, new ContactRowMapper());
    }
}
