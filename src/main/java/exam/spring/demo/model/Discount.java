package exam.spring.demo.model;

import java.time.LocalDateTime;

public class Discount {
	private int idDiscount;
	private String dateStart;
	private String dateEnd;
	private float percentDiscount;
	public static final String id_discount="idDiscount";
	public static final String start_discount="DateStart";
	public static final String end_discount="DateEnd";
	public static final String percent_discount="PercentDiscount";
	public int getIdDiscount() {
		return idDiscount;
	}
	public void setIdDiscount(int idDiscount) {
		this.idDiscount = idDiscount;
	}

	public float getPercentDiscount() {
		return percentDiscount;
	}
	public void setPercentDiscount(float percentDiscount) {
		this.percentDiscount = percentDiscount;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	
}
