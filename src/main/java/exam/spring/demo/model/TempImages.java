package exam.spring.demo.model;


public class TempImages {
	private String fileName;
	private Integer mainImg;
	private byte[] data;
	public TempImages(String fileName,byte[] data) {
		this.fileName=fileName;
		this.data=data;
		// TODO Auto-generated constructor stub
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Integer getMainImg() {
		return mainImg;
	}
	public void setMainImg(Integer mainImg) {
		this.mainImg = mainImg;
	}
	
}
