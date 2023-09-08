package com.imer1c;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.imer1c.gui.ErrorDialog;

public class Main {
    public static void main(String[] args)
    {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            new ErrorDialog(e);
        });

        FlatMacLightLaf.setup();

        Client.getInstance();
    }
}