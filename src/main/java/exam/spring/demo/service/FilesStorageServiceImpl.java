package exam.spring.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import exam.spring.demo.model.ImageData;

@Service
public class FilesStorageServiceImpl implements FilesStorageService{
	private final Path root = Paths.get("./uploads");

	  @Override
	  public void init() {
	    try {
	      Files.createDirectories(root);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not initialize folder for upload!");
	    }
	  }

	  @Override
	  public void save(MultipartFile file) {
	    try {
	      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
	    } catch (Exception e) {
	      if (e instanceof FileAlreadyExistsException) {
	        throw new RuntimeException("A file of that name already exists.");
	      }

	      throw new RuntimeException(e.getMessage());
	    }
	  }

	  @Override
	  public Resource load(String filename) {
	    try {
	      Path file = root.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }

	  @Override
	  public boolean delete(String filename) {
	    try {
	      Path file = root.resolve(filename);
	      return Files.deleteIfExists(file);
	    } catch (IOException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }

	  @Override
	  public void deleteAllFilesInRoot() {
	      try {
	          Files.walk(this.root)
	               .filter(path -> !path.equals(this.root))
	               .forEach(this::deleteFile);
	      } catch (IOException e) {
	          throw new RuntimeException("Error deleting files in root directory!");
	      }
	  }

	  private void deleteFile(Path filePath) {
	      try {
	          Files.delete(filePath);
	      } catch (IOException e) {
	          throw new RuntimeException("Error deleting file: " + filePath);
	      }
	  }

	  @Override
	  public Stream<ImageData> loadAll() {
	      try {
	          return Files.walk(this.root, 1)
	                      .filter(path -> !path.equals(this.root))
	                      .map(this::readImageData)
	                      .filter(Objects::nonNull);
	      } catch (IOException e) {
	          throw new RuntimeException("Could not load the files!");
	      }
	  }

	  private ImageData readImageData(Path filePath) {
	      try {
	          String filename = filePath.getFileName().toString();
	          byte[] data = Files.readAllBytes(filePath);
	          return new ImageData(filename, data);
	      } catch (IOException e) {
	          // Xử lý ngoại lệ nếu cần
	          return null;
	      }
	  }

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

}
