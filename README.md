# Coding Challenge: Log Monitoring Application
This project is addressed to a specific requestor. It is a java POC application based on document `Log Monitoring Application 1.pdf` which due to possible copyright issues, I will not upload to this repo.

## Design
The application is designed to read a log file and parse it to extract the required information. The log file is expected to have the following format:
```log
11:35:23,scheduled task 032, START,37980
11:35:56,scheduled task 032, END,37980
11:36:11,scheduled task 796, START,57672
11:36:18,scheduled task 796, END,57672
```

The application will parse the log file and extract the following information:
- Log level (ERROR, WARN)
- Time of log entry
- Job name
- PID
- Duration
- Missing END
- Missing START

## Setup
Maven Spring Boot project, with prerequisites:
- Java 17+
- Maven ver 3+

## Build
Build fat jar:
 ```shell
# from ./log-monitor dir
 mvn clean package
```

## Run
```shell
java -jar log-monitor.jar <path_to_log_file>
``` 

## Typical output:
```log
ERROR +10m, Job: background job wmy, PID: 81258, Duration: 14m
ERROR +10m, Job: scheduled task 051, PID: 39547, Duration: 11m
WARN  +5m,  Job: scheduled task 746, PID: 98746, Duration: 7m
WARN  +5m,  Job: scheduled task 794, PID: 87570, Duration: 7m
ERROR +10m, Job: scheduled task 182, PID: 70808, Duration: 33m
ERROR +10m, Job: scheduled task 064, PID: 85742, Duration: 12m
WARN  +5m,  Job: scheduled task 811, PID: 50295, Duration: 6m
ERROR +10m, Job: scheduled task 004, PID: 22003, Duration: 11m
WARN  +5m,  Job: scheduled task 672, PID: 24482, Duration: 8m
ERROR +10m, Job: scheduled task 936, PID: 62401, Duration: 10m
WARN  +5m,  Job: background job xfg, PID: 86716, Duration: 5m
WARN  +5m,  Job: scheduled task 074, PID: 71766, Duration: 5m
WARN  Missing END, Job: scheduled task 016, PID: 72897, Duration: ?
WARN  +5m,  Job: background job sqm, PID: 99672, Duration: 5m
ERROR +10m, Job: scheduled task 374, PID: 23703, Duration: 13m
WARN  Missing END, Job: scheduled task 333, PID: 72029, Duration: ?
ERROR +10m, Job: scheduled task 515, PID: 45135, Duration: 12m
WARN  +5m,  Job: scheduled task 294, PID: 27222, Duration: 6m
ERROR +10m, Job: scheduled task 460, PID: 39860, Duration: 19m
WARN  +5m,  Job: scheduled task 268, PID: 87228, Duration: 9m
ERROR +10m, Job: background job tqc, PID: 52532, Duration: 13m
```

## Testing
Test units are provided in the `src/test` directory. To run the tests:
```shell
mvn test
```