package de.undefinedhuman.core.entity.ecs.component.texture;

import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;

public class TextureComponent extends Component {

    private String textureName;

    public TextureComponent(String textureName) {
        this.textureName = textureName;
    }

    @Override
    public void load(FileReader reader) {}

    @Override
    public void save(FileWriter writer) {}

    public String getTextureName() {
        return textureName;
    }

}
