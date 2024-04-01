package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.model.Category;
import exam.spring.demo.model.User;



@Repository
public class UserRepository {
	@Autowired
	JdbcTemplate db;

	class userRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs,int rowNum) throws SQLException{
			User item = new User();
			item.setId(rs.getInt(User.id_usr));
			item.setName(rs.getString(User.name_usr));
			item.setPassword(rs.getString(User.pwd_usr));
			item.setAddress(rs.getString(User.address_usr));
			item.setEmail(rs.getString(User.email_usr));
			item.setRole(rs.getInt(User.role_usr));
			item.setPhone(rs.getString(User.phone_usr));
			item.setUsername(rs.getString(User.username_usr));
			item.setAvatar(rs.getString(User.avatar_usr));
			item.setActive(rs.getInt(User.active_usr));
			return item;
		}
	}
	public List<User> findAll() {// hien thi day du database
		return db.query("select * from member", new userRowMapper());
	}
	//public int insert(User Category) {
		//return db.update("insert into category ( name_cate, active_cate) " + "values( ?, ?)",
			//	new Object[] { Category.getName() });
	//}
	public User findById(int id) {
		return db.queryForObject("select * from member where idUser=?", new userRowMapper(), 
				new Object[] {id});
	}
	
	public int update(User User) {
		return db.update("update member set active = ? where idUser =?",
				new Object[] { User.getActive(), User.getId()});
	}

	public List<User> findByName(String name,String phone) {
	    return db.query("select * from member where Name like ? or Phone like ?", new userRowMapper(),
	            new Object[]{"%" + name + "%","%" + phone + "%"});
	}
	public List<User> findAll(int offset, int size){
		int start =0 ;
		if (offset>size) {
			start= offset-size;
	}

		return db.query("SELECT * FROM member LIMIT ?, ?", new Object[] { start, size}, 
				new userRowMapper());
	}
	public int getTotalRows() {
	    return db.queryForObject("SELECT COUNT(*) FROM member ", Integer.class);
	    }	
}


