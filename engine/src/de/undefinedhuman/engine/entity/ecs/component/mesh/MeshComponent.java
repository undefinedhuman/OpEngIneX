package de.undefinedhuman.engine.entity.ecs.component.mesh;

import de.undefinedhuman.engine.entity.ecs.component.Component;
import de.undefinedhuman.engine.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.file.FileReader;
import de.undefinedhuman.core.file.FileWriter;
import de.undefinedhuman.core.file.LineSplitter;
import de.undefinedhuman.core.file.LineWriter;
import de.undefinedhuman.engine.settings.panels.PanelObject;

import java.util.Collection;

public class MeshComponent extends Component {

    private MeshBlueprint meshBlueprint;

    public MeshComponent(MeshBlueprint blueprint) {
        super(ComponentType.MESH);
        this.meshBlueprint = blueprint;
    }

    public Collection<PanelObject> getMeshes() {
        return meshBlueprint.getMeshes();
    }

    @Override
    public void load(FileReader reader) {}

    @Override
    public void save(FileWriter writer) {}

    @Override
    public void send(LineWriter writer) { }

    @Override
    public void read(LineSplitter splitter) { }

}
