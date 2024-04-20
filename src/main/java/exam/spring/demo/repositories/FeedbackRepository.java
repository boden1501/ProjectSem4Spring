package exam.spring.demo.repositories;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Comment;
import exam.spring.demo.model.Feedback;
import exam.spring.demo.repositories.CommentRepository.CommentRowMapper;



@Repository
public class FeedbackRepository {
	@Autowired
	JdbcTemplate db;
	class FeedRowMapper implements RowMapper<Feedback> {
		@Override
		public Feedback mapRow(ResultSet rs, int rowNum) throws SQLException{
			Feedback item = new Feedback();
			item.setIdFeedback(rs.getInt("idComment"));
			item.setNameUser(rs.getString("nameUser"));
			item.setEmail(rs.getString("email"));
			item.setDetail(rs.getString("Detail"));
			item.getActive(rs.getInt("active"));
			return item;
		}
	
	}
	public int insert(String name, String email, String messege) {
		return db.update("INSERT INTO feedback(nameUser,email,Detail ) VALUES(?,?,?)",
				new Object[] {name, email, messege});
	}
}

