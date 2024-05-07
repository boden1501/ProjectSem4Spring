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
            item.setIdUser(rs.getInt("idUser"));
            return item;
        }
    }
	public List<Checkout> findCheckoutAll() {
    
        return db.query("SELECT * FROM `order` ", new CheckoutRowMapper());
    }

	public List<Checkout> findByName(int id,int offset, int size) {
		int start=0;
    	if(offset>size) {
    		start=offset-size;
    	}
        return db.query("SELECT * FROM `order` where idUSer=? order by DateCreate desc LIMIT ?, ?", new CheckoutRowMapper(),new Object[] {id,start,size});
    }
	public int getTotalRows(int id) {
        return db.queryForObject("SELECT COUNT(*) FROM `order` where idUSer=?", Integer.class,new Object[] {id});
    }
	public int insert(String idOrder,int idUser ,String DateCreate, CheckoutTemp chkTemp, int active) {
	    return db.update("INSERT INTO `order` (idOrder,idUser ,DateCreate, TotalPrice, PriceAfterDiscount, DiscountPrice, nameUser, phoneUser, addressUser, emailUser, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
	            new Object[] { idOrder,idUser ,DateCreate, chkTemp.getSubTotalPrice(), chkTemp.getTotalPrice(), chkTemp.getDiscountPrice(), chkTemp.getNameUser(), chkTemp.getPhoneUser(), chkTemp.getAddressUser(), chkTemp.getEmailUser(), active});
	}


}
