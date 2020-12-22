package de.undefinedhuman.engine.world;

import de.undefinedhuman.engine.resources.texture.TextureManager;

public enum TerrainTexture {

    GRASS,
    DIRT,
    STONE;

    public static void load() {
        for(TerrainTexture texture : values())
            TextureManager.instance.addTexture(texture.getTextureName());
    }

    public static void delete() {
        for(TerrainTexture texture : values())
            TextureManager.instance.removeTexture(texture.getTextureName());
    }

    public String getTextureName() {
        return "terrain/" + name() + ".png";
    }

}
