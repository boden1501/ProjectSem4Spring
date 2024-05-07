package exam.spring.demo.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import exam.spring.demo.model.Category;
import exam.spring.demo.model.Discount;
import exam.spring.demo.repositories.DiscountRepository.DiscountRowMapper;

@Repository
public class DiscountRepository {
	@Autowired
	JdbcTemplate db;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	class DiscountRowMapper implements RowMapper<Discount> {
        @Override
        public Discount mapRow(ResultSet rs, int rowNum) throws SQLException {
            Discount item = new Discount();
            item.setIdDiscount(rs.getInt(Discount.id_discount));
            item.setDateStart(rs.getString(Discount.start_discount));
            item.setDateEnd(rs.getString(Discount.end_discount));
//            LocalDateTime dateStart = rs.getTimestamp(Discount.start_discount).toLocalDateTime();
//            String formattedDateStart = dateStart.format(formatter);
//            item.setDateStart(formattedDateStart);
//            
//            // Format dateEnd
//            LocalDateTime dateEnd = rs.getTimestamp(Discount.end_discount).toLocalDateTime();
//            String formattedDateEnd = dateEnd.format(formatter);
//            item.setDateEnd(formattedDateEnd);
            item.setPercentDiscount(rs.getFloat(Discount.percent_discount));
            return item;
        }

    }
	public List<Discount> findDiscountAll() {
    
        return db.query("SELECT * FROM discount where active=0", new DiscountRowMapper());
    }

	public List<Discount> findAll(int offset, int size) {
    	int start=0;
    	if(offset>size) {
    		start=offset-size;
    	}
        return db.query("SELECT * FROM discount where active=0 LIMIT ?, ? ", new Object[] { start, size },
                new DiscountRowMapper());
    }

	public String getDiscount(int idUser) {
		double price = db.queryForObject("SELECT sum(p.Price*d.PercentDiscount*c.Quantity) FROM discount d join product p on p.idDiscount=d.idDiscount join cart c on c.idProduct=p.idProduct where idUser=?", Integer.class,new Object[]{idUser});
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        String formattedTotal = decimalFormat.format(price);

	    return formattedTotal;
	}
	public double getDiscountTemp(int idUser) {
		return db.queryForObject("SELECT sum(p.Price*d.PercentDiscount*c.Quantity) FROM discount d join product p on p.idDiscount=d.idDiscount join cart c on c.idProduct=p.idProduct where idUser=?", Integer.class,new Object[]{idUser});

	}
    public int getTotalRows() {
        return db.queryForObject("SELECT COUNT(*) FROM discount", Integer.class);
    }
    public Discount findById(int id) {
		return db.queryForObject("select * from discount where idDiscount=?", new DiscountRowMapper(),
				new Object[] { id });
	}
    
    public int insert(Discount discount) {
        return db.update("INSERT INTO Discount (DateStart, DateEnd,PercentDiscount) VALUES (?, ?, ?)",
                new Object[] { discount.getDateStart(), discount.getDateEnd(),discount.getPercentDiscount()});
    }
    
    public int update(Discount discount) {
        return db.update("update Discount set DateStart = ?, DateEnd = ?, PercentDiscount=? where idDiscount = ?",
                new Object[] { discount.getDateStart(),discount.getDateEnd(),discount.getPercentDiscount(),discount.getIdDiscount()});
    }
}
