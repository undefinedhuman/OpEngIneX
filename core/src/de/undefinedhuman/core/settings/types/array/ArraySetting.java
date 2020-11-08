package de.undefinedhuman.core.settings.types.array;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class ArraySetting extends Setting {

    protected JTextField valueField;
    private boolean editable = true, focusable = true;

    public ArraySetting(SettingType type, String key, Object value) {
        super(type, key, value);
    }

    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        valueField = createTextField(
                generateString(),
                position,
                new Vector2f(200, 25),
                new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if(valueField.getText() == null || valueField.getText().equalsIgnoreCase("")) return;
                        keyEvent();
                    }
                });
        valueField.setEditable(editable);
        valueField.setFocusable(focusable);
        panel.add(valueField);
    }

    @Override
    protected void setValueInMenu(Object value) {
        if(valueField != null) valueField.setText(generateString());
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeString(key);
    }

    public ArraySetting setEditable(boolean editable) {
        this.editable = editable;
        return this;
    }

    public ArraySetting setFocusable(boolean focusable) {
        this.focusable = focusable;
        return this;
    }

    public abstract String generateString();
    public abstract void keyEvent();

}
