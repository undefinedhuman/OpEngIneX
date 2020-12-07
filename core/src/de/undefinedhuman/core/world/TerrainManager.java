package de.undefinedhuman.core.world;

import de.undefinedhuman.core.log.Log;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.world.generation.HeightGenerator;
import de.undefinedhuman.core.world.generation.TerrainGenerator;
import de.undefinedhuman.core.world.shader.TerrainShader;
import org.joml.Vector2i;

import java.util.HashMap;

public class TerrainManager implements Manager {

    public static TerrainManager instance;

    private TerrainShader shader;
    private HashMap<Vector2i, Terrain> terrains = new HashMap<>();

    private Vector2i tempKey = new Vector2i();

    public TerrainManager() {
        if(instance == null) instance = this;
    }

    @Override
    public void init() {
        TerrainTexture.load();
    }

    @Override
    public void resize(int width, int height) {
        if(shader == null)
            Log.instance.crash("Can't find terrain shader! Call TerrainManager.instance.setShader(SHADER); before rendering!");
        shader.bind();
        shader.resize(width, height);
        shader.unbind();
    }

    @Override
    public void update(float delta) {
        terrains
                .values()
                .forEach(terrain -> terrain.update(delta));
    }

    @Override
    public void render() {
        shader.bind();
        shader.loadUniforms();
        terrains
                .values()
                .forEach(terrain -> terrain.render(shader));
        shader.unbind();
    }

    @Override
    public void delete() {
        terrains
                .values()
                .forEach(Terrain::delete);
        TerrainTexture.delete();
        terrains.clear();
        shader.delete();
    }

    public float getHeightAtPosition(float x, float z) {
        Terrain terrain = getTerrain((int) (x / Variables.TERRAIN_SIZE), (int) (z / Variables.TERRAIN_SIZE));
        if(terrain == null) return 0;
        return terrain.getHeightAtPosition(x, z);
    }

    public void addTerrain(TerrainTexture texture, int x, int z, HeightGenerator heightGenerator) {
        if(!hasTerrain(x, z))
            this.terrains.put(new Vector2i(x, z), TerrainGenerator.generateTerrain(texture, x, z, heightGenerator));
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

    public HashMap<Vector2i, Terrain> getTerrains() {
        return terrains;
    }

}
