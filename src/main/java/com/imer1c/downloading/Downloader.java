package com.imer1c.downloading;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.downloader.response.ResponseStatus;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.imer1c.gui.ErrorDialog;
import com.imer1c.gui.VideoComponent;
import com.imer1c.utils.Utils;

import java.io.File;

public class Downloader {

    private final YoutubeDownloader downloader;
    private final String link;
    private VideoDetails details;
    private Format format;
    private VideoComponent componentCallback;
    private boolean audio;
    private File output;
    private Response<File> response;

    public Downloader(String link)
    {
        this.downloader = new YoutubeDownloader();
        this.link = link;
    }

    public void parseDetails(boolean audio)
    {
        String videoId = Utils.decodeVideoId(link);
        VideoInfo info = this.info(videoId);
        VideoDetails details = info.details();

        info.audioFormats().forEach(audioFormat -> System.out.println(audioFormat.mimeType() + " - " + audioFormat.audioQuality()));

        this.audio = audio;
        this.format = audio ? info.bestAudioFormat() : info.bestVideoWithAudioFormat();
        this.details = details;
    }

    private VideoInfo info(String videoId)
    {
        RequestVideoInfo info = new RequestVideoInfo(videoId);
        Response<VideoInfo> videoInfo = this.downloader.getVideoInfo(info);
        return videoInfo.data();
    }

    public boolean isAudio()
    {
        return audio;
    }

    public VideoDetails getDetails()
    {
        return this.details;
    }

    public File getOutput()
    {
        return output;
    }

    public void setComponentCallback(VideoComponent componentCallback)
    {
        this.componentCallback = componentCallback;
    }

    public void start()
    {
        RequestVideoFileDownload download = new RequestVideoFileDownload(this.format)
                .renameTo(this.details.title())
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
                        throwable.printStackTrace();
                        new ErrorDialog(throwable);
                    }
                }).async();

        this.output = download.getOutputFile();

        this.response = this.downloader.downloadVideoFile(download);
    }

    public void del()
    {
        if (this.response.status() == ResponseStatus.downloading)
        {
            this.response.cancel();
        }

        this.output.delete();
    }
}
