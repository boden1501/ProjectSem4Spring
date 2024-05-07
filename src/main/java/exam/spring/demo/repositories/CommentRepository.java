package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
        	item.setAvatar(rs.getString("Avatar"));
        	return item;
        }
    }
    
    
    public List<Comment> findComments(){
    	return db.query("SELECT *  FROM comment c join member m on c.idUser=m.idUser ", new CommentRowMapper());
    }
    
    public List<Comment> findAll(int id,int offset, int size ){
    	int start=0;
    	if(offset>size) {
    		start=offset-size;
    	}
    	return db.query("SELECT * FROM comment c join member m on c.idUser=m.idUser where c.idProduct=? LIMIT ?,? ", new Object[] {id, start, size },
    			new CommentRowMapper());
    }
    
    public int getToalRows() {
    	return db.queryForObject("SELECT COUNT(*) FROM comment", Integer.class);
    }
    
    public List<Comment> findById(int id) {
    	return db.query("select * from comment c join member m on c.idUser=m.idUser where idProduct=?", new CommentRowMapper(),
    			new Object[] {id});
    }
    
    public int insert(int idUser,int idProduct,String Content) {
    	LocalDateTime currentTime = LocalDateTime.now();
    	return db.update("INSERT INTO comment(idUser,idProduct,Content,DateTime) VALUES(?,?,?,?)",
    			new Object[] {idUser,idProduct,Content,currentTime});
    }
    
    public int deleteById(int id) {
    	return db.update("delete from comment where idComment=?", new Object[] {id});
    }
}


