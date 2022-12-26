package com.sapher.youtubedl;

import lombok.NonNull;
import java.util.*;

/**
 * Represent the request to be sent to the youtube-dl.
 */
public class YoutubeDLRequest {

    private String directory;
    private String url;

    private final Map<String, String> options = new HashMap<>();

    /**
     * Construct a request with empty options.
     */
    public YoutubeDLRequest() {}

    /**
     * Construct a request with a video url.
     *
     * @param url The video url.
     */
    public YoutubeDLRequest(@NonNull String url) {
        this.url = url;
    }

    /**
     * Construct a request with a video url and working directory
     *
     * @param url The video url.
     * @param directory The working directory.
     */
    public YoutubeDLRequest(@NonNull String url, @NonNull String directory) {
        this.url = url;
        this.directory = directory;
    }

    /**
     * Get the working directory.
     *
     * @return The working directory.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Set the working directory.
     *
     * @param directory The working directory.
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Get video url.
     *
     * @return The video url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set video url.
     *
     * @param url The video url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get request options.
     *
     * @return The options map.
     */
    public Map<String, String> getOption() {
        return options;
    }

    /**
     * Set request option.
     *
     * @param key The option.
     */
    public void setOption(@NonNull String key) {
        options.put(key, null);
    }

    /**
     * Set request option with value.
     *
     * @param key The option.
     * @param value The value.
     */
    public void setOption(@NonNull String key, @NonNull String value) {
        options.put(key, value);
    }

    /**
     * Set the request option with int value.
     *
     * @param key The option.
     * @param value The value.
     */
    public void setOption(@NonNull String key, int value) {
        options.put(key, String.valueOf(value));
    }

    /**
     * Remove the request option.
     *
     * @param key The option.
     */
    public void removeOption(@NonNull String key) {
        options.remove(key);
    }

    /**
     * Transform options to a string that the executable will execute.
     *
     * @return The command string.
     */
    protected String buildOptions() {
        final var builder = new StringBuilder();
        if (url != null) {
            builder.append(url).append(" ");
        }

        this.options.forEach((key, value) -> {
            String option = String.format("--%s %s", key, value == null ? "" : value).trim();
            builder.append(option).append(" ");
        });

        return builder.toString().trim();
    }
}
