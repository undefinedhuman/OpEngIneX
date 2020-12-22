package de.undefinedhuman.engine.opengl.shader;

import de.undefinedhuman.engine.resources.ResourceManager;
import de.undefinedhuman.core.log.Log;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {

    private int shaderID, programID;

    public Shader(String path, int programID, int type) {
        this.programID = programID;
        this.shaderID = loadShader(path, type);
        GL20.glAttachShader(programID, shaderID);
    }

    private int loadShader(String shaderPath, int type) {
        int id = GL20.glCreateShader(type);
        GL20.glShaderSource(id, ResourceManager.loadShader(shaderPath));
        GL20.glCompileShader(id);
        if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
            Log.instance.crash("Could not compile shader! \n" + GL20.glGetShaderInfoLog(id, 500));
        return id;
    }

    public void delete() {
        GL20.glDetachShader(programID, shaderID);
        GL20.glDeleteShader(shaderID);
    }

}
