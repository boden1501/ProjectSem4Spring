package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.User;


@Repository
public class UseRepository {
	@Autowired
	JdbcTemplate db;
	class userRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs,int rowNum) throws SQLException{
			User item = new User();
			item.setId(rs.getInt(User.id_usr));
			item.setUsername(rs.getString(User.username_usr));
			item.setPassword(rs.getString(User.pwd_usr));
			return item;
		}
	}
	public List<User> findAll() {
		return db.query("select * from member", new userRowMapper());
	}
	
	public int registerUser(User User) {
		return db.update("insert into member (Name, UserName, Password, Address, Phone, Email)" + "values(?, ?, ?, ?, ?, ?)", new Object[] {
				User.getName(),
				User.getUsername(),
				User.getPassword(),
				User.getAddress(),
				User.getPhone(),
				User.getEmail()
				});
	}
}
