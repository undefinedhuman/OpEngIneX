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

public class FloatArraySetting extends Setting {

    private JTextField valueField;

    public FloatArraySetting(String key, float[] value) {
        super(SettingType.FloatArray, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        LineSplitter splitter = (LineSplitter) value;
        setValue(splitter.getNextFloatArray());
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeFloatArray(getFloatArray());
    }

    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        valueField = createTextField(panel,
                Tools.convertArrayToString(getFloatArray()),
                position,
                new Vector2f(200, 25),
                new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if(valueField.getText() == null || valueField.getText().equalsIgnoreCase("")) return;
                        setValue(Tools.convertStringToFloatArray(valueField.getText().split(";")));
                    }
                });
    }

    @Override
    protected void setValueInMenu(Object value) {
        if(valueField != null) valueField.setText(Tools.convertArrayToString(getFloatArray()));
    }

    public FloatArraySetting setEditable(boolean editable) {
        this.valueField.setEditable(editable);
        return this;
    }

}
