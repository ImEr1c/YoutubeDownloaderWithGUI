package com.imer1c.downloading;

import com.imer1c.Client;
import com.imer1c.gui.MainGUI;
import com.imer1c.gui.VideosListComponent;

import java.util.ArrayList;
import java.util.List;

public class DownloadSystem {
    private final List<Downloader> downloaderList = new ArrayList<>();
    private final Client client;
    private VideosListComponent guiCallback;

    public DownloadSystem(Client client)
    {
        this.client = client;
    }

    public void setGuiCallback(VideosListComponent guiCallback)
    {
        this.guiCallback = guiCallback;
    }

    public void parseAndStart(String link, boolean mp3)
    {
        Downloader downloader = new Downloader(link);
        this.downloaderList.add(0, downloader);

        downloader.parseDetails(mp3);

        this.guiCallback.addToList(downloader);
        downloader.start();
    }
}
