package com.osiris.jgbrowser.exceptions;

import java.util.List;

public class JavaScriptException extends Exception {
    private final String message;

    public JavaScriptException(String message, List<String> errors) {
        super();
        message = message + "\n";
        if (errors != null)
            for (String line :
                    errors) {
                message = message + line + "\n";
            }
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
