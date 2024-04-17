package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Brand;
import exam.spring.demo.model.Image;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.Product;
import exam.spring.demo.repositories.ProductRepository.ProductRowMapper;

@Repository
public class ProductRepository {
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
		        item.setIdCategory(rs.getInt(Product.id_category));
		        item.setIdDiscount(rs.getInt(Product.id_discount));
		        item.setAvgVote(rs.getFloat(Product.avgvote_product));
		        item.setIdBrand(rs.getInt(Product.id_brand));
		        item.setActiveProduct(rs.getInt(Product.active_product));
		        item.setDetail(rs.getString(Product.detail_product));
		        item.setNameBrand(rs.getString(Brand.nameBrand));
		        return item;
		    }

		
	}
	public List<Product> findProductAll() {

		return db.query("SELECT * FROM product p join brand b on p.idBrand=b.idBrand ", new ProductRowMapper());
	}

	public List<Product> findAll(int offset, int size) {
		int start = 0;
		if (offset > size) {
			start = offset - size;
		}
		return db.query("SELECT * FROM product p join brand b on p.idBrand=b.idBrand  LIMIT ?, ?", new Object[] { start, size }, new ProductRowMapper());
	}

	public int getTotalRows() {
		return db.queryForObject("SELECT COUNT(*) FROM product", Integer.class);
	}

	public Product findById(int id) {
		 return db.queryForObject("select * from product p join brand b on p.idBrand=b.idBrand where idProduct=?", new ProductRowMapper(),
	                new Object[] { id });
	}
	public List<Product> findByName(String Name) {
		 return db.query("select * from product p join brand b on p.idBrand=b.idBrand where Name like ?", new ProductRowMapper(),
	                new Object[] { "%" + Name + "%" });
	}
	public int insert(Product product) {
		return db.update(
				"INSERT INTO product (Name,idBrand,idCategory,Price,Quantity,Active,Detail) VALUES (?, ?, ?, ?, ?, ?, ?)",
				new Object[] { product.getNameProduct(), product.getIdBrand(), product.getIdCategory(),
					 product.getPriceProduct(), product.getQuantityProduct(),
						product.getActiveProduct(), product.getDetail() });
	}

    public int update(int idDiscount,int idProduct) {
        return db.update("update product set idDiscount=? where idProduct = ?",
                new Object[] { idDiscount,idProduct});
    }
}
