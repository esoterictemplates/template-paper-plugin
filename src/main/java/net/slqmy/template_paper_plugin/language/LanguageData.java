package net.slqmy.template_paper_plugin.language;

import java.util.Map;

public class LanguageData {

    private final String name;
    private final Map<Message, String> messages;
    
    public LanguageData(String name, Map<Message, String> messages) {
        this.name = name;
        this.messages = messages;
    }
}
