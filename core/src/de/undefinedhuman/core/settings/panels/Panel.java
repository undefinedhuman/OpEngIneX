package de.undefinedhuman.core.settings.panels;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.settings.SettingsObject;
import org.joml.Vector2f;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Panel extends Setting {

    protected JList<String> objectSelectionList;
    protected HashMap<String, PanelObject> objects = new HashMap<>();
    protected DefaultListModel<String> objectList;
    protected PanelObject panelObject;
    protected JPanel objectPanel;

    public Panel(String key, PanelObject panelObject) {
        super(SettingType.Panel, key, "");
        this.panelObject = panelObject;
    }

    @Override
    public void delete() {
        super.delete();
        for(PanelObject object : objects.values()) object.delete();
        objects.clear();
    }

    @Override
    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        int panelObjectOffset = 0;
        for(Setting setting : panelObject.getSettings())
            panelObjectOffset += setting.offset;

        objectPanel = new JPanel(null);
        objectPanel.setBounds((int) position.x - 175, (int) position.y + 190, 500, panelObjectOffset);
        objectPanel.setOpaque(true);

        objectList = new DefaultListModel<>();
        for(String key : objects.keySet()) objectList.addElement(key);

        objectSelectionList = new JList<>(objectList);
        objectSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        objectSelectionList.addListSelectionListener(e -> {
            if(objectSelectionList.getSelectedValue() == null) return;
            selectObject(objects.get(objectSelectionList.getSelectedValue()));
        });

        JScrollPane objectScrollPane = new JScrollPane(objectSelectionList);
        objectScrollPane.setBounds((int) position.x - 175, (int) position.y + 90, 500, 90);

        this.offset = 190 + panelObjectOffset;

        panel.add(objectScrollPane);
        panel.add(objectPanel);
        panel.add(addButton("Add", (int) position.x - 175, (int) position.y + 60, e -> addObject()));
        panel.add(addButton("Remove", (int) position.x + 80, (int) position.y + 60, e -> removeObject()));
    }

    private JButton addButton(String text, int x, int y, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 245, 25);
        button.addActionListener(listener);
        return button;
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof SettingsObject)) return;
        SettingsObject settingsObject = (SettingsObject) value;
        HashMap<String, Object> settings = settingsObject.getSettings();
        for(String key : settings.keySet()) {
            Object panelObjectSettings = settings.get(key);
            if(!(panelObjectSettings instanceof SettingsObject)) continue;
            try {
                objects.put(key, panelObject.getClass().newInstance().load(parentDir, (SettingsObject) panelObjectSettings).setKey(key));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeString("{:" + key).nextLine();
        for(PanelObject object : this.objects.values())
            object.save(writer);
        writer.writeString("}");
    }

    public HashMap<String, PanelObject> getPanelObjects() {
        return objects;
    }

    public ArrayList<PanelObject> values() {
        return new ArrayList<>(objects.values());
    }

    public abstract void addObject();
    public abstract void removeObject();
    public abstract void selectObject(PanelObject object);

}
