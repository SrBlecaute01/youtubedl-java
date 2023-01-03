package com.sapher.youtubedl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapher.youtubedl.mapper.VideoFormat;
import com.sapher.youtubedl.mapper.VideoInfo;
import com.sapher.youtubedl.mapper.VideoThumbnail;
import com.sapher.youtubedl.utils.StreamGobbler;
import com.sapher.youtubedl.utils.StreamProcessExtractor;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>Provide an interface for youtube-dl executable</p>
 *
 * <p>
 *     For more information on youtube-dl, please see
 *     <a href="https://github.com/rg3/youtube-dl/blob/master/README.md">YoutubeDL Documentation</a>
 * </p>
 */
public class YoutubeDL {

    /**
     * Youtube-dl executable name.
     */
    protected static String executablePath = "yt-dlp";

    /**
     * The object mapper.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Append executable name to command.
     *
     * @param command The command string.
     *
     * @return The command string.
     */
    protected static String buildCommand(String command) {
        return String.format("%s %s", executablePath, command);
    }

    /**
     * Execute youtube-dl request.
     *
     * @param request The request object.
     *
     * @return The youtube-dl response.
     *
     * @throws YoutubeDLException If an error occurs.
     */
    @Contract("_ -> new")
    public static @NotNull YoutubeDLResponse execute(@NonNull YoutubeDLRequest request) throws YoutubeDLException {
        return execute(request, null);
    }

    /**
     * Execute youtube-dl request.
     *
     * @param request The request object.
     * @param callback The progress callback.
     *
     * @return The youtube-dl response.
     *
     * @throws YoutubeDLException If an error occurs.
     */
    @Contract("_, _ -> new")
    public static @NotNull YoutubeDLResponse execute(@NonNull YoutubeDLRequest request,
                                                     @Nullable DownloadProgressCallback callback) throws YoutubeDLException {

        final var command = buildCommand(request.buildOptions());

        final var directory = request.getDirectory();
        final var options = request.getOption();

        final var outBuffer = new StringBuffer();
        final var errBuffer = new StringBuffer();

        final var processBuilder = new ProcessBuilder(command.split(" "));

        // Define directory if one is passed
        if (directory != null) {
            processBuilder.directory(new File(directory));
        }

        try {
            final var startTime = System.nanoTime();
            final var process = processBuilder.start();
            final var charset = StandardCharsets.UTF_8;

            try (final var outStream = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
                 final var errStream = new BufferedReader(new InputStreamReader(process.getErrorStream(), charset))) {

                new StreamProcessExtractor(outBuffer, outStream, callback).join();
                new StreamGobbler(errBuffer, errStream).join();

                final var out = outBuffer.toString();
                final var err = errBuffer.toString();

                long elapsedTime = (System.nanoTime() - startTime) / 1000000;

                int exitCode = process.waitFor();
                if (exitCode > 0) {
                    throw new YoutubeDLException(err);
                }

                return new YoutubeDLResponse(command, options, directory, exitCode, elapsedTime, out, err);
            }

        } catch (IOException | InterruptedException exception) {
            throw new YoutubeDLException(exception);
        }
    }

    /**
     * Retrieve youtube-dl executable version.
     *
     * @return The version string.
     *
     * @throws YoutubeDLException If an error occurs.
     */
    public static String getVersion() throws YoutubeDLException {
        YoutubeDLRequest request = new YoutubeDLRequest();
        request.setOption("version");
        return YoutubeDL.execute(request).getOut();
    }

    /**
     * Retrieve all information available on a video.
     *
     * @param url The video url.
     * @return The video information.
     *
     * @throws YoutubeDLException if an error occurs.
     */
    public static VideoInfo getVideoInfo(@NonNull String url) throws YoutubeDLException {
        // Build request
        YoutubeDLRequest request = new YoutubeDLRequest(url);

        request.setOption("dump-json");
        request.setOption("no-playlist");

        YoutubeDLResponse response = YoutubeDL.execute(request);

        try {
            return MAPPER.readValue(response.getOut(), VideoInfo.class);
        } catch (IOException e) {
            throw new YoutubeDLException("Unable to parse video information: " + e.getMessage());
        }
    }

    /**
     * Retrieve available formats of a video.
     *
     * @param url The Video url.
     * @return The list of formats.
     *
     * @throws YoutubeDLException if an error occurs.
     */
    public static List<VideoFormat> getFormats(@NonNull String url) throws YoutubeDLException {
        return getVideoInfo(url).formats;
    }

    /**
     * Retrieve thumbnails of a video.
     *
     * @param url The video url.
     * @return The list of thumbnails.
     *
     * @throws YoutubeDLException if an error occurs.
     */
    public static List<VideoThumbnail> getThumbnails(@NonNull String url) throws YoutubeDLException {
        return getVideoInfo(url).thumbnails;
    }

    /**
     * Retrieve categories of a video.
     *
     * @param url The video url.
     * @return The list of categories.
     *
     * @throws YoutubeDLException if an error occurs.
     */
    public static List<String> getCategories(@NonNull String url) throws YoutubeDLException {
        return getVideoInfo(url).categories;
    }

    /**
     * Retrieve tags of a video.
     *
     * @param url The video url.
     * @return The list of tags.
     *
     * @throws YoutubeDLException if an error occurs.
     */
    public static List<String> getTags(@NonNull String url) throws YoutubeDLException {
        return getVideoInfo(url).tags;
    }

    /**
     * Get command executable or path to the executable.
     *
     * @return path string.
     */
    public static String getExecutablePath(){
        return executablePath;
    }

    /**
     * Set path to use for the command
     * @param path String path to the executable
     */
    public static void setExecutablePath(@NonNull String path){
        executablePath = path;
    }
}
