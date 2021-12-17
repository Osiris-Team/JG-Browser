# JG-Browser
The Java-Graal Browser, is a headless browser with latest JavaScript support, completely written in Java.

```java
JGBrowser browser = new JGBrowser();
try(JGWindow window = browser.openWindow()){
    window.load("https://example.com");
   // ...   
}
```

### Important
Even though this browser has latest JavaScript support, browsers like Google Chrome,
Firefox etc. provide additional JavaScript APIs, called Web-APIs, which practically every
modern webpage uses.

Since this browser is meant to be completely written in Java, we must also write/implement
all of those Web-APIs by ourselves.

For one person alone that would take decades, that's why this project is
designed in a modular way, so that everyone can add the functionality that he/she needs.
See [how-to-implement-a-js-web-api.md](how-to-implement-a-js-web-api.md) for details.

In short, this browser will fail executing most of the JavaScript code that relies on Web-APIs
at the moment.

### Links
- Checkout [Headless-Browser](https://github.com/Osiris-Team/Headless-Browser), which has NodeJS and Playwright implemented into Java,
if you want something production ready.

### Installation
- Java 8 or higher required
- Steps for [Maven/Gradle/Sbt/Leinigen](https://jitpack.io/#Osiris-Team/JG-Browser/LATEST)
- Click on 'Watch' -> 'Custom' -> and tick the 'Releases' box to get notified of future releases

### Motivation
HtmlUnit is good, but relies on another JavaScript engine with a lot less features. Thus,
this project exists.

### Features
- Latest JavaScript support via GraalJS 
- Easy HTML handling with Jsoup
- Easy Web-API implementation
- Completely written in Java

### Libraries
Note that this may be outdated. Check the [pom.xml](pom.xml) file for all dependencies.

| Name/Link | Usage | License |
| :-----: | :-----: | :-----: |
| [GraalJS](https://github.com/oracle/graaljs) | Enables executing JavaScript code from Java | [License](https://github.com/oracle/graaljs/blob/master/LICENSE) |
| [Jsoup](https://github.com/jhy/jsoup)      | Used to load pages and modify their HTML code      |   [License](https://github.com/jhy/jsoup/blob/master/LICENSE) |

