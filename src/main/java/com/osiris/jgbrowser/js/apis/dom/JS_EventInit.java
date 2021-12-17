package com.osiris.jgbrowser.js.apis.dom;

import org.graalvm.polyglot.HostAccess;

public class JS_EventInit {

    @HostAccess.Export
    boolean bubbles = false;

    @HostAccess.Export
    boolean cancelable = false;

    @HostAccess.Export
    boolean composed = false;
}
