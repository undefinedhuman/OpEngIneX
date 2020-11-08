package de.undefinedhuman.core.language;

import de.undefinedhuman.core.config.SettingsManager;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.log.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class LanguageManager extends Manager {

    public static LanguageManager instance;

    private HashMap<String, String> languageList;
    private String languageName;

    public LanguageManager() {
        languageList = new HashMap<>();
        if (instance == null) instance = this;
    }

    @Override
    public void init() {
        languageName = SettingsManager.instance.language.getString();
        if (!loadLanguage(languageName))
            loadLanguage(languageName = Variables.DEFAULT_LANGUAGE);
    }

    public boolean loadLanguage(String languageName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
        try {
            FsFile file = new FsFile(Paths.LANGUAGE_PATH, Variables.LANGUAGES_FILE_NAME, false);
            builder = factory.newDocumentBuilder();
            if (builder == null || !file.exists()) return false;
            Document doc = builder.parse(file.getFile());
            if (doc == null) return false;
            doc.getDocumentElement().normalize();
            NodeList languages = doc.getElementsByTagName("language");
            if (languages == null) return false;
            boolean loaded = false;
            for (int i = 0; i < languages.getLength(); i++) {
                Node language = languages.item(i);
                if ((language.getNodeType() == Node.ELEMENT_NODE) && getValue("name", language).equals(languageName)) {
                    loaded = true;
                    NodeList values = ((Element) language).getElementsByTagName("string");
                    for (int j = 0; j < values.getLength(); j++) {
                        Node value = values.item(j);
                        if (value == null) return false;
                        languageList.put(getValue("key", value), getValue("value", value).replace("&lt;br /&gt;&lt;br /&gt;", "\n"));
                    }
                }
            }
            Log.info("Successfully loaded language: " + languageName);
            return loaded;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Log.error("Error while loading language " + languageName);
            Log.error(ex.getMessage());
        }
        return false;
    }

    private String getValue(String key, Node node) {
        if (node == null) return "ERROR! Could not load value for " + key;
        Node item = node.getAttributes().getNamedItem(key);
        if (item == null) return "ERROR! Could not load value for " + key;
        return item.getNodeValue();
    }

    @Override
    public void delete() {
        languageList.clear();
    }

    public String getLanguageName() {
        return languageName;
    }

    public String getString(String key, Replacement... replacements) {
        if (languageList == null) return languageName + " not found!";
        String s = languageList.get(key);
        if (s == null) return key + " not found!";
        for (Replacement replacement : replacements) s = s.replaceAll(replacement.getName(), replacement.getValue());
        return s;
    }

}
