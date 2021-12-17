package com.osiris.jgbrowser.window;


import com.osiris.jgbrowser.JGBrowser;
import com.osiris.jgbrowser.data.chrome.ChromeHeaders;
import com.osiris.jgbrowser.js.context.GraalContext;
import com.osiris.jgbrowser.utils.TrashOutput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Headless-Window with GraalJS as JavaScript engine.
 *
 * @author Osiris-Team
 */
public class JGWindow implements AutoCloseable {
    private final GraalContext graalContext;
    private JGBrowser parentBrowser;
    private boolean enableJavaScript;
    private Map<String, String> customHeaders;
    private Document document;
    private String authority;
    private String javaScriptCode;
    private final PrintStream out;

    /**
     * <p style="color: red;">Note that this is not the recommended way of creating the window object.</p>
     * Use the {@link JGWindowBuilder} instead. The {@link JGBrowser} has a shortcut method for creating custom windows: {@link JGBrowser#openCustomWindow()}.
     */
    public JGWindow(JGBrowser parentBrowser, boolean enableJavaScript, Map<String, String> customHeaders, OutputStream debugOutput) {
        this.parentBrowser = parentBrowser;
        this.enableJavaScript = enableJavaScript;
        this.customHeaders = customHeaders;
        if (debugOutput==null)
            debugOutput = new TrashOutput();
        this.out = new PrintStream(debugOutput);

        graalContext = new GraalContext(this);
    }

    /**
     * Load the contents from the provided url into the current {@link JGWindow}.
     *
     * @param url Examples: https://www.wikipedia.org or wikipedia.org.
     * @return the current {@link JGWindow} for chained method calls.
     * @throws IOException
     */
    public JGWindow load(String url) throws IOException {
        if (!url.startsWith("http"))
            url = "https://" + url;

        Map<String, String> headers = null;
        if (this.customHeaders == null)
            headers = new ChromeHeaders().getMap();
        else
            headers = this.customHeaders;

        out.println("Loading contents into "+this+" from: "+url);
        authority = new URL(url).getAuthority();
        document = Jsoup.connect(url).headers(headers)
                .get();

        if (enableJavaScript) {
            int scriptElements = 0;
            javaScriptCode = "";
            for (Element e :
                    document.getElementsByTag("script")) {
                if (e.hasAttr("src")) {
                    String externalScriptUrl = e.attr("src");
                    if (!externalScriptUrl.startsWith("http")) {
                        if (externalScriptUrl.startsWith("/"))
                            externalScriptUrl = "https://" + authority + externalScriptUrl;
                        else
                            externalScriptUrl = "https://" + authority + "/" + externalScriptUrl;
                    }

                    javaScriptCode = javaScriptCode + "\n" +
                            "//\n" +
                            "// Following lines are external JS-Code from " + externalScriptUrl + "\n" +
                            "//\n" +
                            "\n" +
                            "" + new String(Jsoup.connect(externalScriptUrl).ignoreContentType(true)
                            .get()
                            .connection().response().bodyAsBytes(), StandardCharsets.UTF_8);
                } else {
                    javaScriptCode = javaScriptCode + "\n" +
                            "//\n" +
                            "// Following lines are JS-Code from <script> number " + (scriptElements++) + "\n" +
                            "//\n" +
                            "\n" +
                            "" + e.data();
                }

                // Execute code
                out.println("Executing JavaScript code: \n"+javaScriptCode);
                graalContext.eval(javaScriptCode);
            }
        }
        return this;
    }

    /**
     * Returns the current HTML-Document. <br>
     * If no page has been loaded this will return null. <br>
     */
    public Document getDocument() {
        return document;
    }

    public GraalContext getJavaScriptContext() {
        return graalContext;
    }

    /**
     * Returns the JavaScript code extracted from the pages script elements. <br>
     * If no page has been loaded this will return null. <br>
     */
    public String getLoadedJavaScriptCode() {
        return javaScriptCode;
    }

    /**
     * Executes the provided JavaScript code in the current context. <br>
     * See {@link GraalContext} for details. <br>
     */
    public JGWindow executeJS(String jsCode) {
        graalContext.eval(jsCode);
        return this;
    }

    public String getAuthority() {
        return authority;
    }

    @Override
    public void close() {
        graalContext.close();
    }

    public JGBrowser getParentBrowser() {
        return parentBrowser;
    }

    public void setParentBrowser(JGBrowser parentBrowser) {
        this.parentBrowser = parentBrowser;
    }

    public boolean isEnableJavaScript() {
        return enableJavaScript;
    }

    public void setEnableJavaScript(boolean enableJavaScript) {
        this.enableJavaScript = enableJavaScript;
    }

    public Map<String, String> getCustomHeaders() {
        return customHeaders;
    }

    public void setCustomHeaders(Map<String, String> customHeaders) {
        this.customHeaders = customHeaders;
    }

    public PrintStream getOut() {
        return out;
    }
}
