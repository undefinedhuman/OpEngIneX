package de.undefinedhuman.core.settings.types;

import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingType;
import de.undefinedhuman.core.utils.Tools;
import de.undefinedhuman.core.utils.Variables;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MeshSetting extends Setting {

    private BufferedImage texture;
    private JLabel meshLabel;

    private int[] indicies;
    private float[] vertices, textureCoords, normals;

    public MeshSetting(String key, int[] value) {
        super(SettingType.Mesh, key, value);
        try { texture = ImageIO.read(new FsFile("Unknown.png", false).getFile());
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    protected void addValueMenuComponents(JPanel panel, Vector2f position) {
        meshLabel = new JLabel("Load OBJ");
        meshLabel.setBounds((int) position.x, (int) position.y, 200, 25);
        meshLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new FsFile(Paths.EDITOR_PATH, false).getFile());
                chooser.setFileFilter(new FileNameExtensionFilter("OBJ Model", "obj"));

                int returnVal = chooser.showOpenDialog(null);
                if(returnVal != JFileChooser.APPROVE_OPTION) return;
                File objFile = chooser.getSelectedFile();
                if(objFile == null) return;

                setValue(calculateVectors(texture));
                setValueInMenu(getVector2Array());
            }

        });
        panel.add(meshLabel);
        valueField = createTextField(Tools.convertArrayToString(getVector2Array()), new Vector2(position).add(30, 0), new Vector2(170, 25), null);
        panel.add(valueField);
    }

    private int[] generateMeshFromOBJ() {
        FsFile file = new FsFile(Paths.CONVERTER_PATH, fileName + ".obj", false);
        if (!file.exists()) return;
        FileReader reader = file.getFileReader(false, " ");
        ArrayList<Vector3f> verticesList = new ArrayList<>(), normalsList = new ArrayList<>();
        ArrayList<Vector2f> texturesList = new ArrayList<>();
        ArrayList<Integer> indicesList = new ArrayList<>();

        boolean setup = false;

        while (reader.nextLine() != null) {
            switch (reader.getNextString()) {
                case "v":
                    verticesList.add(reader.getNextVector3());
                    break;
                case "vt":
                    texturesList.add(reader.getNextVector2());
                    break;
                case "vn":
                    normalsList.add(reader.getNextVector3());
                    break;
                case "f":
                    if (!setup) {
                        textureCoords = new float[verticesList.size() * 2];
                        normals = new float[verticesList.size() * 3];
                        setup = true;
                    }
                    for (int i = 0; i < 3; i++) {
                        LineSplitter splitter = new LineSplitter(reader.getNextString(), false, "/");
                        int currentVertexPointer = splitter.getNextInt() - 1;
                        indicesList.add(currentVertexPointer);
                        Vector2f texture = texturesList.get(splitter.getNextInt() - 1);
                        textureCoords[currentVertexPointer * 2] = texture.x;
                        textureCoords[currentVertexPointer * 2 + 1] = 1 - texture.y;
                        Vector3f normal = normalsList.get(splitter.getNextInt() - 1);
                        normals[currentVertexPointer * 3] = normal.x;
                        normals[currentVertexPointer * 3 + 1] = normal.y;
                        normals[currentVertexPointer * 3 + 2] = normal.z;
                    }
                    break;
            }

        }
        reader.close();

        indicies = new int[indicesList.size()];
        for (int i = 0; i < indicies.length; i++)
            indicies

        writer.writeInt(verticesList.size()*3);
        for (Vector3f vector : verticesList) writer.writeVector3(vector);
        writer.writeInt(textureArray.length);
        for (float textureCord : textureArray) writer.writeFloat(textureCord);
        writer.writeInt(normalsArray.length);
        for (float normal : normalsArray) writer.writeFloat(normal);
    }

}
