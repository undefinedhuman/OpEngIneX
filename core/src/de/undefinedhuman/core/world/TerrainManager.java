package de.undefinedhuman.core.world;

import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.world.generation.HeightGenerator;
import de.undefinedhuman.core.world.shader.TerrainShader;
import org.joml.Vector2i;

import java.util.HashMap;

public class TerrainManager extends Manager {

    public static TerrainManager instance;

    private TerrainShader shader;
    private HashMap<Vector2i, Terrain> terrains = new HashMap<>();

    private Vector2i tempKey = new Vector2i();

    public TerrainManager() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        super.init();
        TerrainTexture.load();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if(shader == null)
            Log.instance.crash("Can't find terrain shader! You have to call TerrainManager.instance.setShader(INSERT YOUR SHADER INSTANCE); during your game initialization!");
        shader.bind();
        shader.resize(width, height);
        shader.unbind();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        terrains
                .values()
                .forEach(terrain -> terrain.update(delta));
    }

    @Override
    public void render() {
        super.render();
        shader.bind();
        shader.loadUniforms();
        terrains
                .values()
                .forEach(terrain -> terrain.render(shader));
        shader.unbind();
    }

    @Override
    public void delete() {
        super.delete();
        terrains
                .values()
                .forEach(Terrain::delete);
        TerrainTexture.delete();
        terrains.clear();
        shader.delete();
    }

    public void addTerrain(TerrainTexture texture, int x, int z, HeightGenerator heightGenerator) {
        if(!hasTerrain(x, z))
            this.terrains.put(new Vector2i(x, z), new Terrain(texture, x, z, heightGenerator));
    }

    public Terrain getTerrain(int x, int z) {
        if(hasTerrain(tempKey.set(x, z)))
            return terrains.get(tempKey);
        return null;
    }

    public boolean hasTerrain(int x, int z) {
        return terrains.containsKey(tempKey.set(x, z));
    }

    private boolean hasTerrain(Vector2i coords) {
        return terrains.containsKey(coords);
    }

    public void setShader(TerrainShader shader) {
        this.shader = shader;
    }

}
