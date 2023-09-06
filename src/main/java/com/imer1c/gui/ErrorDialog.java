package com.imer1c.gui;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ErrorDialog extends JDialog {

    private static final String SPACER = "".repeat(3);

    public ErrorDialog(Throwable t)
    {
        this.setTitle(t.getMessage());

        JTextArea area = new JTextArea();

        StringBuilder builder = new StringBuilder();

        for (StackTraceElement stackTraceElement : t.getStackTrace())
        {
            builder.append(SPACER).append(stackTraceElement.toString()).append(SPACER).append("\n");
        }

        area.setText(builder.toString());

        this.setLayout(null);
        this.setContentPane(area);
        this.pack();
        this.setVisible(true);
    }

    @Override
    protected void processWindowEvent(WindowEvent e)
    {
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            System.exit(0);
            return;
        }

        super.processWindowEvent(e);
    }
}
