package com.sapher.youtubedl.utils;

import com.sapher.youtubedl.DownloadProgressCallback;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.util.regex.Pattern;

public class StreamProcessExtractor extends Thread {

    private static final String GROUP_PERCENT = "percent";
    private static final String GROUP_MINUTES = "minutes";
    private static final String GROUP_SECONDS = "seconds";

    private static final Pattern PATTERN = Pattern.compile(
            "\\[download]\\s+(?<percent>\\d+\\.\\d)% .* ETA (?<minutes>\\d+):(?<seconds>\\d+)"
    );

    private final BufferedReader reader;
    private final StringBuffer buffer;
    private final DownloadProgressCallback callback;

    public StreamProcessExtractor(@NonNull StringBuffer buffer, @NonNull BufferedReader reader, @Nullable DownloadProgressCallback callback) {
        this.reader = reader;
        this.buffer = buffer;
        this.callback = callback;
        this.start();
    }

    public void run() {
        try {
            this.reader.lines().forEach(line -> {
                buffer.append(line).append(System.lineSeparator());
                if (callback != null) {
                    processOutputLine(line);
                }
            });
        } catch (Exception ignored) {}
    }

    private void processOutputLine(String line) {
        final var matcher = PATTERN.matcher(line);
        if (!matcher.matches()) return;

        final var progress = Float.parseFloat(matcher.group(GROUP_PERCENT));
        final var eta = convertToSeconds(matcher.group(GROUP_MINUTES), matcher.group(GROUP_SECONDS));

        callback.onProgressUpdate(progress, eta);
    }

    private int convertToSeconds(String minutes, String seconds) {
        return Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
    }
}
