package de.undefinedhuman.core.opengl.shader.uniforms;

import de.undefinedhuman.core.log.Log;
import org.lwjgl.opengl.GL20;

public abstract class Uniform {

    protected boolean loaded = false;
    private String name;
    private int locationID;

    public Uniform(String name) {
        this.name = name;
    }

    public void storeUniformLocation(int programID) {
        locationID = GL20.glGetUniformLocation(programID, name);
        if (locationID == -1) Log.error("Can't find uniform variable: " + name);
    }

    protected int getLocation() {
        return locationID;
    }

}
