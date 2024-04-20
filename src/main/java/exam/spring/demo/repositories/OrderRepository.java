package exam.spring.demo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import exam.spring.demo.model.Order;

public class OrderRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Order> findByUserName(String UserName) {
		String sql ="SELECT * FROM orders WHERE user_name =?";
		return jdbcTemplate.query(sql, new Object[]{findByUserName(null)}, (rs, rowNum)
				new Order(
						rs.getLong("id"),
						rs.getString("user_name"),
						rs.getString("product_name"),
						rs.getString("status")
						));
	    }
	}
	

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public class OrderRepository {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public List<Order> findByCustomerName(String customerName) {
//        String sql = "SELECT * FROM orders WHERE customer_name = ?";
//        return jdbcTemplate.query(sql, new Object[]{customerName}, (rs, rowNum) ->
//                new Order(
//                        rs.getLong("id"),
//                        rs.getString("customer_name"),
//                        rs.getString("product_name"),
//                        rs.getString("status")
//                ));
//    }
//}
