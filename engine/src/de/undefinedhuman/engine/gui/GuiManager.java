package de.undefinedhuman.engine.gui;

import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.Manager;

import java.util.ArrayList;

public class GuiManager implements Manager {

    public static GuiManager instance;

    private ArrayList<GuiTransform> guiTransforms = new ArrayList<>();
    private GuiShader shader;

    public GuiManager() {
        if(instance == null) instance = this;
    }

    @Override
    public void resize(int width, int height) {
        if(shader == null)
            Log.instance.crash("Can't find gui shader! Call GuiManager.instance.setShader(SHADER); before rendering!");
        shader.bind();
        shader.resize(width, height);
        shader.unbind();
    }

    @Override
    public void update(float delta) {
        guiTransforms.forEach(guiTransform -> guiTransform.update(delta));
    }

    @Override
    public void render() {
        shader.bind();
        guiTransforms.forEach(guiTransform -> guiTransform.render(shader));
        shader.unbind();
    }

    @Override
    public void delete() {
        guiTransforms.forEach(GuiTransform::delete);
        guiTransforms.clear();
    }

    public void addGuiTransform(GuiTransform guiTransform) {
        if(!hasGuiTransform(guiTransform))
            this.guiTransforms.add(guiTransform);
    }

    public boolean hasGuiTransform(GuiTransform guiTransform) {
        return guiTransforms.contains(guiTransform);
    }

    public void setShader(GuiShader shader) {
        this.shader = shader;
    }

}
