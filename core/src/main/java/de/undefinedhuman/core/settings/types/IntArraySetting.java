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

public class IntArraySetting extends Setting {

    private JTextField valueField;

    public IntArraySetting(String key, int[] value) {
        super(SettingType.IntArray, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        LineSplitter splitter = (LineSplitter) value;
        setValue(splitter.getNextIntegerArray());
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeIntegerArray(getIntegerArray());
    }

    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        valueField = createTextField(panel,
                Tools.convertArrayToString(getIntegerArray()),
                position,
                new Vector2f(200, 25),
                new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (valueField.getText() == null || valueField.getText().equalsIgnoreCase(""))
                            return;
                        setValue(Tools.convertStringToIntArray(valueField.getText().split(";")));
                    }
                });
    }

    @Override
    protected void setValueInMenu(Object value) {
        if(valueField != null) valueField.setText(Tools.convertArrayToString(getIntegerArray()));
    }

    public IntArraySetting setEditable(boolean editable) {
        this.valueField.setEditable(editable);
        return this;
    }

}
