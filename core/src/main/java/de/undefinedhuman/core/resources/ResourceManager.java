package de.undefinedhuman.core.resources;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.file.Paths;
import de.undefinedhuman.core.entity.EntityType;
import de.undefinedhuman.core.entity.ecs.ComponentBlueprint;
import de.undefinedhuman.core.entity.ecs.ComponentType;
import de.undefinedhuman.core.entity.ecs.blueprint.Blueprint;
import de.undefinedhuman.core.resources.texture.Texture;
import org.lwjgl.opengl.GL11;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class ResourceManager {

    public static Blueprint loadBlueprint(int id) {
        FsFile file = new FsFile(Paths.ENTITY_PATH, id + "/settings.entity", false);
        FileReader reader = file.getFileReader(true);
        reader.nextLine();
        Blueprint blueprint = new Blueprint(id, EntityType.valueOf(reader.getNextString()));
        reader.nextLine();
        int componentCount = reader.getNextInt();
        for(int i = 0; i < componentCount; i++) {
            reader.nextLine();
            ComponentType type = ComponentType.valueOf(reader.getNextString());
            ComponentBlueprint componentBlueprint = ComponentType.createComponentBlueprint(type);
            if(componentBlueprint == null) continue;
            componentBlueprint.load(reader);
            blueprint.addComponentBlueprint(componentBlueprint);
        }
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

}
