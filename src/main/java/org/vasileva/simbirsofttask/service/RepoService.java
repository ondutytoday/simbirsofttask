package org.vasileva.simbirsofttask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

/*
* класс, получающий данный из файла, парсящий их и сохраняющий в базу данных
* */
@Service
@Transactional(rollbackFor = Exception.class)
public class RepoService {

    private static Logger logger = LoggerFactory.getLogger(RepoService.class);

    private final ParseLogRepository parseLogRepository;
    private final LogLevelRepository logLevelRepository;
    private final SourceThreadRepository sourceThreadRepository;

    private Path fileWithLogs;

    public RepoService(ParseLogRepository parseLogRepository, LogLevelRepository logLevelRepository, SourceThreadRepository sourceThreadRepository) {
        this.parseLogRepository = parseLogRepository;
        this.logLevelRepository = logLevelRepository;
        this.sourceThreadRepository = sourceThreadRepository;
    }

    //парсим
    private void parseWordsInFile() throws IOException {
        BufferedReader bis = new BufferedReader(new FileReader(fileWithLogs.toFile()));

        while (bis.ready()) {
            String line = bis.readLine();
            String[] partsOfLog = line.split(" ");
            if (partsOfLog.length < 4) {
                continue;
            }
            Timestamp time = null;
            String threadName = null;
            String logDescription = null;
            String message = null;

            String dateTime = partsOfLog[0].concat(" ").concat(partsOfLog[1]);

            if (dateTime.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d.\\d\\d\\d")) {
                time = Timestamp.valueOf(LocalDateTime.parse(dateTime, ParserForDateTimeUtil.DATE_TIME_FORMATTER));
                threadName = partsOfLog[2].substring(1, partsOfLog[2].length() - 1);
                logDescription = partsOfLog[3];
                StringJoiner joiner = new StringJoiner(" ");
                for (int i = 4; i < partsOfLog.length; i++) {
                    joiner.add(partsOfLog[i]);
                }
                message = joiner.toString();
                saveToRepo(time, threadName, logDescription, message);
            }

        }
    }

    //сохраняем
    private void saveToRepo(Timestamp time, String threadName, String logDescription, String message) {
        SourceThread sourceThread = sourceThreadRepository.findByThreadName(threadName);
        if (sourceThread == null) {
            sourceThread = new SourceThread(threadName);
            sourceThreadRepository.save(sourceThread);
        }
        LogLevel logLevel = logLevelRepository.findByDescription(logDescription);
        if (logLevel == null) {
            logLevel = new LogLevel(logDescription);
            logLevelRepository.save(logLevel);
        }
        ParsedLog parsedLog = new ParsedLog(time, sourceThread, logLevel, message);
        parseLogRepository.save(parsedLog);
    }


    //получаем файл который будем парсить
    public void parseFile(Path fileWithLogs) throws IOException {
        this.fileWithLogs = fileWithLogs;
        parseWordsInFile();
    }
}


