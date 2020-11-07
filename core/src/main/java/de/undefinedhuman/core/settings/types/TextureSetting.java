package de.undefinedhuman.core.settings.types;

import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.utils.Variables;
import org.joml.Vector2f;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureSetting extends Setting {

    private BufferedImage texture;
    private JLabel textureLabel;

    public TextureSetting(String key, Object value) {
        super(SettingType.Texture, key, value);
        setValue("Unknown.png");
        loadTexture(getString());
        this.offset = 301;
    }

    @Override
    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        textureLabel = new JLabel();
        textureLabel.setBounds((int) position.x, (int) position.y, 256, 256);
        setTextureIcon();
        textureLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new FsFile(Paths.EDITOR_PATH + "texture/", false).getFile());
                chooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));

                int returnVal = chooser.showOpenDialog(null);
                if(returnVal != JFileChooser.APPROVE_OPTION) return;
                File textureFile = chooser.getSelectedFile();
                if(textureFile == null) return;
                loadTexture(textureFile.getPath());
                setValue(textureFile.getName());
                setTextureIcon();
            }
        });
        panel.add(textureLabel);
    }

    @Override
    public void save(FileWriter writer) {
        writer.writeString(key).writeString(value.toString());
        FsFile file = new FsFile(writer.getParentDirectory().getPath() + Variables.FILE_SEPARATOR + getString(), false);
        try { ImageIO.write(texture, "png", file.getFile());
        } catch (IOException e) { Log.instance.crash(e.getMessage()); }
    }

    @Override
    public void load(FsFile parentDir, Object value) {
        if(!(value instanceof LineSplitter)) return;
        setValue(((LineSplitter) value).getNextString());
        String path = parentDir.getPath() + Variables.FILE_SEPARATOR + getString();
        try {
            loadTexture(path);
            if(texture == null) loadTexture("Unknown.png");
        } catch (Exception e) { Log.instance.crash(e.getMessage()); }
        if(TextureManager.instance != null) {
            setValue(path);
            TextureManager.instance.addTexture(getString());
        }
    }

    @Override
    protected void setValueInMenu(Object value) {
        if(textureLabel != null) {
            Image scaledTexture = texture.getScaledInstance(textureLabel.getWidth(), textureLabel.getHeight(), Image.SCALE_FAST);
            textureLabel.setIcon(new ImageIcon(scaledTexture));
        }
    }

    @Override
    public void delete() {
        if(TextureManager.instance != null) TextureManager.instance.removeTexture(getString());
    }

    private void loadTexture(String path) {
        try { texture = ImageIO.read(new FsFile(path, false).getFile());
        } catch (Exception ex) { Log.instance.crash(ex.getMessage()); }
        if(texture == null && !path.equals("Unknown.png")) loadTexture("Unknown.png");
    }

    private void setTextureIcon() {
        if(textureLabel == null) return;
        textureLabel.setIcon(new ImageIcon(texture.getScaledInstance(textureLabel.getWidth(), textureLabel.getHeight(), Image.SCALE_FAST)));
    }

}
