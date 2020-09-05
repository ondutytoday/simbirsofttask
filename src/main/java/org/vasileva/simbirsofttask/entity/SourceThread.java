package org.vasileva.simbirsofttask.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "sourcethread")
public class SourceThread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "thread_id")
    private Long threadId;

    @Column(name = "thread_name")
    private String threadName;

    public SourceThread() {
    }
    public SourceThread(String threadName) {
        this.threadName = threadName;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public String toString() {
        return "[" + threadName + "]";
    }
}
