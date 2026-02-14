package com.imer1c.downloading;

import com.github.kiulian.downloader.Config;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeCallback;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.client.ClientType;
import com.github.kiulian.downloader.downloader.proxy.ProxyCredentialsImpl;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.downloader.response.ResponseStatus;
import com.github.kiulian.downloader.model.Extension;
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
        Config config = new Config.Builder()
                .maxRetries(1)
                .header("Accept-language", "en-US,en;") // extra request header
                .build();

        this.downloader = new YoutubeDownloader(config);
        this.link = link;
    }

    public VideoInfo parseDetails(boolean audio)
    {
        String videoId = Utils.decodeVideoId(link);
        VideoInfo info = this.info(videoId);
        VideoDetails details = info.details();

        System.out.println(details);


        this.audio = audio;
        this.format = audio ? info.bestAudioFormat() : null;
        this.details = details;

        if (audio)
        {
            this.format = info.bestAudioFormat();
        }
        else
        {
            this.format = info.bestVideoWithAudioFormat();
            System.out.println(info.bestVideoFormat().qualityLabel());
            //this.format = info.videoFormats().stream().filter(format -> format.extension() == Extension.MPEG4).min((o1, o2) -> o2.videoQuality().compare(o1.videoQuality())).orElse(null);
        }

        return info;
    }

    public void setFormat(Format format)
    {
        if (format == null)
        {
            return;
        }

        this.format = format;
    }

    private VideoInfo info(String videoId)
    {
        RequestVideoInfo info = new RequestVideoInfo(videoId)
                .clientType(ClientType.WEB_PARENT_TOOLS)
                .callback(new YoutubeCallback<VideoInfo>() {
                    @Override
                    public void onFinished(VideoInfo videoInfo)
                    {

                    }

                    @Override
                    public void onError(Throwable throwable)
                    {
                        throwable.printStackTrace();
                    }
                });
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
                .clientType(ClientType.WEB_PARENT_TOOLS)
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
