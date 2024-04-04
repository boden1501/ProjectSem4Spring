package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Comment;


@Repository
public class CommentRepository {
	@Autowired
	JdbcTemplate db;
    class CommentRowMapper implements RowMapper<Comment>{
    	@Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException{
        	Comment item = new Comment();
        	item.setIdComment(rs.getInt("idComment"));
        	item.setIdUser(rs.getInt("idUser"));
        	item.setIdProduct(rs.getInt("idProduct"));
        	item.setContent(rs.getString("Content"));
        	item.setDateTime(rs.getString("Datetime"));
        	item.setName(rs.getString("Name"));
        	return item;
        }
    }
    
    
    public List<Comment> findComments(){
    	return db.query("SELECT *  FROM comment", new CommentRowMapper());
    }
    
    public List<Comment> finAll(int offset, int size){
    	int start=0;
    	if(offset>size) {
    		start=offset-size;
    	}
    	return db.query("SELECT * FROM comment LIMIT?,?", new Object[] {},
    			new CommentRowMapper());
    }
    
    public int getToalRows() {
    	return db.queryForObject("SELECT COUNT (*) FROM brand", Integer.class);
    }
    
    public List<Comment> findById(int id) {
    	return db.query("select * from comment c join member m on c.idUser=m.idUser where idProduct=?", new CommentRowMapper(),
    			new Object[] {id});
    }
    
    public int insert(Comment comment) {
    	return db.update("INSERT INTO comment(nameComment, active) VALUES(?,?)",
    			new Object[] {comment.getIdComment(), comment.getIdComment()});
    }
    
    public int deleteById(int id) {
    	return db.update("delete from comment where idComment=?", new Object[] {id});
    }
}


