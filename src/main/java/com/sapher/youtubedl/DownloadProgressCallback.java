package com.sapher.youtubedl;

/**
 * Represents the youtube-dl download progress.
 */
@FunctionalInterface
public interface DownloadProgressCallback {

    void onProgressUpdate(float progress, long etaInSeconds);

}