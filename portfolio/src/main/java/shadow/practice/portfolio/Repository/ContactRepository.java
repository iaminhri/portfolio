package shadow.practice.portfolio.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import shadow.practice.portfolio.Model.Contact;
//import shadow.practice.portfolio.RowMappers.ContactRowMapper;
import shadow.practice.portfolio.Service.ContactService;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


/** Persistent Layer */


@Repository
/**                                            Corresponded Class, Primary KeyType */
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {
    //For Reference
    List<Contact> findByStatus(String status);

    @Query("SELECT c FROM Contact c WHERE c.status = :status")
//    @Query(value = "SELECT * FROM contact_msg c WHERE c.status = :status",nativeQuery = true)
    Page<Contact> findByStatus(@Param("status")String status, Pageable pageable);

    /**
     * @Transactional ->
     * @param status - Entity class attribute
     * @param id - entity class attribute
     * @return -> type int.
     */

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status  = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

    //query annotation mentioned in Contact Entity class.
    Page<Contact> findOpenMsgs(@Param("status")String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);
}


/**
 * @Repository -> Indicates towards the data layer, such it handles the factors of data layer, handling queries,
 * updating, inserting deleting etc.
 */

/** Repository accessing Using JDBC Style. */
//@Repository
//public class ContactRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public ContactRepository(JdbcTemplate jdbcTemplate){
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public int saveContactMsg(Contact contact){
//        String sql = "insert into contact_msg (name, mobile_num, email, message, status," +
//                "created_at, created_by) values(?,?,?,?,?,?,?)";
//
//        return jdbcTemplate.update(sql, contact.getName(), contact.getPhoneNum(), contact.getEmail(),
//                contact.getMessage(), contact.getStatus(), contact.getCreatedAt(), contact.getCreatedBy());
//    }
//
//    public List<Contact> findMsgsWithStatus(String status) {
//        String sql = "select * from contact_msg where status = ?";
//        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                preparedStatement.setString(1, status);
//            }
//        }, new ContactRowMapper());
//    }
//
//    public int updateMsgStatus(int contactId, String status, String updatedBy) {
//        String sql = "update contact_msg set status = ?, updated_by = ?, updated_at = ? WHERE contact_id = ?";
//        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                preparedStatement.setString(1, status);
//                preparedStatement.setString(2, updatedBy);
//                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//                preparedStatement.setInt(4, contactId);
//            }
//        });
//    }
//}
