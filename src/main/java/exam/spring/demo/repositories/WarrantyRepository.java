package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Warranty;


@Repository

 public class WarrantyRepository {
  @Autowired
  JdbcTemplate db;
  class WarrantyRowMapper implements RowMapper<Warranty>{
	  @Override
	  public Warranty mapRow(ResultSet rs, int rowNum) throws SQLException{
		  Warranty item = new Warranty();
		  item.setIdWarranty(rs.getInt("idWarranty"));
		  item.setIdOrder(rs.getString("idOrder"));
		  item.setIdUser(rs.getInt("idUser"));
		  item.setIssueDescription(rs.getString("issueDescription"));
		  return item;
	  }
  }
  public Warranty findById(int id) {
		 return db.queryForObject("SELECT * FROM warranty w JOIN order o ON w.idOrder =o.idOrder JOIN orderdetail d ON o.idOrder=d.idOrder WHERE idWarranty= ?", new WarrantyRowMapper(),
	               new Object[] { id });
}
  public List<Warranty> findAll() {
	     return db.query("SELECT * FROM warranty w JOIN `order` o ON w.idOrder = o.idOrder JOIN orderdetail d ON o.idOrder = d.idOrder"
	     		, new WarrantyRowMapper());
  }
}