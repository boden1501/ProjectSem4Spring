package exam.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import exam.spring.demo.service.DiscountService;
import exam.spring.demo.service.FilesStorageService;
import jakarta.annotation.Resource;

@SpringBootApplication
public class ProjectSem4Application implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;
    @Autowired
    DiscountService discountService;
    public static void main(String[] args) {
        SpringApplication.run(ProjectSem4Application.class, args);
    }

    public void run(String... arg) throws Exception {
        storageService.init();
        discountService.updateDiscountStatus();
    }
}
