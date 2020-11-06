package de.undefinedhuman.core.entity.ecs.component.mesh;

import de.undefinedhuman.core.entity.ecs.component.Component;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.opengl.Vao;

public class MeshComponent extends Component {

    private Vao[] vaos;

    public MeshComponent(Vao[] vaos) {
        this.vaos = vaos;
    }

    @Override
    public void load(FileReader reader) {}

    @Override
    public void save(FileWriter writer) {}

    public Vao[] getVaos() {
        return vaos;
    }

}
