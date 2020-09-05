package org.vasileva.simbirsofttask.controller;

import org.springframework.boot.CommandLineRunner;
import org.vasileva.simbirsofttask.entity.LogLevel;
import org.vasileva.simbirsofttask.entity.ParsedLog;
import org.vasileva.simbirsofttask.entity.SourceThread;
import org.vasileva.simbirsofttask.repo.LogLevelRepository;
import org.vasileva.simbirsofttask.repo.ParseLogRepository;
import org.vasileva.simbirsofttask.repo.SourceThreadRepository;
import org.vasileva.simbirsofttask.service.ParserForDateTimeService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.StringJoiner;


public class ArgsTaskController implements TaskController, CommandLineRunner {
    private final ParseLogRepository parseLogRepository;
    private final LogLevelRepository logLevelRepository;
    private final SourceThreadRepository sourceThreadRepository;

    public ArgsTaskController(ParseLogRepository parseLogRepository, LogLevelRepository logLevelRepository, SourceThreadRepository sourceThreadRepository) {
        this.parseLogRepository = parseLogRepository;
        this.logLevelRepository = logLevelRepository;
        this.sourceThreadRepository = sourceThreadRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Path file = Paths.get(args[0]);

        if (Files.size(file) >= (2 * 1024)) {
            throw new Exception("File is more than 2Mb");
        }

        BufferedReader bis = new BufferedReader(new FileReader(file.toFile()));

        while (bis.ready()) {
            String line = bis.readLine();
            String[] partsOfLog = line.split(" ");
            Timestamp time = Timestamp.valueOf(LocalDateTime.parse(partsOfLog[0].concat(" ").concat(partsOfLog[1]), ParserForDateTimeService.DATE_TIME_FORMATTER));
            String threadName = partsOfLog[2].substring(1, partsOfLog[2].length() - 1);
            String type = partsOfLog[3];
            StringJoiner joiner = new StringJoiner(" ");
            for (int i = 4; i < partsOfLog.length; i++) {
                joiner.add(partsOfLog[i]);
            }
            String message = joiner.toString();

            SourceThread sourceThread = new SourceThread(threadName);
            sourceThreadRepository.save(sourceThread);
            LogLevel logLevel = new LogLevel(type);
            logLevelRepository.save(logLevel);
            ParsedLog parsedLog = new ParsedLog(time, sourceThread, logLevel, message);
            parseLogRepository.save(parsedLog);

        }
    }
}
