package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Checkout;
import exam.spring.demo.model.CheckoutTemp;

@Repository
public class OrderRepository {
	@Autowired
    JdbcTemplate db;
	class CheckoutRowMapper implements RowMapper<Checkout> {
        @Override
        public Checkout mapRow(ResultSet rs, int rowNum) throws SQLException {
            Checkout item = new Checkout();
            item.setIdOrder(rs.getString("idOrder"));
            item.setDateCreate(rs.getString("DateCreate"));
            item.setTotalPrice(rs.getDouble("PriceAfterDiscount"));
            item.setSubTotalPrice(rs.getDouble("TotalPrice"));
            item.setDiscountPrice(rs.getDouble("DiscountPrice"));
            item.setNameUser(rs.getString("nameUser"));
            item.setPhoneUser(rs.getString("phoneUser"));
            item.setEmailUser(rs.getString("emailUser"));
            item.setAddressUser(rs.getString("addressUser"));
            item.setActive(rs.getInt("active"));
            return item;
        }
    }
	public List<Checkout> findCheckoutAll() {
    
        return db.query("SELECT * FROM `order` ", new CheckoutRowMapper());
    }


    
	public int insert(String idOrder, String DateCreate, CheckoutTemp chkTemp, int active) {
	    return db.update("INSERT INTO `order` (idOrder, DateCreate, TotalPrice, PriceAfterDiscount, DiscountPrice, nameUser, phoneUser, addressUser, emailUser, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
	            new Object[] { idOrder, DateCreate, chkTemp.getSubTotalPrice(), chkTemp.getTotalPrice(), chkTemp.getDiscountPrice(), chkTemp.getNameUser(), chkTemp.getPhoneUser(), chkTemp.getAddressUser(), chkTemp.getEmailUser(), active});
	}

    
	/*
	 * public int update(Checkout Checkout) { return db.
	 * update("update Checkout set nameCheckout = ?, active = ? where idCheckout = ?"
	 * , new Object[] { Checkout.getName_Checkout(), Checkout.getActive_Checkout(),
	 * Checkout.getId_Checkout()}); }
	 */
}