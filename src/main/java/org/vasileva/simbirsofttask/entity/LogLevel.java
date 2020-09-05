package org.vasileva.simbirsofttask.entity;

import javax.persistence.*;


@Entity
@Table(name = "loglevel")
public class LogLevel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "level_id")
    private Long levelId;

    @Column(name="description")
    private String description;

    public LogLevel() {
    }
    public LogLevel(String description) {
        this.description = description;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long id) {
        this.levelId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description.toUpperCase();
    }
}
