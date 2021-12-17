package com.osiris.jgbrowser;

import com.osiris.jgbrowser.exceptions.JavaScriptException;
import com.osiris.jgbrowser.window.JGWindow;
import com.osiris.jgbrowser.window.JGWindowBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Headless-Browser.
 *
 * @author Osiris-Team
 */
public class JGBrowser {
    private File mainDirectory = new File(System.getProperty("user.dir")+"/headless-browser");

    /**
     * Creates and returns a new window, built with defaults. <br>
     * Remember to close the window either by {@link #closeWindow} or
     * by creating the window in a try/catch blocks' method. <br>
     * By using the {@link JGWindowBuilder} or the {@link #openCustomWindow()} method <br>
     * you can create customized windows. <br>
     */
    public JGWindow openWindow() {
        return new JGWindowBuilder(this).build();
    }

    /**
     * Shortcut for opening a window and loading a page into it. <br>
     * See {@link #openWindow()} for details. <br>
     */
    public JGWindow openWindowAndLoad(String url) throws IOException, JavaScriptException {
        return openWindow().load(url);
    }

    /**
     * Returns the {@link JGWindowBuilder} to build custom window.
     */
    public JGWindowBuilder openCustomWindow() {
        return new JGWindowBuilder(this);
    }

    /**
     * Closes the provided {@link JGWindow}. <br>
     * Note that a {@link JGWindow} can automatically be closed like this:
     * <pre>
     * try(HWindow hWindow = openNewWindow()){
     *     // Do stuff here...
     * } // Gets automatically closed when leaving the try/catch block.
     * </pre>
     */
    public void closeWindow(JGWindow window) throws Exception {
        window.close();
    }

    public File getMainDirectory() {
        return mainDirectory;
    }

    public JGBrowser setMainDirectory(File mainDirectory) {
        this.mainDirectory = mainDirectory;
        return this;
    }
}
