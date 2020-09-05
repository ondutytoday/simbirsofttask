package org.vasileva.simbirsofttask.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table (name = "parsedlog")
public class ParsedLog {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column (name = "date_time")
    private Timestamp dateTime;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "thread_id")
    private SourceThread thread;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id")
    private LogLevel level;

    @Column (name="message")
    private String message;

    public ParsedLog() {
    }

    public ParsedLog(Timestamp dateTime, SourceThread thread, LogLevel level, String message) {
        this.dateTime = dateTime;
        this.thread = thread;
        this.level = level;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public SourceThread getThread() {
        return thread;
    }

    public void setThread(SourceThread thread) {
        this.thread = thread;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return dateTime + " " +
                level +" " +
                thread + " " +
                message + "\n";
    }
}
