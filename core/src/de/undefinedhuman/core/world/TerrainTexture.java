package de.undefinedhuman.core.world;

import de.undefinedhuman.core.resources.texture.TextureManager;

public enum TerrainTexture {

    GRASS;

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
