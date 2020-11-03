package de.undefinedhuman.core.settings.types;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.utils.Tools;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StringArraySetting extends Setting {

    private JTextField valueField;

    public StringArraySetting(String key, String[] value) {
        super(SettingType.StringArray, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        LineSplitter splitter = (LineSplitter) value;
        String[] values = new String[splitter.getNextInt()];
        for(int i = 0; i < values.length; i++) values[i] = splitter.getNextString();
        setValue(values);
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeString(key).writeInt(getStringArray().length);
        for(String s : getStringArray()) writer.writeString(s);
    }

    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        valueField = createTextField(Tools.convertArrayToString(getStringArray()), position, new Vector2f(200, 25), new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(valueField.getText() == null || valueField.getText().equalsIgnoreCase("")) return;
                String[] array = valueField.getText().split(";");
                setValue(array);
            }
        });
        panel.add(valueField);
    }

    @Override
    protected void setValueInMenu(Object value) {
        if(valueField != null) valueField.setText(Tools.convertArrayToString(getStringArray()));
    }

}
