package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Category;


@Repository
public class CategoryRepository {
    @Autowired
    JdbcTemplate db;

    class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category item = new Category();
            item.setId(rs.getInt(Category.id_cate));
            item.setName(rs.getString(Category.name_cate));
            item.setActive(rs.getInt(Category.active_cate));
            return item;
        }
    }

    public List<Category> findCategoryAll() {

        return db.query("SELECT * FROM category",new CategoryRowMapper());
    }
	public List<Category> findAll(int offset, int size) {
    	int start=0;
    	if(offset>size) {
    		start=offset-size;
    	}
        return db.query("SELECT * FROM category LIMIT ?, ?", new Object[] { start, size },
                new CategoryRowMapper());
    }

    public int getTotalRows() {
        return db.queryForObject("SELECT COUNT(*) FROM category", Integer.class);
    }
    public Category findById(int id) {
		return db.queryForObject("select * from category where idCategory=?", new CategoryRowMapper(),
				new Object[] { id });
	}
    
    public int insert(Category category) {
        return db.update("INSERT INTO category (nameCategory, active) VALUES (?, ?)",
                new Object[] { category.getName(), category.getActive() });
    }
    
    public int update(Category Category) {
        return db.update("update category set nameCategory = ?, active = ? where idCategory = ?",
                new Object[] { Category.getName(), Category.getActive(), Category.getId()});
    }

}
