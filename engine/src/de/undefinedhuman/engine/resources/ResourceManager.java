package de.undefinedhuman.engine.resources;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.undefinedhuman.engine.entity.ecs.blueprint.Blueprint;
import de.undefinedhuman.engine.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.engine.settings.Setting;
import de.undefinedhuman.engine.settings.SettingsObject;
import de.undefinedhuman.engine.settings.types.mesh.MeshData;
import de.undefinedhuman.core.utils.Tools;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

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
            blueprint.addComponentBlueprint(type.createInstance(reader.getParentDirectory(), (SettingsObject) componentObject));
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
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(decoder == null) return 0;

        textureID = glGenTextures();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_SRGB_ALPHA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        GL30.glGenerateMipmap(GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL_REPEAT);
        GL11.glTexParameterf(GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.56f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        return textureID;
    }

    public static MeshData generateMeshFromOBJ(File objFile) {
        FsFile file = new FsFile(objFile.getPath(), false);
        if (!file.exists()) return new MeshData();
        FileReader reader = file.getFileReader(false, " ");
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Vector2f> textureCoords = new ArrayList<>();
        ArrayList<Vector3f> normals = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        while (reader.nextLine() != null) {
            switch (reader.getNextString()) {
                case "v":
                    vertices.add(new Vertex(vertices.size(), reader.getNextVector3()));
                    break;
                case "vt":
                    textureCoords.add(reader.getNextVector2());
                    break;
                case "vn":
                    normals.add(reader.getNextVector3());
                    break;
                case "f":
                    for (int i = 0; i < 3; i++)
                        processVertex(new LineSplitter(reader.getNextString(), false, "/"), vertices, indices);
                    break;
            }

        }
        reader.close();

        for(Vertex vertex : vertices)
            if(!vertex.isProcessed()) vertex.setTextureIndex(0).setNormalIndex(0);

        return generateMeshData(indices, vertices, textureCoords, normals);
    }

    private static MeshData generateMeshData(ArrayList<Integer> indicies, ArrayList<Vertex> vertices, ArrayList<Vector2f> textureCoords, ArrayList<Vector3f> normals) {
        float[] verticesArray = new float[vertices.size() * 3], textureCoordsArray = new float[vertices.size() * 2], normalsArray = new float[vertices.size() * 3];
        int[] indicesArray = new int[indicies.size()];
        float maxDistance = 0;

        for(int i = 0; i < vertices.size(); i++) {
            Vertex vertex = vertices.get(i);
            float vertexMaxDistance = vertex.position.length();
            if (vertexMaxDistance > maxDistance)
                maxDistance = vertexMaxDistance;
            Vector2f textureCoord = textureCoords.get(vertex.textureIndex);
            Vector3f normal = normals.get(vertex.normalIndex);
            verticesArray[i * 3] = vertex.position.x;
            verticesArray[i * 3 + 1] = vertex.position.y;
            verticesArray[i * 3 + 2] = vertex.position.z;
            textureCoordsArray[i * 2] = textureCoord.x;
            textureCoordsArray[i * 2 + 1] = 1 - textureCoord.y;
            normalsArray[i * 3] = normal.x;
            normalsArray[i * 3 + 1] = normal.y;
            normalsArray[i * 3 + 2] = normal.z;
        }

        for(int i = 0; i < indicesArray.length; i++)
            indicesArray[i] = indicies.get(i);

        return new MeshData(maxDistance, indicesArray, verticesArray, textureCoordsArray, normalsArray);
    }

    private static void processVertex(LineSplitter splitter, ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
        int index = splitter.getNextInt() - 1;
        Vertex vertex = vertices.get(index);
        int textureIndex = splitter.getNextInt() - 1;
        int normalIndex = splitter.getNextInt() - 1;
        if (!vertex.isProcessed()) {
            vertex.setTextureIndex(textureIndex).setNormalIndex(normalIndex);
            indices.add(index);
        } else processDuplicatedVertex(vertex, textureIndex, normalIndex, vertices, indices);
    }

    private static void processDuplicatedVertex(Vertex previousVertex, int newTextureIndex, int newNormalIndex, ArrayList<Vertex> vertices, ArrayList<Integer> indices) {
        if (previousVertex.compare(newTextureIndex, newNormalIndex))
            indices.add(previousVertex.index);
        else {
            if (previousVertex.duplicatedVertex != null) processDuplicatedVertex(previousVertex.duplicatedVertex, newTextureIndex, newNormalIndex, vertices, indices);
            else {
                Vertex newVertex = new Vertex(vertices.size(), previousVertex.position, newTextureIndex, newNormalIndex);
                previousVertex.setDuplicatedVertex(newVertex);
                vertices.add(newVertex);
                indices.add(newVertex.index);
            }
        }
    }

}
