<div align="center">
    <h1 align="center">youtubedl-java</h1>
    <a target="_blank" href="https://jitpack.io/#srblecaute01/youtubedl-java">
        <img src="https://img.shields.io/jitpack/v/github/SrBlecaute01/youtubedl-javar?label=Snapshots&color=lime_green/" alt="Jitpack">
    </a>
</div>

A simple java wrapper for [yt-dlp](https://github.com/yt-dlp/yt-dlp) executable

There's a lot of thing left to do. Parsing output is one of them. Too bad, yt-dlp doesn't output formatted data.

## Prerequisites 

For installation, you need:

-   yt-dlp installed and available in the environment variables.
-   Java 17 or higher.

[How to properly install yt-dlp executable](https://github.com/yt-dlp/yt-dlp/wiki/Installation)


## Installation

You can use [jitpack.io](https://jitpack.io/#srblecaute01/youtubedl-java) to add the library to your project.

### Gradle

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
        implementation 'com.github.srblecaute01:youtubedl-java:${VERSION}'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
        
<dependencies>
    <dependency>
        <groupId>com.github.srblecaute01</groupId>
        <artifactId>youtubedl-java</artifactId>
        <version>${VERSION}</version>
    </dependency>
</dependencies>
```

## Making requests

```java
// Video url to download
String videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

// Destination directory
String directory = System.getProperty("user.home");

// Build request
YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, directory);
request.setOption("ignore-errors");		// --ignore-errors
request.setOption("output", "%(id)s");	// --output "%(id)s"
request.setOption("retries", 10);		// --retries 10

// Make request and return response
YoutubeDLResponse response = YoutubeDL.execute(request);

// Response
String stdOut = response.getOut(); // Executable output
```

You may also specify a callback to get notified about the progress of the download:

```java
YoutubeDLResponse response = YoutubeDL.execute(request, new DownloadProgressCallback() {
      @Override
      public void onProgressUpdate(float progress, long etaInSeconds) {
          System.out.println(String.valueOf(progress) + "%");
      }
});
```

# Useful links

-   [yt-dlp documentation](https://github.com/yt-dlp/yt-dlp/wiki)
