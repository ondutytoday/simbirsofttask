package org.vasileva.simbirsofttask.service;

import org.springframework.stereotype.Service;
import org.vasileva.simbirsofttask.entity.LogLevel;
import org.vasileva.simbirsofttask.entity.ParsedLog;
import org.vasileva.simbirsofttask.entity.SourceThread;
import org.vasileva.simbirsofttask.repo.LogLevelRepository;
import org.vasileva.simbirsofttask.repo.ParseLogRepository;
import org.vasileva.simbirsofttask.repo.SourceThreadRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Service
public class RepoService {
    private final ParseLogRepository parseLogRepository;
    private final LogLevelRepository logLevelRepository;
    private final SourceThreadRepository sourceThreadRepository;

    private Path fileWithLogs;

    public RepoService(ParseLogRepository parseLogRepository, LogLevelRepository logLevelRepository, SourceThreadRepository sourceThreadRepository) {
        this.parseLogRepository = parseLogRepository;
        this.logLevelRepository = logLevelRepository;
        this.sourceThreadRepository = sourceThreadRepository;
    }

    private void parseWordsInFile() throws IOException {
        BufferedReader bis = new BufferedReader(new FileReader(fileWithLogs.toFile()));

        while (bis.ready()) {
            String line = bis.readLine();
            String[] partsOfLog = line.split(" ");
            Timestamp time = Timestamp.valueOf(LocalDateTime.parse(partsOfLog[0].concat(" ").concat(partsOfLog[1]), ParserForDateTimeUtil.DATE_TIME_FORMATTER));
            String threadName = partsOfLog[2].substring(1, partsOfLog[2].length() - 1);
            String type = partsOfLog[3];
            StringJoiner joiner = new StringJoiner(" ");
            for (int i = 4; i < partsOfLog.length; i++) {
                joiner.add(partsOfLog[i]);
            }
            String message = joiner.toString();
            saveToRepo(time, threadName, type, message);
        }
    }

    private void saveToRepo(Timestamp time, String threadName, String type, String message) {
        SourceThread sourceThread = sourceThreadRepository.findByName(threadName);
        if (sourceThread == null) {
            sourceThread = new SourceThread(threadName);
            sourceThreadRepository.save(sourceThread);
        }


        LogLevel logLevel = new LogLevel(type);
        logLevelRepository.save(logLevel);
        ParsedLog parsedLog = new ParsedLog(time, sourceThread, logLevel, message);
        parseLogRepository.save(parsedLog);
    }


    public void parseFile(Path fileWithLogs) throws IOException {
        this.fileWithLogs = fileWithLogs;
        parseWordsInFile();
    }
}


