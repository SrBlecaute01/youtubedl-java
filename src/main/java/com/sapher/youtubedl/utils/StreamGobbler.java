package com.sapher.youtubedl.utils;

import lombok.NonNull;

import java.io.BufferedReader;

public class StreamGobbler extends Thread {

    private final BufferedReader reader;
    private final StringBuffer buffer;

    public StreamGobbler(@NonNull StringBuffer buffer, @NonNull BufferedReader reader) {
        this.reader = reader;
        this.buffer = buffer;
        this.start();
    }

    public void run() {
        try {
            reader.lines().forEach(line -> this.buffer
                    .append(line)
                    .append(System.lineSeparator()));

        } catch (Exception ignored) {}
    }
}