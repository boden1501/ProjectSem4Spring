package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Image;


@Repository
public class ImageRepository {
	@Autowired
	JdbcTemplate db;
	class ImageRowMapper implements RowMapper<Image> {
		@Override
		public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
			Image item = new Image();
			item.setIdImage(rs.getInt(Image.id_img));
			item.setIdProduct(rs.getInt(Image.id_product));
			item.setMain(rs.getInt(Image.main_img));
			item.setImage(rs.getString(Image.image_img));
			return item;
		}


	}
	public List<Image> findImgAll() {
		return db.query("SELECT * FROM image", new ImageRowMapper());
	}
	public List<Image> findListImgByID(int id){
		return db.query("SELECT * FROM image where idProduct=?", new ImageRowMapper(),new Object[] {id});
	}
    public int insert(int idProduct,int main,String Image) {
        return db.update("INSERT INTO image (idProduct,main,Image) VALUES (?,?,?)",
                new Object[] { idProduct,main,Image});
    }
    public int deleteByID(int idImage) {
		return db.update("delete from image where idImage=?",new Object[] {idImage});
	}
    public int updateMain(int main,int id) {
    	return db.update("update image set main=? where idImage=?",new Object[] {main,id});
    }
    public int update(int main,int idImage,int idProduct) {
    	return db.update("update image set main=? where idImage!=? and idProduct=?",new Object[] {main,idImage,idProduct});
    }
}
