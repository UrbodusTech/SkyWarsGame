# Add messages to a lang file from an extension
It is possible that when you need to program an extension and you want to apply the language and translation system you need to add more messages to the lang files. The easiest way is to add them in the main ones located in plugins/SkyWarsGame/language, but there is also an alternative so you can add more messages from an extension.

```java
public class ExampleExtension extends Extension {

    @Override
    public void install() {
        getLangManager().getLangFile("en_US").appendMessage(
                "OTHER_MESSAGE",
                "&aHello &e{0}"
        );
    }

    @Override
    public void uninstall() {

    }
}
```

NOTE: New messages are not saved in the original files.