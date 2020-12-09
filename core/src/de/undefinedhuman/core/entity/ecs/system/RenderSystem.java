package de.undefinedhuman.core.entity.ecs.system;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.EntityType;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.entity.ecs.component.mesh.MeshComponent;
import de.undefinedhuman.core.entity.shader.EntityShader;
import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.opengl.shader.ShaderProgram;
import de.undefinedhuman.core.resources.texture.Texture;
import de.undefinedhuman.core.resources.texture.TextureManager;
import de.undefinedhuman.core.settings.panels.PanelObject;
import de.undefinedhuman.core.settings.types.mesh.Mesh;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.window.Window;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;

public class RenderSystem implements System {

    private HashMap<EntityType, EntityShader> entityShader = new HashMap<>();

    private final Matrix4f projectionViewMatrix = new Matrix4f();
    private final FrustumIntersection frustumIntersection = new FrustumIntersection();

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
        frustumIntersection.set(projectionViewMatrix.set(Camera.instance.getProjectionMatrix()).mul(Camera.instance.getViewMatrix()));
    }

    private Matrix4f generateProjectionMatrix() {
        float xScale = (float) (1f / Math.tan(Math.toRadians(Variables.FOV_ANGLE / 4f))), yScale = xScale * Window.instance.getAspectRatio();
        return new Matrix4f()
                .m00(xScale)
                .m11(yScale)
                .m22(-((Variables.FAR_PLANE + Variables.NEAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m23(-1)
                .m32(-((2 * Variables.NEAR_PLANE * Variables.FAR_PLANE) / Variables.FRUSTUM_LENGTH))
                .m33(0);
    }

    @Override
    public void render() {
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
                for(PanelObject panelObject : meshComponent.getMeshes()) {
                    if(!(panelObject instanceof Mesh)) continue;
                    Mesh mesh = (Mesh) panelObject;
                    Texture texture = TextureManager.instance.getTexture(mesh.texture.getString()).setCulling(mesh.culling.getBoolean());
                    mesh.getVao().start();
                    shader.loadUniforms(mesh);
                    texture.bind();
                    for(Entity entity : entitiesWithID) {
                        if(entity.getPosition().distance(Camera.instance.getPosition()) > Variables.VIEW_DISTANCE || !frustumIntersection.testSphere(entity.getPosition(), mesh.maxDistanceSetting.getFloat() * entity.getScale()))
                            continue;
                        entity.updateMatrices();
                        shader.loadUniforms(entity);
                        OpenGLUtils.renderVao(mesh.getVao().getVertexCount());
                    }
                    texture.unbind();
                    mesh.getVao().stop();
                }
            }
            shader.unbind();
        }
    }

    @Override
    public void delete() {
        for(ShaderProgram shader : entityShader.values())
            shader.delete();
    }

}
