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

public class Vector2ArraySetting extends Setting {

    protected JTextField valueField;

    public Vector2ArraySetting(String key, Vector2f[] value) {
        super(SettingType.Vector2Array, key, value);
    }

    public Vector2ArraySetting(SettingType type, String key, Vector2f[] value) {
        super(type, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        LineSplitter splitter = (LineSplitter) value;
        Vector2f[] vectors = new Vector2f[splitter.getNextInt()];
        for(int i = 0; i < vectors.length; i++) vectors[i] = splitter.getNextVector2();
        setValue(vectors);
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeString(key).writeInt(getVector2Array().length);
        for(Vector2f vector : getVector2Array()) writer.writeVector2(vector);
    }

    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        valueField = createTextField(panel, Tools.convertArrayToString(getVector2Array()), position, new Vector2f(200, 25), new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(valueField.getText() == null || valueField.getText().equalsIgnoreCase("")) return;
                String[] array = valueField.getText().split(";");
                Vector2f[] vectorArray = new Vector2f[array.length/2];
                for(int i = 0; i < vectorArray.length; i++) vectorArray[i] = new Vector2f(Float.parseFloat(array[i*2]), Float.parseFloat(array[i*2+1]));
                setValue(vectorArray);
            }
        });
    }

    @Override
    protected void setValueInMenu(Object value) {
        if(valueField != null) valueField.setText(Tools.convertArrayToString(getVector2Array()));
    }

    public Vector2ArraySetting setEditable(boolean editable) {
        this.valueField.setEditable(editable);
        return this;
    }

}
