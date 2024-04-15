package exam.spring.demo.service;


import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import exam.spring.demo.model.ImageData;

public interface FilesStorageService {
	public void init();

	public void save(MultipartFile file);

	public Resource load(String filename);

	public boolean delete(String filename);
	public void deleteAllFilesInRoot();
	public void deleteAll();

	public Stream<ImageData> loadAll();
}
