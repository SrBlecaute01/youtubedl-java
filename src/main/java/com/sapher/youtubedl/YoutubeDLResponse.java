package com.sapher.youtubedl;

import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Represents the response from youtube-dl.
 */
@RequiredArgsConstructor
public class YoutubeDLResponse {

    private final String command;
    private final Map<String, String> options;
    private final String directory;
    private final int exitCode;
    private final long elapsedTime;
    private final String out;
    private final String err;

    /**
     * Get the command that was executed.
     *
     * @return The command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get the program exit code.
     *
     * @return The exit code.
     */
    public int getExitCode() {
        return exitCode;
    }

    /**
     * Get program output.
     *
     * @return The output.
     */
    public String getOut() {
        return out;
    }

    /**
     * Get program error output.
     *
     * @return The error output.
     */
    public String getErr() {
        return err;
    }

    /**
     * Get command options.
     *
     * @return The options.
     */
    public Map<String, String> getOptions() {
        return options;
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
     * Get program execution time.
     *
     * @return The execution time.
     */
    public long getElapsedTime() {
        return elapsedTime;
    }
}
