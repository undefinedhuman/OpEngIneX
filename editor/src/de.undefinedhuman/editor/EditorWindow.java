package de.undefinedhuman.editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.editor.editor.Editor;
import de.undefinedhuman.editor.editor.EditorType;
import de.undefinedhuman.editor.editor.entity.EntityEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditorWindow extends JFrame {

    public static EditorWindow instance;
    public Editor editor;

    public JMenuBar menuBar;
    public float errorTime = 0;
    public JLabel errorMessage;

    private Container container;
    private boolean hasError = false;

    public EditorWindow() {
        FlatDarculaLaf.install();

        errorMessage = new JLabel();
        errorMessage.setBounds(22, 1007, 1880, 25);
        errorMessage.setForeground(new Color(255, 85, 85));

        Log.instance = new Log() {
            @Override
            public void displayMessage(String msg) {
                if(!msg.contains("Error")) return;
                errorMessage.setText(msg);
                hasError = true;
            }
        };
        Log.instance.init();

        setResizable(false);
        setSize(1920, 1080);
        container = getContentPane();
        container.setBackground(new Color(60, 63, 65));
        addMenu();

        setEditor(EditorType.ENTITY);

        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Log.instance.save();
                System.exit(0);
            }
        });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void addMenu() {
        menuBar = new JMenuBar();

        JMenu fileMenu = addMenu("File");
        addMenuItem(fileMenu, "Load", e -> editor.load());
        addMenuItem(fileMenu, "Save", e -> editor.save());

        JMenu editMenu = addMenu("Edit");
        addMenuItem(editMenu, "New", e -> editor.reset());

        JMenu editorMenu = addMenu("Editor");
        addMenuItem(editorMenu, "Entity", e -> setEditor(EditorType.ENTITY));

        setJMenuBar(menuBar);
    }

    private JMenu addMenu(String name) {
        JMenu menu = new JMenu(name);
        menuBar.add(menu);
        return menu;
    }

    private void addMenuItem(JMenu menu, String name, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        menu.add(item);
    }

    public void setEditor(EditorType type) {
        container.removeAll();
        container.add(errorMessage);
        container.setLayout(null);
        switch (type) {
            case ENTITY:
                editor = new EntityEditor(container);
                break;
        }
        revalidate();
        repaint();
    }

    public void updateErrorTime(float delta) {
        if(!hasError) return;
        this.errorTime -= delta;
        if(errorTime <= 0) {
            errorMessage.setText("");
            hasError = false;
            errorTime = 15;
        }
    }

}
