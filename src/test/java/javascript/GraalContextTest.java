package javascript;

import com.osiris.jgbrowser.JGBrowser;
import com.osiris.jgbrowser.js.context.GraalContext;
import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Callable;

class GraalContextTest {

    public static void main(String[] args) {
        try (Context context = Context.newBuilder()
                .allowAllAccess(true)
                .build()) {
            context.getBindings("js").putMember("javaObj", new MyClass());
            boolean valid = context.eval("js",
                            "    javaObj.id         == 42" +
                                    " && javaObj.text       == '42'" +
                                    " && javaObj.arr[1]     == 42" +
                                    " && javaObj.ret42()    == 42")
                    .asBoolean();
            context.eval("js", "javaObj.print('HELLO!!!');");
            assert valid == true;
        }
    }

    @Test
    void testConsoleApi() throws IOException {
        JGBrowser JGBrowser = new JGBrowser();
        GraalContext graalContext = JGBrowser.openCustomWindow().build().getJavaScriptContext();
        graalContext.getConsole().onLog(msg -> System.out.println("JavaScript message received: " + msg));
        graalContext.eval("console.log('john stamos');");
    }

    @Test
    void testContextWebApis() throws IOException {
        JGBrowser browser = new JGBrowser();
        GraalContext graalContext = browser.openCustomWindow().build().getJavaScriptContext();
        graalContext.eval("console.log('hi!');");
    }

    public static class MyClass {
        public int id = 42;
        public String text = "42";
        public int[] arr = new int[]{1, 42, 3};
        public Callable<Integer> ret42 = () -> 42;

        public void print(String msg) {
            System.out.println(msg);
        }
    }
}