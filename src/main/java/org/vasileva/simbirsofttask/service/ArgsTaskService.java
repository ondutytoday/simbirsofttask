package org.vasileva.simbirsofttask.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.vasileva.simbirsofttask.exception.FileSizeException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@PropertySource("classpath:resources/application.properties")
public class ArgsTaskService implements CommandLineRunner, TaskService {

    @Value("${fileSize}")
    private Long fileSize;
    private RepoService repoService;

    public ArgsTaskService(RepoService repoService) {
        this.repoService = repoService;
    }

    private Long getFileSizeInBytes() {
        return fileSize * 1024 * 1024;
    }

    @Override
    public void run(String... args) throws Exception {
        Path fileWithLogs = null;
        try {
            fileWithLogs = Paths.get(args[0]);
        } catch (Exception e) {
            System.out.println("Something wrong with your args");
            throw e;
        }

        if (Files.size(fileWithLogs) <= (getFileSizeInBytes())) {
            repoService.parseFile(fileWithLogs);
        } else {
            throw new FileSizeException("Your file is more than " + fileSize + " Mb");
        }

    }


}
