package org.vasileva.simbirsofttask.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vasileva.simbirsofttask.repo.LogLevelRepository;
import org.vasileva.simbirsofttask.repo.ParseLogRepository;
import org.vasileva.simbirsofttask.repo.SourceThreadRepository;

import java.util.Map;

@Controller
public class WebTaskController implements TaskController{
    private final ParseLogRepository parseLogRepository;
    private final LogLevelRepository logLevelRepository;
    private final SourceThreadRepository sourceThreadRepository;

    public WebTaskController(ParseLogRepository parseLogRepository, LogLevelRepository logLevelRepository, SourceThreadRepository sourceThreadRepository) {
        this.parseLogRepository = parseLogRepository;
        this.logLevelRepository = logLevelRepository;
        this.sourceThreadRepository = sourceThreadRepository;
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
/*        Message message = new Message(text, tag);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);*/

        return "main";
    }


}
