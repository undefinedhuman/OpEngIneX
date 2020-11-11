package de.undefinedhuman.core.settings.panels;

import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.utils.Tools;
import org.joml.Vector2f;

import javax.swing.*;

public class StringPanel extends Panel {

    private JTextField objectName;

    public StringPanel(String key, PanelObject panelObject) {
        super(key, panelObject);
    }

    @Override
    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        super.addValueMenuComponents(panel, position);
        objectName = new JTextField("");
        objectName.setBounds((int) position.x - 175, (int) position.y + 30, 500, 25);
        panel.add(objectName);
    }

    @Override
    public void addObject() {
        if(objectList.contains(objectName.getText())) {
            Log.error(this.key + " with name " + objectName.getText() + " already exist!");
            return;
        }
        PanelObject object = null;
        try {
            object = this.panelObject.getClass().newInstance();
            object.setKey(objectName.getText());
            objects.put(object.getKey(), object);
        } catch (InstantiationException | IllegalAccessException ex) { ex.printStackTrace(); }
        if(object != null) objectList.addElement(object.getKey());
    }

    @Override
    public void removeObject() {
        if(!objectList.contains(objectName.getText())) {
            Log.error(this.key + " with name " + objectName.getText() + " does not exist!");
            return;
        }
        objectList.removeElement(objectName.getText());
        objects.remove(objectName.getText());
        Tools.removeSettings(objectPanel);
    }

    @Override
    public void selectObject(PanelObject object) {
        objectName.setText(object.getKey());
        Tools.removeSettings(objectPanel);
        Tools.addSettings(objectPanel, object.getSettings());
    }

}
