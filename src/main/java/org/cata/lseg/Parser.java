package org.cata.lseg;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Parser {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final long WARNING_THRESHOLD = 5 * 60; // 5 minutes
    private static final long ERROR_THRESHOLD = 10 * 60; // 10 minutes

    public void parseLog(String logFile) {
        var jobMap = new HashMap<String, LogEntry>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLogLine(line, jobMap);
            }
        } catch (IOException e) {
            log.error("Error reading log file: ", e);
        }

        generateReport(jobMap);
    }

    private void processLogLine(String line, Map<String, LogEntry> jobMap) {
        var parts = line.split(",");
        if (parts.length != 4) {
            log.error("Invalid log entry: {}", line);
            return;
        }

        var timestamp = LocalTime.parse(parts[0].trim(), TIME_FORMAT);
        var jobDescription = parts[1].trim();
        var eventType = parts[2].trim();
        var pid = parts[3].trim();

        switch (eventType) {
            case "START" -> jobMap.put(pid, new LogEntry(jobDescription, pid, timestamp));
            case "END" -> {
                LogEntry entry = jobMap.get(pid);
                if (entry != null) {
                    entry.setEndTime(timestamp);
                } else {
                    log.warn("Missing START, {}", line);
                }
            }
            default -> log.error("Invalid event type: {}", line);
        }
    }

    private void generateReport(Map<String, LogEntry> jobMap) {
        for (var entry : jobMap.values()) {
            if (entry.getEndTime() == null) {
                log.warn("Missing END, {}", entry);
                continue;
            }

            var duration = Duration.between(entry.getStartTime(), entry.getEndTime()).getSeconds();
            if (duration > ERROR_THRESHOLD) {
                log.error("+10m, {}", entry);
            } else if (duration > WARNING_THRESHOLD) {
                log.warn("+5m,  {}", entry);
            }
        }
    }
}
