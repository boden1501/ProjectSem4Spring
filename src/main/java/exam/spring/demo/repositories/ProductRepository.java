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
		        item.setPriceTemp(rs.getLong(Product.price_product));
		        return item;
		    }

		
	}
	public List<Product> findProductAll() {
		return db.query("SELECT * FROM product p join brand b on p.idBrand=b.idBrand ", new ProductRowMapper());
	}
	public List<Product> findProductActive() {
		return db.query("SELECT * FROM product p join brand b on p.idBrand=b.idBrand where p.Active=1 ", new ProductRowMapper());
	}
//	public List<Product> findProductIMGAll() {
//
//		return db.query("SELECT * FROM product p join brand b on p.idBrand=b.idBrand join image i on i.idProduct=p.idProduct", new ProductRowMapper());
//	}
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
		 return db.queryForObject("select * from product p join brand b on p.idBrand=b.idBrand where idProduct=? ", new ProductRowMapper(),
	                new Object[] { id });
	}
	public List<Product> findByBrand(int id) {
		 return db.query("select * from product p join brand b on p.idBrand=b.idBrand join category c on p.idCategory=c.idCategory where b.idBrand=? and p.Active=1 ", new ProductRowMapper(),
	                new Object[] { id });
	}
	public List<Product> findByCategory(int idCategory) {
		 return db.query("select * from product p join category c on p.idCategory=c.idCategory join brand b on p.idBrand=b.idBrand where c.idCategory=? and p.Active=1 ", new ProductRowMapper(),
	                new Object[] { idCategory });
	}
	public List<Product> findByName(String Name) {
		 return db.query("select * from product p join brand b on p.idBrand=b.idBrand where Name like ?", new ProductRowMapper(),
	                new Object[] { "%" + Name + "%" });
	}
	public int insert(Product product) {
		return db.update(
				"INSERT INTO product (Name,idBrand,idCategory,Price,Quantity,Active,Detail) VALUES (?, ?, ?, ?, ?, ?, ?)",
				new Object[] { product.getNameProduct(), product.getIdBrand(), product.getIdCategory(),
					 product.getPriceTemp(), product.getQuantityProduct(),
						product.getActiveProduct(), product.getDetail() });
	}
	public int updateProduct(Product product,int idProduct) {
        return db.update("update product set Name=?,idBrand=?,idCategory=?,Price=?,Quantity=?,Active=?,Detail=? where idProduct = ?",
                new Object[] { product.getNameProduct(),product.getIdBrand(),product.getIdCategory(),product.getPriceTemp(),product.getQuantityProduct(),product.getActiveProduct(),product.getDetail(),idProduct});
    }
    public int update(int idDiscount,int idProduct) {
        return db.update("update product set idDiscount=? where idProduct = ?",
                new Object[] { idDiscount,idProduct});
    }
    public int updateQuantity(int Quantity,int idProduct) {
        return db.update("update product set Quantity=? where idProduct = ?",
                new Object[] { Quantity,idProduct});
    }
}
