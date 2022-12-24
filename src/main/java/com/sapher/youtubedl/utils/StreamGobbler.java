package com.sapher.youtubedl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {

    private InputStreamReader stream;
    private StringBuffer buffer;

    public StreamGobbler(StringBuffer buffer, InputStreamReader stream) {
        this.stream = stream;
        this.buffer = buffer;
        start();
    }

    public void run() {
        try {
            int nextChar;
            while((nextChar = this.stream.read()) != -1) {
                this.buffer.append((char) nextChar);
            }
        }
        catch (IOException e) {

        }
    }
}