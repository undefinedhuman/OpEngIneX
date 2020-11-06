package de.undefinedhuman.core.entity.ecs.component.texture;

import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.entity.ecs.component.ComponentBlueprint;
import de.undefinedhuman.core.entity.ecs.component.ComponentParam;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FsFile;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.settings.SettingsObject;
import de.undefinedhuman.core.settings.types.BooleanSetting;
import de.undefinedhuman.core.settings.types.TextureSetting;

import java.util.HashMap;

public class TextureBlueprint extends ComponentBlueprint {

    private TextureSetting texture = new TextureSetting("Texture", "Unknown.png");
    private BooleanSetting culling = new BooleanSetting("Culling", true);

    public TextureBlueprint() {
        this.settings.add(texture, culling);
    }

    @Override
    public Component createInstance(HashMap<ComponentType, ComponentParam> params) {
        return new TextureComponent(texture.getString());
    }

    @Override
    public void load(FsFile parentDir, SettingsObject settingsObject) {
        super.load(parentDir, settingsObject);
        if(TextureManager.instance != null) TextureManager.instance.getTexture(texture.getString()).setCulling(culling.getBoolean());
    }

    @Override
    public void delete() {
        texture.delete();
    }

}
