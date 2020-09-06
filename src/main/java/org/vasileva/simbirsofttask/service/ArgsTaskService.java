package org.vasileva.simbirsofttask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.vasileva.simbirsofttask.exception.FileSizeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@PropertySource("classpath:application.properties")
public class ArgsTaskService implements CommandLineRunner, TaskService {

    private static Logger logger = LoggerFactory.getLogger(ArgsTaskService.class);

    @Value("${fileSize}")
    Long fileSize;
    private RepoService repoService;

    public ArgsTaskService(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    public void run(String... args) throws Exception {
        Path fileWithLogs = null;
        try {
            fileWithLogs = Paths.get(args[0]);
        } catch (Exception e) {
            System.out.println("\n---------------------------\n" + "Something wrong with your args" + "\n---------------------------\n");
            logger.error(e.getMessage());
        }

        checkFile(fileWithLogs);

    }

    private Long getFileSizeInBytes() {
        return fileSize * 1024;
    }

    @Override
    public void checkFile(Path fileWithLogs) throws IOException, FileSizeException {
        if (Files.size(fileWithLogs) <= (getFileSizeInBytes())) {
            repoService.parseFile(fileWithLogs);
        } else {
            logger.error("Your file is more than " + fileSize + " Kb");
            throw new FileSizeException("\n---------------------------\n" + "Your file is more than " + fileSize + " Kb \n---------------------------");
        }
    }


}
