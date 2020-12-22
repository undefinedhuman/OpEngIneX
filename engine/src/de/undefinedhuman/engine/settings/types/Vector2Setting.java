package de.undefinedhuman.engine.settings.types;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.engine.settings.Setting;
import de.undefinedhuman.engine.settings.SettingType;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Vector2Setting extends Setting {

    private JTextField xTextField, yTextField;

    public Vector2Setting(String key, Vector2f value) {
        super(SettingType.Vector2, key, value);
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        setValue(((LineSplitter) value).getNextVector2());
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeString(key).writeVector2(getVector2());
    }

    @Override
    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        xTextField = createTextField(getVector2().x, position, new Vector2f(95, 25), new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(xTextField.getText() == null || xTextField.getText().equalsIgnoreCase("")) return;
                setValue(new Vector2f(Float.parseFloat(xTextField.getText()), getVector2().y));
            }
        });
        panel.add(xTextField);

        yTextField = createTextField(getVector2().y, new Vector2f(position).add(105, 0), new Vector2f(95, 25), new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(yTextField.getText() == null || yTextField.getText().equalsIgnoreCase("")) return;
                setValue(new Vector2f(getVector2().x, Float.parseFloat(yTextField.getText())));
            }
        });
        panel.add(yTextField);
    }

    @Override
    protected void setValueInMenu(Object value) {
        if(xTextField != null) xTextField.setText(String.valueOf(getVector2().x));
        if(yTextField != null) yTextField.setText(String.valueOf(getVector2().y));
    }

}
