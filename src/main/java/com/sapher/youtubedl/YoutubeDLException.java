package com.sapher.youtubedl;

import lombok.NonNull;

/**
 * The YoutubeDlException is called when an error occurs
 * during the execution of the youtube-dl.
 */
public class YoutubeDLException extends RuntimeException {

    /**
     * Construct YoutubeDLException with a message.
     *
     * @param message The message.
     */
    public YoutubeDLException(@NonNull String message) {
        super(message);
    }

    /**
     * Construct YoutubeDLException from another exception.
     *
     * @param exception An exception.
     */
    public YoutubeDLException(@NonNull Exception exception) {
        super(exception.getMessage());
    }

}