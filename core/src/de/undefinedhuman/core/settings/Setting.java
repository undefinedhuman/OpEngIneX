package de.undefinedhuman.core.settings;

import de.undefinedhuman.core.entity.EntityType;
import de.undefinedhuman.core.file.FileUtils;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Setting {

    public float offset = 30;

    protected String key;
    protected Object value;

    public JTextField valueField;
    private SettingType type;

    public Setting(SettingType type, String key, Object value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public Object getValue() { return value; }
    public void setValue(Object value) {
        this.value = value;
        setValueInMenu(value);
    }

    public String getString() { return String.valueOf(value); }
    public float getFloat() { return Float.parseFloat(getString()); }
    public boolean getBoolean() { return FileUtils.readBoolean(getString()); }
    public int getInt() { return Integer.parseInt(getString()); }
    public byte getByte() { return Byte.parseByte(getString()); }

    public Vector2f getVector2() { return (Vector2f) value; }

    public String[] getStringArray() { return (String[]) value; }
    public int[] getIntegerArray() { return (int[]) value; }
    public float[] getFloatArray() { return (float[]) value; }
    public Vector2f[] getVector2Array() { return (Vector2f[]) value; }

    public EntityType getEntityType() { return EntityType.valueOf(getString()); }

    public SettingType getType() { return type; }

    public void loadSetting(FsFile parentDir, SettingsObject settingsObject) {
        if(!settingsObject.containsKey(key)) return;
        load(parentDir, settingsObject.get(key));
        setValueInMenu(value);
    }

    public void save(FileWriter writer) {
        writer.writeString(key);
        writer.writeString(value.toString());
    }

    protected void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        setValue(((LineSplitter) value).getNextString());
    }

    public void addMenuComponents(JPanel panel, Vector2f position) {
        JLabel keyLabel = new JLabel("  " + key + ": (" + type.name() + ")", SwingConstants.LEFT);
        keyLabel.setBounds((int) position.x + 5, (int) position.y, 165, 25);
        keyLabel.setBackground(new Color(55, 58, 60));
        keyLabel.setOpaque(true);
        panel.add(keyLabel);
        addValueMenuComponents(panel, new Vector2f(position).add(180, 0));
    }

    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        valueField = createTextField(value, position, new Vector2f(200f, 25f), new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(valueField.getText() == null || valueField.getText().equalsIgnoreCase("")) return;
                setValue(valueField.getText());
            }
        });
        panel.add(valueField);
        valueField.requestFocus();
    }

    protected JTextField createTextField(Object value, Vector2f position, Vector2f size, KeyAdapter adapter) {
        JTextField textField = new JTextField(String.valueOf(value));
        textField.setBounds((int) position.x, (int) position.y, (int) size.x, (int) size.y);
        textField.addKeyListener(adapter);
        return textField;
    }

    protected void setValueInMenu(Object value) {
        if(valueField != null) valueField.setText(value.toString());
    }

    public void delete() { value = null; }

}
