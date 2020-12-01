package de.undefinedhuman.core.entity.ecs.component.mesh;

import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.file.LineWriter;
import de.undefinedhuman.core.opengl.Vao;

public class MeshComponent extends Component {

    private Vao[] vaos;
    private String[] textures;

    public MeshComponent(Vao[] vaos, String[] textures) {
        super(ComponentType.MESH);
        this.vaos = vaos;
        this.textures = textures;
    }

    @Override
    public void load(FileReader reader) {}

    @Override
    public void save(FileWriter writer) {}

    public String[] getTextures() {
        return textures;
    }

    public Vao[] getVaos() {
        return vaos;
    }

    @Override
    public void send(LineWriter writer) { }

    @Override
    public void read(LineSplitter splitter) { }

}
