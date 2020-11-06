package de.undefinedhuman.core.resources;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.undefinedhuman.core.entity.ecs.blueprint.Blueprint;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.settings.Setting;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.settings.types.mesh.MeshData;
import de.undefinedhuman.core.utils.Tools;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class ResourceManager {

    public static Blueprint loadBlueprint(FsFile file) {
        Blueprint blueprint = new Blueprint();
        FileReader reader = file.getFileReader(true);
        SettingsObject object = Tools.loadSettings(reader);

        for(Setting setting : blueprint.settings.getSettings())
            setting.loadSetting(reader.getParentDirectory(), object);

        for(ComponentType type : ComponentType.values()) {
            if(!object.containsKey(type.name())) continue;
            Object componentObject = object.get(type.name());
            if(!(componentObject instanceof SettingsObject)) continue;
            blueprint.addComponentBlueprint(type.load(reader.getParentDirectory(), (SettingsObject) object.get(type.name())));
        }

        reader.close();
        return blueprint;
    }

    public static String loadShader(String path) {
        FileReader reader = new FsFile(Paths.SHADER_PATH, path, false).getFileReader(false);
        String line;
        StringBuilder shader = new StringBuilder();
        while ((line = reader.nextLine()) != null) shader.append(line).append("//\n");
        return shader.toString();
    }

    public static int loadTexture(Paths paths, String fileName) {
        return loadTexture(paths + fileName);
    }

    public static int loadTexture(String path) {
        int textureID;
        ByteBuffer image = null;
        PNGDecoder decoder = null;

        try {
            InputStream stream = new FileInputStream(path);
            decoder = new PNGDecoder(stream);
            image = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
            decoder.decode(image, decoder.getWidth()*4, PNGDecoder.Format.RGBA);
            image.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(decoder == null) return 0;

        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        return textureID;
    }

    public static MeshData generateMeshFromOBJ(File objFile) {
        FsFile file = new FsFile(objFile.getPath(), false);
        if (!file.exists()) return new MeshData();
        FileReader reader = file.getFileReader(false, " ");
        ArrayList<Vector3f> verticesList = new ArrayList<>(), normalsList = new ArrayList<>();
        ArrayList<Vector2f> texturesList = new ArrayList<>();
        ArrayList<Integer> indicies = new ArrayList<>();

        boolean setup = false;

        float[] textureCoords = new float[0], normals = new float[0];

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
                        indicies.add(currentVertexPointer);
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

        float[] vertices = new float[verticesList.size()*3];
        for(int i = 0; i < vertices.length; i++) {
            Vector3f vertex = verticesList.get(i);
            vertices[i * 3] = vertex.x;
            vertices[i * 3 + 1] = vertex.y;
            vertices[i * 3 + 2] = vertex.z;
        }

        return new MeshData(indicies.stream().mapToInt(Integer::intValue).toArray(), vertices, textureCoords, normals);
    }

}
