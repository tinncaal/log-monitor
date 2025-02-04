package org.cata.lseg;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;

public class LogEntry {
    private final String jobDescription;
    private final String pid;

    @Getter
    private final LocalTime startTime;

    @Getter
    @Setter
    private LocalTime endTime;

    public LogEntry(String jobDescription, String pid, LocalTime startTime) {
        this.jobDescription = jobDescription;
        this.pid = pid;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Job: %s, PID: %s, Duration: %s"
                .formatted(jobDescription, pid, endTime != null ? Duration.between(startTime, endTime).toMinutes() + "m" : "?");
    }
}
