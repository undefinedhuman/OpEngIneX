package de.undefinedhuman.core.entity.ecs.system;

import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.EntityType;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.entity.ecs.component.mesh.MeshComponent;
import de.undefinedhuman.core.entity.shader.EntityShader;
import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.opengl.Vao;
import de.undefinedhuman.core.resources.texture.Texture;
import de.undefinedhuman.core.resources.texture.TextureManager;

import java.util.ArrayList;
import java.util.HashMap;

public class RenderSystem implements System {

    private HashMap<EntityType, EntityShader> entityShader = new HashMap<>();

    @Override
    public void init() {
        for(EntityType type : EntityType.values())
            entityShader.put(type, type.createNewInstance());
    }

    @Override
    public void resize(int width, int height) {
        for(EntityShader shader : entityShader.values()) {
            shader.bind();
            shader.resize(width, height);
            shader.unbind();
        }
    }

    @Override
    public void update(float delta) {
        for(EntityType entityType : EntityManager.instance.getEntitiesByTypeAndID().keySet()) {
            EntityShader shader = entityShader.get(entityType);
            shader.bind();
            shader.loadUniforms();
            HashMap<Integer, ArrayList<Entity>> entitiesByBlueprintID = EntityManager.instance.getEntitiesByTypeAndID().get(entityType);
            for(Integer blueprintID : entitiesByBlueprintID.keySet()) {
                ArrayList<Entity> entitiesWithID = entitiesByBlueprintID.get(blueprintID);
                MeshComponent meshComponent;
                Entity baseEntity = entitiesWithID.get(0);
                if((meshComponent = (MeshComponent) baseEntity.getComponent(ComponentType.MESH)) == null) continue;
                Vao[] vaos = meshComponent.getVaos();
                for(int i = 0; i < vaos.length; i++) {
                    Vao vao = vaos[i];
                    Texture texture = TextureManager.instance.getTexture(meshComponent.getTextures()[i]);
                    vao.start();
                    texture.bind();
                    for(Entity entity : entitiesWithID) {
                        shader.loadUniforms(entity);
                        OpenGLUtils.renderVao(vao.getVertexCount());
                    }
                    texture.unbind();
                    vao.stop();
                }
            }
            shader.unbind();
        }
    }

    @Override
    public void delete() {}

}
