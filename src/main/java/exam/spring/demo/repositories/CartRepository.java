package exam.spring.demo.repositories;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import exam.spring.demo.model.Cart;


@Repository
public class CartRepository {
	@Autowired
	JdbcTemplate db;
	class CartRowMapper implements RowMapper<Cart> {
		@Override
		public Cart mapRow(ResultSet rs, int rowNum) throws SQLException{
			Cart item = new Cart();
			item.setIdCart(rs.getInt(Cart.id_cart));
			item.setIdProduct(rs.getInt(Cart.id_product));
			item.setQuantity(rs.getInt(Cart.quantity));
			item.setIdUser(rs.getInt(Cart.id_User));
			item.setNameProduct(rs.getString(Cart.name_Product));
			item.setImage(rs.getString(Cart.image_Product));
			double price = rs.getDouble(Cart.price_Product);
	        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
	        String formattedPrice = decimalFormat.format(price);
	        item.setPriceProduct(formattedPrice); 
	        item.setIdDiscount(rs.getInt(Cart.id_discount));
			return item;
		}
	}
	public List<Cart> loadAll(){
		return db.query("select * from cart", new CartRowMapper());
	}
	public List<Cart> loadAllByID(int idUser){
		return db.query("select * from cart c join product p on c.idProduct=p.idProduct join image i on i.idProduct=c.idProduct where idUser=? and main=1", new CartRowMapper(),new Object[]{idUser});
	}


	public String getTotal(int idUser) {
		double price = db.queryForObject("select sum(c.Quantity*p.Price) from cart c join product p on c.idProduct=p.idProduct join image i on i.idProduct=c.idProduct where idUser=? and main=1", Integer.class,new Object[]{idUser});
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        String formattedTotal = decimalFormat.format(price);

	    return formattedTotal;
	}
	public double getTotalTemp(int idUser) {
		return db.queryForObject("select sum(c.Quantity*p.Price) from cart c join product p on c.idProduct=p.idProduct join image i on i.idProduct=c.idProduct where idUser=? and main=1", Integer.class,new Object[]{idUser});
	}

	public int insert(int idProduct,int quantity,int idUser) {
		return db.update("insert into cart(idProduct,Quantity,idUser) value(?,?,?)",new Object[] {idProduct,quantity,idUser});
	}
	public int deletaAll(int idUser) {
		return db.update("delete from cart where idUser=?",new Object[] {idUser});
	}
	public int deleteByID(int idProduct) {
		return db.update("delete from cart where idCart=?",new Object[] {idProduct});
	}
	public int updateQuantiy(int quantity,int idProduct) {
		return db.update("update cart set Quantity=? where idProduct=?",new Object[] {quantity,idProduct});
	}
	public int getNum(int idUser) {
		return db.queryForObject("select count(*) from cart c join product p on c.idProduct=p.idProduct where idUser=?",Integer.class,new Object[] {idUser});
	}
}
