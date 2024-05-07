package exam.spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateDiscountStatus() {
        String sql = "CALL UpdateDiscountStatus()";
        jdbcTemplate.execute(sql);
    }
}
