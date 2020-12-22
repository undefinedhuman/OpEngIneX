package de.undefinedhuman.engine.opengl.shader;

import de.undefinedhuman.engine.opengl.shader.uniforms.Uniform;
import de.undefinedhuman.engine.opengl.shader.uniforms.UniformArray;
import de.undefinedhuman.engine.resources.ResourceManager;
import de.undefinedhuman.core.log.Log;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

public abstract class ShaderProgram {

    private int programID;
    private String shaderPath;

    private ArrayList<Integer> shaderIDs = new ArrayList<>();

    public ShaderProgram(String shaderPath, String... attributes) {
        this.programID = GL20.glCreateProgram();
        this.shaderPath = shaderPath;
        bindAttributes(attributes);
    }

    public void compileShader() {
        GL20.glLinkProgram(programID);
        if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) Log.instance.crash("Can't link " + shaderPath + ":\n" + GL20.glGetProgramInfoLog(programID, 1024));
        //GL20.glValidateProgram(programID);
        //if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) Log.instance.crash("Can't validate " + shaderPath + ":\n" + GL20.glGetProgramInfoLog(programID, 1024));
    }

    public ShaderProgram addVertexShader() {
        attachShader(GL_VERTEX_SHADER, "vertex");
        return this;
    }

    public ShaderProgram addFragmentShader() {
        attachShader(GL_FRAGMENT_SHADER, "fragment");
        return this;
    }

    public ShaderProgram addGeometryShader() {
        attachShader(GL_GEOMETRY_SHADER, "geometry");
        return this;
    }

    public ShaderProgram addComputeShader() {
        attachShader(GL_COMPUTE_SHADER, "compute");
        return this;
    }

    public ShaderProgram addTessellationControlShader() {
        attachShader(GL_TESS_CONTROL_SHADER, "tessellationControl");
        return this;
    }

    public ShaderProgram addTessellationEvaluationShader() {
        attachShader(GL_TESS_EVALUATION_SHADER, "tessellationEvaluation");
        return this;
    }

    private void attachShader(int type, String shaderName) {
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, ResourceManager.loadShader(shaderPath + "/" + shaderName + ".glsl"));
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
            Log.instance.crash("Could not compile shader! " + shaderPath + "/" + shaderName + "\n" + GL20.glGetShaderInfoLog(shaderID, 500));
        GL20.glAttachShader(programID, shaderID);
        shaderIDs.add(shaderID);
    }

    public void bindAttributes(String[] attributes) {
        for (int i = 0; i < attributes.length; i++) GL20.glBindAttribLocation(programID, i, attributes[i]);
    }

    protected void initUniforms(UniformArray... uniformArrays) {
        for (UniformArray uniformArray : uniformArrays)
            initUniforms(uniformArray.getUniforms());
    }

    protected void initUniforms(Uniform... uniforms) {
        for (Uniform uniform : uniforms)
            uniform.storeUniformLocation(programID);
    }

    public void bind() { GL20.glUseProgram(programID); }
    public void unbind() { GL20.glUseProgram(0); }

    public void delete() {
        for(int id : shaderIDs) {
            GL20.glDetachShader(programID, id);
            GL20.glDeleteShader(id);
        }
        shaderIDs.clear();
        unbind();
        GL20.glDeleteProgram(programID);
    }

}
