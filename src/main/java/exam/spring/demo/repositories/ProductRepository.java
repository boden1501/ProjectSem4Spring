package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
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
			item.setPriceProduct(rs.getDouble(Product.price_product));
			item.setQuantityProduct(rs.getInt(Product.quantity_product));
			item.setIdCategory(rs.getInt(Product.id_category));
			item.setIdDiscount(rs.getInt(Product.id_discount));
			item.setAvgVote(rs.getFloat(Product.avgvote_product));
			item.setIdBrand(rs.getInt(Product.id_brand));
			item.setActiveProduct(rs.getInt(Product.active_product));
			item.setDetail(rs.getString(Product.detail_product));
			item.setNameBrand(rs.getString(Brand.nameBrand));
//			item.setImg(rs.getString(Image.image_img));
//			item.setMainImg(rs.getInt(Image.main_img));
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

	public int insert(Product product) {
		return db.update(
				"INSERT INTO product (Name,idBrand,idCategory,Price,Quantity,Active,Detail) VALUES (?, ?, ?, ?, ?, ?, ?)",
				new Object[] { product.getNameProduct(), product.getIdBrand(), product.getIdCategory(),
					 product.getPriceProduct(), product.getQuantityProduct(),
						product.getActiveProduct(), product.getDetail() });
	}

//    public int update(Product Product) {
//        return db.update("update Product set nameProduct = ?, active = ? where idProduct = ?",
//                new Object[] { Product.getName(), Product.getActive(), Product.getId()});
//    }
}
