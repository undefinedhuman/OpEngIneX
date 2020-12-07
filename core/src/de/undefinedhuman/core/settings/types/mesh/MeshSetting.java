package de.undefinedhuman.core.settings.types.mesh;

import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.resources.ResourceManager;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import org.joml.Vector2f;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public abstract class MeshSetting extends Setting {

    public MeshSetting(String key) {
        super(SettingType.Mesh, key, "");
    }

    @Override
    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        JLabel loadOBJLabel = new JLabel("Load OBJ");
        loadOBJLabel.setBounds((int) position.x, (int) position.y, 200, 25);
        loadOBJLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new FsFile(Paths.EDITOR_PATH + "obj/", false).getFile());
                chooser.setFileFilter(new FileNameExtensionFilter("OBJ Model", "obj"));

                int returnVal = chooser.showOpenDialog(null);
                if(returnVal != JFileChooser.APPROVE_OPTION) return;
                File objFile = chooser.getSelectedFile();
                if(objFile == null) return;

                MeshData meshData = ResourceManager.generateMeshFromOBJ(objFile);

                updateMesh(meshData.maxDistance, meshData.indicies, meshData.vertices, meshData.textureCoords, meshData.normals);
            }

        });
        panel.add(loadOBJLabel);
    }

    public abstract void updateMesh(float maxDistance, int[] indicies, float[] vertices, float[] textureCoords, float[] normals);

}
