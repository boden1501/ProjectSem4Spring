package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import exam.spring.demo.model.Product;

@Repository
public class IndexRepository {
	@Autowired
	JdbcTemplate db;

	class ProductRowMapper implements RowMapper<Product> {
		  @Override
		    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		        Product item = new Product();
		        item.setIdProduct(rs.getInt(Product.id_product));
		        item.setNameProduct(rs.getString(Product.name_product));
		        double price = rs.getDouble(Product.price_product);
		        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
		        String formattedPrice = decimalFormat.format(price);
		        item.setPriceProduct(formattedPrice); 
		        item.setQuantityProduct(rs.getInt(Product.quantity_product));
		        item.setAvgVote(rs.getFloat(Product.avgvote_product));
		        item.setActiveProduct(rs.getInt(Product.active_product));
		        item.setPriceTemp(rs.getLong(Product.price_product));
		        item.setQuantityBuy(rs.getInt("quantity"));
		        return item;
		    }

		
	}
	public List<Product> findBestSeller() {
		return db.query("SELECT p.idProduct,p.Quantity,Name,Price,p.Quantity,AvgVote,sum(od.quantity),Active FROM product p join `orderdetail` od on p.idProduct=od.idProduct group by p.idProduct order by sum(od.quantity) desc LIMIT 4", new ProductRowMapper());
	}
}
