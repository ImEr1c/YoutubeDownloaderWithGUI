package com.imer1c.downloading;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeCallback;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.imer1c.gui.VideoComponent;
import com.imer1c.utils.Utils;

import java.io.File;

public class Downloader {

    private final YoutubeDownloader downloader;
    private final String link;
    private String title;
    private Format format;
    private VideoComponent componentCallback;

    public Downloader(String link)
    {
        this.downloader = new YoutubeDownloader();
        this.link = link;
    }

    public void parseDetails(boolean mp3)
    {
        String videoId = Utils.decodeVideoId(link);
        VideoInfo info = this.info(videoId);
        VideoDetails details = info.details();

        info.audioFormats().forEach(audioFormat -> System.out.println(audioFormat.mimeType() + " - " + audioFormat.audioQuality()));

        this.format = info.bestVideoWithAudioFormat();
        this.title = details.title();
    }

    private VideoInfo info(String videoId)
    {
        RequestVideoInfo info = new RequestVideoInfo(videoId);
        Response<VideoInfo> videoInfo = this.downloader.getVideoInfo(info);
        return videoInfo.data();
    }

    public String getTitle()
    {
        return title;
    }

    public void setComponentCallback(VideoComponent componentCallback)
    {
        this.componentCallback = componentCallback;
    }

    public void start()
    {
        RequestVideoFileDownload download = new RequestVideoFileDownload(this.format)
                .renameTo(this.title)
                .saveTo(Utils.getDownloadsFolder())
                .callback(new YoutubeProgressCallback<>() {
                    @Override
                    public void onDownloading(int progress)
                    {
                        componentCallback.setProgress(progress);
                    }

                    @Override
                    public void onFinished(File data)
                    {
                        componentCallback.finished();
                    }

                    @Override
                    public void onError(Throwable throwable)
                    {

                    }
                }).async();

        //this.downloader.downloadVideoFile(download);
    }
}
