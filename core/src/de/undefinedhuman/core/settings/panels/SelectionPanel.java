package de.undefinedhuman.core.settings.panels;

import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.utils.Tools;
import org.joml.Vector2f;

import javax.swing.*;

public class SelectionPanel extends Panel {

    private JComboBox<String> selection;
    private String[] itemsForSelection;

    public SelectionPanel(String name, PanelObject panelObject, String[] itemsForSelection) {
        super(name, panelObject);
        this.itemsForSelection = itemsForSelection;
    }

    @Override
    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        super.addValueMenuComponents(panel, position);
        selection = new JComboBox<>(itemsForSelection);
        selection.setBounds((int) position.x - 175, (int) position.y + 30, 500, 25);
        panel.add(selection);
    }

    @Override
    public void addObject() {
        if(selection.getSelectedItem() == null) {
            Log.error("No selected Item!");
            return;
        }
        PanelObject object = null;
        try {
            object = this.panelObject.getClass().newInstance();
            object.setKey((String) selection.getSelectedItem());
            if(objects.containsKey(object.getKey())) {
                Log.error(this.key + " with name " + object.getKey() + " already exist!");
                return;
            }
            objects.put(object.getKey(), object);
        } catch (InstantiationException | IllegalAccessException ex) { ex.printStackTrace(); }
        if(object != null) objectList.addElement(object.getKey());
    }

    @Override
    public void removeObject() {
        if(selection.getSelectedItem() == null || !objectList.contains(selection.getSelectedItem())) {
            Log.error(this.key + " with name " + selection.getSelectedItem() + " does not exist!");
            return;
        }
        objectList.removeElement(selection.getSelectedItem());
        objects.remove(String.valueOf(selection.getSelectedItem()));
    }

    @Override
    public void selectObject(PanelObject object) {
        selection.setSelectedItem(object.getKey());
        Tools.removeSettings(objectPanel);
        Tools.addSettings(objectPanel, object.getSettings());
    }

}
