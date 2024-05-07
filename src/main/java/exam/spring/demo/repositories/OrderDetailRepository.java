package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Cart;
import exam.spring.demo.model.CheckoutDT;


@Repository
public class OrderDetailRepository {
	@Autowired
    JdbcTemplate db;
	class CheckoutDTRowMapper implements RowMapper<CheckoutDT> {
        @Override
        public CheckoutDT mapRow(ResultSet rs, int rowNum) throws SQLException {
            CheckoutDT item = new CheckoutDT();
            item.setIdOrder(rs.getString("idOrder"));
            item.setIdOrderDetail(rs.getInt("idOrderDetail"));
            item.setIdProduct(rs.getInt("idProduct"));
            item.setQuantity(rs.getInt("quantity"));
            item.setNameProduct(rs.getString("Name"));
            return item;
        }
    }
	public List<CheckoutDT> findCheckoutDTAll() {
        return db.query("SELECT * FROM orderdetail dt join product p on dt.idProduct=p.idProduct ", new CheckoutDTRowMapper());
    }
	public List<CheckoutDT> findByID(String idOrder) {
        return db.query("SELECT * FROM orderdetail dt join product p on dt.idProduct=p.idProduct where idOrder=?", new CheckoutDTRowMapper(),new Object[] {idOrder});
    }

    
    public int insert(String idOrder,Cart cart) {
        return db.update("INSERT INTO orderdetail (idOrder,idProduct,quantity) VALUES (?, ?, ?)",
                new Object[] { idOrder,cart.getIdProduct(),cart.getQuantity()});
    }
}
