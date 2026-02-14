package com.imer1c.downloading;

import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.imer1c.Client;
import com.imer1c.gui.MainGUI;
import com.imer1c.gui.VideosListComponent;

import java.util.ArrayList;
import java.util.List;

public class DownloadSystem {
    private final List<Downloader> downloaderList = new ArrayList<>();
    private final Client client;
    private VideosListComponent guiCallback;
    private Downloader lastDownloader;

    public DownloadSystem(Client client)
    {
        this.client = client;
    }

    public void setGuiCallback(VideosListComponent guiCallback)
    {
        this.guiCallback = guiCallback;
    }

    public VideoInfo parse(String link, boolean audio)
    {
        Downloader downloader = new Downloader(link);
        this.downloaderList.add(0, downloader);

        lastDownloader = downloader;

        return downloader.parseDetails(audio);
    }

    public boolean hasDownloader()
    {
        return lastDownloader != null;
    }

    public void startLast()
    {
        this.guiCallback.addToList(lastDownloader);
        lastDownloader.start();

        lastDownloader = null;
    }
}
