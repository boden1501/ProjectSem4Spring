package exam.spring.demo.model;
public class ImageData {
    private String filename;
    private byte[] data;

    public ImageData(String filename, byte[] data) {
        this.filename = filename;
        this.data = data;
    }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

    
}
