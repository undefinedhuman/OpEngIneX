package de.undefinedhuman.core.water;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.opengl.fbos.FBO;
import de.undefinedhuman.core.opengl.fbos.attachments.RenderBufferAttachment;
import de.undefinedhuman.core.opengl.fbos.attachments.TextureAttachment;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.window.Window;
import de.undefinedhuman.core.world.TerrainManager;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;

public class WaterManager implements Manager {

    public static WaterManager instance;
    public FBO reflectionTexture, refractionTexture;

    private WaterShader shader;
    private HashMap<Vector2i, WaterTile> waterTiles = new HashMap<>();
    private Vector2i tempKey = new Vector2i();
    private int dudvTextureID, normalMapID;
    private float waveFactor = 0;


    public WaterManager() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        reflectionTexture = createFBO(Variables.WINDOW_WIDTH, Variables.WINDOW_HEIGHT, false);
        refractionTexture = createFBO(Variables.WINDOW_WIDTH/2, Variables.WINDOW_HEIGHT/2, true);
        dudvTextureID = TextureManager.instance.getTexture("water/dudv.png").getID();
        normalMapID = TextureManager.instance.getTexture("water/normal.png").getID();
    }

    @Override
    public void resize(int width, int height) {
        if(shader == null)
            Log.instance.crash("Can't find water shader! Call WaterManager.instance.setShader(SHADER); before rendering!");
        shader.bind();
        shader.resize(width, height);
        shader.unbind();
    }

    @Override
    public void update(float delta) {
        waterTiles
                .values()
                .forEach(waterTileTile -> waterTileTile.update(delta));

        waveFactor += (Variables.WATER_WAVE_MOVE_SPEED * delta);
        waveFactor %= 1;

        reflectionTexture.start(0);
        OpenGLUtils.clipPlane.set(0, 1, 0, -Variables.WATER_HEIGHT);
        Camera.instance.setReflected(true);
        Window.instance.render();
        OpenGLUtils.disableMSAA();
        TerrainManager.instance.render();
        EntityManager.instance.render();
        Camera.instance.setReflected(false);
        reflectionTexture.stop();

        refractionTexture.start(0);
        OpenGLUtils.clipPlane.set(0, -1, 0, Variables.WATER_HEIGHT + 2f);
        Window.instance.render();
        OpenGLUtils.disableMSAA();
        TerrainManager.instance.render();
        EntityManager.instance.render();
        refractionTexture.stop();
    }

    @Override
    public void render() {
        shader.bind();
        shader.loadUniforms();
        shader.loadUniforms(waveFactor);
        OpenGLUtils.bindTexture(0, reflectionTexture.getAttachment(0));
        OpenGLUtils.bindTexture(1, refractionTexture.getAttachment(0));
        OpenGLUtils.bindTexture(2, dudvTextureID);
        OpenGLUtils.bindTexture(3, normalMapID);
        OpenGLUtils.bindTexture(4, refractionTexture.getDepthBuffer());
        OpenGLUtils.enableAlphaBlending();
        waterTiles
                .values()
                .forEach(waterTile -> waterTile.render(shader));
        OpenGLUtils.disableAlphaBlending();
        shader.unbind();
    }

    @Override
    public void delete() {
        reflectionTexture.delete();
        refractionTexture.delete();
        waterTiles
                .values()
                .forEach(WaterTile::delete);
        waterTiles.clear();
        shader.delete();
    }

    public void addWater(int x, int z) {
        if(!hasWaterTile(x, z))
            this.waterTiles.put(new Vector2i(x, z), new WaterTile(x, z, Variables.WATER_HEIGHT));
    }

    public WaterTile getWater(int x, int z) {
        if(hasWaterTile(tempKey.set(x, z)))
            return waterTiles.get(tempKey);
        return null;
    }

    public boolean hasWaterTile(int x, int z) {
        return waterTiles.containsKey(tempKey.set(x, z));
    }

    private boolean hasWaterTile(Vector2i coords) {
        return waterTiles.containsKey(coords);
    }

    public void setShader(WaterShader shader) {
        this.shader = shader;
    }

    private FBO createFBO(int width, int height, boolean useTextureForDepth) {
        return new FBO(width, height, 0)
                .addAttachment(0, new TextureAttachment(GL30.GL_SRGB_ALPHA))
                .addDepthAttachment(useTextureForDepth ? new TextureAttachment(GL14.GL_DEPTH_COMPONENT24) : new RenderBufferAttachment(GL14.GL_DEPTH_COMPONENT24))
                .init();
    }

}
