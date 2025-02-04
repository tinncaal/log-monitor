package org.cata.lseg;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Main {

    public static void main(String[] args) {
        if(args.length == 0) {
            log.error("Usage: java -jar log-monitor.jar <log-file>");
            throw new IllegalStateException("No arguments provided");
        }

        if(!Files.exists(Path.of(args[0]))) {
            log.error("File not found: {}", args[0]);
            throw new IllegalStateException("File not found");
        }

        var parser = new Parser();
        parser.parseLog(args[0]);
    }

}