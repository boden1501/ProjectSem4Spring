package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Category;
import exam.spring.demo.repositories.CategoryRepository.categoryRowMapper;

@Repository
public class CategoryRepository {
	@Autowired
	JdbcTemplate db;
	class categoryRowMapper implements RowMapper<Category> {
		@Override
		public Category mapRow(ResultSet rs,int rowNum) throws SQLException{
			Category item = new Category();
			item.setId(rs.getInt(Category.id_cate));
			item.setName(rs.getString(Category.name_cate));
			item.setActive(rs.getInt(Category.active_cate));
			return item;
		}
	}
	public List<Category> findAll() {
		return db.query("select * from category", new categoryRowMapper());
	}
	public int insert(Category Category) {
		return db.update("insert into category ( name_cate, active_cate) " + "values( ?, ?)",
				new Object[] { Category.getName(), Category.getActive() });
	}
}
