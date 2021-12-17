package com.osiris.jgbrowser.window;

import com.osiris.jgbrowser.JGBrowser;
import com.osiris.jgbrowser.exceptions.JavaScriptException;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

public class JGWindowBuilder {
    /**
     * The {@link JGBrowser} this window was started from.
     */
    public final JGBrowser parentBrowser;
    /**
     * Enable/Disable JavaScript code execution for this window.
     */
    public boolean enableJavaScript = true;
    /**
     * Default is null. Otherwise the provided headers will be used instead of the default ones.
     */
    public Map<String, String> customHeaders = null;
    /**
     * Default is null. Otherwise, writes/prints debug related information and JavaScript code console output to the provided {@link OutputStream}.
     */
    public OutputStream debugOutputStream = null;
    /**
     * Default is 30s. The timeout in seconds to wait before throwing a {@link JavaScriptException}, if the running js code didn't finish. Set to 0 to disable.
     */
    public int jsTimeout = 30;
    /**
     * Whether to run browser in headless mode. Defaults to true unless the devtools option is true.
     */
    public boolean isHeadless = true;
    /**
     * Path to a User Data Directory. Default is ./headless-browser/user-data (the "." represents the current working directory).
     */
    public File userDataDir;
    /**
     * Whether to auto-open a DevTools panel for each tab. If this option is true, the headless option will be set false.
     */
    public boolean isDevTools = false;
    /**
     * Default is 0. Specify custom debugging port. Pass 0 to discover a random port.
     */
    public int debuggingPort = 0;
    /**
     * Default is null. Additional arguments to pass to the browser instance. The list of Chromium flags can be found here: https://peter.sh/experiments/chromium-command-line-switches/
     */
    public String[] additionalStartupArgs = null;
    /**
     * Makes this window indistinguishable from 'real', user operated windows, by using the code from puppeteer/playwright-extra and puppeteer-extra-plugin-stealth.
     */
    public boolean makeUndetectable = false;
    /**
     * If true, a new, unique, temporary directory will be created. <br>
     * The value of {@link #userDataDir} will be overwritten. <br>
     * Example: ./headless-browser/user-data-89213<br>
     * As you can see, the directory name will contain the {@link JGWindow} objects unique {@link #hashCode()} as {@link Integer#toHexString(int)}. <br>
     * The directory will get deleted on {@link JGWindow#close()}. <br>
     */
    public boolean temporaryUserDataDir = false;

    public JGWindowBuilder(JGBrowser parentBrowser) {
        this.parentBrowser = parentBrowser;
        this.userDataDir = new File(parentBrowser.getMainDirectory() + "/user-data");
    }

    public JGWindow build() {
        return new JGWindow(this.parentBrowser, this.enableJavaScript, this.customHeaders, this.debugOutputStream);
    }

    /**
     * For details see {@link #makeUndetectable}.
     */
    public JGWindowBuilder makeUndetectable(boolean val) {
        this.makeUndetectable = val;
        return this;
    }

    /**
     * For details see {@link #customHeaders}.
     */
    public JGWindowBuilder customHeaders(Map<String, String> val) {
        this.customHeaders = val;
        return this;
    }

    /**
     * For details see {@link #enableJavaScript}.
     */
    public JGWindowBuilder enableJavaScript(boolean val) {
        this.enableJavaScript = val;
        return this;
    }

    /**
     * For details see {@link #debugOutputStream}
     */
    public JGWindowBuilder debugOutputStream(OutputStream val) {
        this.debugOutputStream = val;
        return this;
    }

    /**
     * For details see {@link #jsTimeout}.
     */
    public JGWindowBuilder jsTimeout(int val) {
        this.jsTimeout = val;
        return this;
    }

    /**
     * For details see {@link #isHeadless}.
     */
    public JGWindowBuilder headless(boolean val) {
        this.isHeadless = val;
        return this;
    }

    /**
     * For details see {@link #userDataDir}.
     */
    public JGWindowBuilder userDataDir(File val) {
        this.userDataDir = val;
        return this;
    }

    /**
     * For details see {@link #isDevTools}.
     */
    public JGWindowBuilder devTools(boolean val) {
        this.isDevTools = val;
        return this;
    }

    /**
     * For details see {@link #debuggingPort}.
     */
    public JGWindowBuilder debuggingPort(int val) {
        this.debuggingPort = val;
        return this;
    }

    /**
     * For details see {@link #additionalStartupArgs}.
     */
    public JGWindowBuilder additionalStartupArgs(String... val) {
        this.additionalStartupArgs = val;
        return this;
    }

    /**
     * For details see {@link #temporaryUserDataDir}.
     */
    public JGWindowBuilder temporaryUserDataDir(boolean val) {
        this.temporaryUserDataDir = val;
        return this;
    }

}
