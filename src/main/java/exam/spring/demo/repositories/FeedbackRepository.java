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
			item.setNameUser(rs.getInt("idnameUser"));
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

//public int insert(Comment comment) {
////	return db.update("INNSERT INTO comment(nameComment, active) VALUES(?,?)",
////			new Object[] {comment.getIdComment(), comment.getIdComment()});
////}
//@Repository
//public class CommentRepository {
//	@Autowired
//	JdbcTemplate db;
//    class CommentRowMapper implements RowMapper<Comment>{
//    	@Override
//        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException{
//        	Comment item = new Comment();
//        	item.setIdComment(rs.getInt("idComment"));
//        	item.setIdUser(rs.getInt("idUser"));
//        	item.setIdProduct(rs.getInt("idProduct"));
//        	item.setContent(rs.getString("Content"));
//        	item.setDateTime(rs.getString("Datetime"));
//        	item.setName(rs.getString("Name"));
//        	return item;
//        }
//    }
//    
//    
//    public List<Comment> findComments(){
//    	return db.query("SELECT *  FROM comment", new CommentRowMapper());
//    }
//    
//    public List<Comment> finnAll(int offset, int size){
//    	int start=0;
//    	if(offset>size) {
//    		start=offset-size;
//    	}
//    	return db.query("SELECT * FROM comment LIMIT?,?", new Object[] {},
//    			new CommentRowMapper());
//    }
//    
//    public int getToalRows() {
//    	return db.queryForObject("SELECT COUNT (*) FROM brand", Integer.class);
//    }
//    
//    public List<Comment> findById(int id) {
//    	return db.query("select * from comment c join member m on c.idUser=m.idUser where idProduct=?", new CommentRowMapper(),
//    			new Object[] {id});
//    }
//    
//    public int insert(Comment comment) {
//    	return db.update("INNSERT INTO comment(nameComment, active) VALUES(?,?)",
//    			new Object[] {comment.getIdComment(), comment.getIdComment()});
//    }
//    
//    public int deleteById(int id) {
//    	return db.update("delete from comment where idComment=?", new Object[] {id});
//    }
//}
