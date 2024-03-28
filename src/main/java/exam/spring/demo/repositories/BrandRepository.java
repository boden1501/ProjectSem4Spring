package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Brand;

@Repository
public class BrandRepository {
	@Autowired
    JdbcTemplate db;
	class BrandRowMapper implements RowMapper<Brand> {
        @Override
        public Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
            Brand item = new Brand();
            item.setId_brand(rs.getInt(Brand.idBrand));
            item.setName_brand(rs.getString(Brand.nameBrand));
            item.setActive_brand(rs.getInt(Brand.activeBrand));
            return item;
        }
    }
	public List<Brand> findBrandAll() {
    
        return db.query("SELECT * FROM brand ", new BrandRowMapper());
    }

	public List<Brand> findAll(int offset, int size) {
    	int start=0;
    	if(offset>size) {
    		start=offset-size;
    	}
        return db.query("SELECT * FROM brand LIMIT ?, ?", new Object[] { start, size },
                new BrandRowMapper());
    }

    public int getTotalRows() {
        return db.queryForObject("SELECT COUNT(*) FROM brand", Integer.class);
    }
    public Brand findById(int id) {
		return db.queryForObject("select * from brand where idBrand=?", new BrandRowMapper(),
				new Object[] { id });
	}
    
    public int insert(Brand brand) {
        return db.update("INSERT INTO brand (nameBrand, active) VALUES (?, ?)",
                new Object[] { brand.getName_brand(), brand.getActive_brand()});
    }
    
    public int update(Brand brand) {
        return db.update("update brand set nameBrand = ?, active = ? where idBrand = ?",
                new Object[] { brand.getName_brand(), brand.getActive_brand(), brand.getId_brand()});
    }
}
