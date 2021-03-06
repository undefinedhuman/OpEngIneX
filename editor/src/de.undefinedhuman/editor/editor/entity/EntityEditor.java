package de.undefinedhuman.editor.editor.entity;

import de.undefinedhuman.engine.entity.EntityType;
import de.undefinedhuman.engine.entity.ecs.component.ComponentBlueprint;
import de.undefinedhuman.engine.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.*;
import de.undefinedhuman.engine.settings.Setting;
import de.undefinedhuman.engine.settings.SettingType;
import de.undefinedhuman.engine.settings.SettingsList;
import de.undefinedhuman.engine.settings.SettingsObject;
import de.undefinedhuman.engine.settings.types.SelectionSetting;
import de.undefinedhuman.core.utils.Tools;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.editor.editor.Editor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EntityEditor extends Editor {

    private JComboBox<ComponentType> componentComboBox;

    private DefaultListModel<String> componentList;
    private JList<String> listPanel;

    private ComponentType selectedComponent;

    private HashMap<ComponentType, ComponentBlueprint> components;

    private SettingsList baseSettings = new SettingsList();

    public EntityEditor(Container container) {
        super(container);
        components = new HashMap<>();

        componentComboBox = new JComboBox<>(ComponentType.values());
        componentComboBox.setSelectedItem(null);
        componentComboBox.setBounds(20, 25, 150, 25);

        baseSettings.add(
                new Setting(SettingType.Int, "ID", getNewEntityID()),
                new Setting(SettingType.String, "Name", "Name"),
                new SelectionSetting("Type", EntityType.values()));
        Tools.addSettings(mainPanel, baseSettings.getSettings());

        componentList = new DefaultListModel<>();

        listPanel = new JList<>(componentList);
        listPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPanel.addListSelectionListener(e -> {
            selectedComponent = listPanel.getSelectedValue() != null ? ComponentType.valueOf(listPanel.getSelectedValue()) : null;
            if(selectedComponent != null) {
                Tools.removeSettings(settingsPanel);
                Tools.addSettings(settingsPanel, components.get(selectedComponent).getSettings());
            }
        });

        JScrollPane listScroller = new JScrollPane(listPanel);
        listScroller.setBounds(20, 125, 150, 450);

        JButton addComponent = new JButton("Add");
        addComponent.setBounds(20, 55, 150, 25);
        addComponent.addActionListener(e -> {
            if(componentComboBox.getSelectedItem() == null) return;
            Tools.removeSettings(settingsPanel);
            ComponentType type = ComponentType.valueOf(componentComboBox.getSelectedItem().toString());
            if(componentList.contains(type.toString())) return;
            componentList.addElement(type.toString());
            components.put(type, type.createInstance());
        });

        JButton removeComponent = new JButton("Remove");
        removeComponent.setBounds(20, 85, 150, 25);
        removeComponent.addActionListener(e -> {
            if(listPanel.getSelectedValue() == null) return;
            ComponentType type = ComponentType.valueOf(listPanel.getSelectedValue());
            Tools.removeSettings(settingsPanel);
            components.remove(type);
            if(componentList.contains(type.toString())) componentList.removeElement(type.toString());
        });

        middlePanel.add(componentComboBox);
        middlePanel.add(addComponent);
        middlePanel.add(removeComponent);
        middlePanel.add(listScroller);

    }

    @Override
    public void load() {

        File[] entityDirs = new FsFile(Paths.ENTITY_PATH, true).list();
        ArrayList<String> ids = new ArrayList<>();

        for (File entityDir : entityDirs) {
            if (!entityDir.isDirectory()) continue;
            FsFile entityFile = new FsFile(Paths.ENTITY_PATH, entityDir.getName() + "/settings.entity", true);
            if(entityFile.isEmpty()) continue;
            FileReader reader = entityFile.getFileReader(true);
            SettingsObject settings = Tools.loadSettings(reader);
            ids.add(entityDir.getName() + "-" + ((LineSplitter) settings.get("Name")).getNextString());
            reader.close();
        }

        JFrame chooseWindow = new JFrame("Load entity");
        chooseWindow.setSize(480, 150);
        chooseWindow.setLocationRelativeTo(null);
        chooseWindow.setResizable(false);

        JLabel label = new JLabel();
        chooseWindow.add(label);

        JButton button = new JButton("Load Entity");
        button.setBounds(90, 70, 300, 25);

        String[] stringID = ids.toArray(new String[0]);
        Arrays.sort(stringID);
        JComboBox<String> comboBox = new JComboBox<>(stringID);
        comboBox.setBounds(90, 35, 300, 25);

        button.addActionListener(a -> {
            if(comboBox.getSelectedItem() == null) return;
            componentList.clear();
            components.clear();
            Tools.removeSettings(settingsPanel);

            FileReader reader = new FsFile(Paths.ENTITY_PATH, Integer.parseInt(((String) comboBox.getSelectedItem()).split("-")[0]) + "/settings.entity", false).getFileReader(true);
            SettingsObject settingsObject = Tools.loadSettings(reader);

            for(Setting setting : baseSettings.getSettings())
                setting.loadSetting(reader.getParentDirectory(), settingsObject);

            for(ComponentType type : ComponentType.values()) {
                if(!settingsObject.containsKey(type.name())) continue;
                componentList.addElement(type.name());
                Object componentObject = settingsObject.get(type.name());
                if(!(componentObject instanceof SettingsObject)) continue;
                components.put(type, type.createInstance(reader.getParentDirectory(), (SettingsObject) settingsObject.get(type.name())));
            }

            chooseWindow.setVisible(false);
            reader.close();

        });

        label.add(comboBox);
        label.add(button);

        chooseWindow.setVisible(true);

    }

    @Override
    public void save() {
        FsFile entityDir = new FsFile(Paths.ENTITY_PATH, baseSettings.getSettings().get(0).getString() + Variables.FILE_SEPARATOR, true, false);
        if(entityDir.exists())
            FileUtils.deleteFile(entityDir);

        FileWriter writer = new FsFile(entityDir.createFile(true), "settings.entity", false).getFileWriter(true);
        Tools.saveSettings(writer, baseSettings.getSettings());
        for(ComponentBlueprint componentBlueprint : this.components.values())
            componentBlueprint.save(writer);
        writer.close();
    }

    @Override
    public void reset() {
        componentList.clear();
        components.clear();
        Tools.removeSettings(settingsPanel);
        baseSettings.getSettings().get(0).setValue(getNewEntityID());
    }

    private int getNewEntityID() {
        File[] entityDirs = new FsFile(Paths.ENTITY_PATH, true).list();
        int maxID = -1;
        for(File file : entityDirs) {
            int currentID = Integer.parseInt(file.getName());
            if(currentID > maxID) maxID = currentID;
        }
        maxID++;
        return maxID;
    }

}
