package exam.spring.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import exam.spring.demo.service.FilesStorageService;
import jakarta.annotation.Resource;

@SpringBootApplication
public class ProjectSem4Application implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectSem4Application.class, args);
    }

    public void run(String... arg) throws Exception {
        System.out.println("Initializing file storage service...");
        storageService.init();
        System.out.println("File storage service initialized successfully.");
    }
}
