package com.imer1c;

import com.imer1c.downloading.Downloader;
import com.imer1c.gui.ErrorDialog;
import com.imer1c.gui.MainGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            new ErrorDialog(e);
        });

        Client.getInstance();
    }
}