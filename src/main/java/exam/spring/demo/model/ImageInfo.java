package exam.spring.demo.model;

public class ImageInfo {
	private String name;
	private String url;
	private byte[] data;
	public ImageInfo(String name, String url,byte[] data) {
		this.name = name;
		this.url = url;
		this.data=data;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
}
