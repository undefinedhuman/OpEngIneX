package de.undefinedhuman.core.shadows;

import de.undefinedhuman.core.camera.Camera;
import de.undefinedhuman.core.entity.Entity;
import de.undefinedhuman.core.entity.EntityManager;
import de.undefinedhuman.core.entity.EntityType;
import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.entity.ecs.component.mesh.MeshComponent;
import de.undefinedhuman.core.light.LightManager;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.opengl.OpenGLUtils;
import de.undefinedhuman.core.opengl.fbos.FBO;
import de.undefinedhuman.core.opengl.fbos.attachments.TextureAttachment;
import de.undefinedhuman.core.settings.panels.PanelObject;
import de.undefinedhuman.core.settings.types.mesh.Mesh;
import de.undefinedhuman.core.utils.Variables;
import de.undefinedhuman.core.utils.VectorUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.HashMap;

public class ShadowManager implements Manager {

    public static ShadowManager instance;

	private FBO shadowFbo;
	private ShadowShader shader;
	private ShadowBox shadowBox;
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f lightViewMatrix = new Matrix4f();
	private Matrix4f projectionViewMatrix = new Matrix4f();
	private Matrix4f offset = new Matrix4f().translate(0.5f, 0.5f, 0.5f).scale(0.5f);

	public ShadowManager() {
	    if(instance == null) instance = this;
	}

    @Override
    public void init() {
        shader = new ShadowShader();
        shadowBox = new ShadowBox(lightViewMatrix);
        shadowFbo = new FBO(Variables.SHADOW_MAP_SIZE, Variables.SHADOW_MAP_SIZE, 0)
                .addDepthAttachment(new TextureAttachment(GL14.GL_DEPTH_COMPONENT16))
                .setShadowFBO(true)
                .init();
    }

    @Override
	public void update(float delta) {
        updateOrthographicProjectionMatrix(shadowBox.getBounds());
        updateLightViewMatrix(LightManager.instance.getSun().getDirection(), shadowBox.getCenter());
        projectionViewMatrix
                .set(projectionMatrix)
                .mul(lightViewMatrix);
        shadowFbo.startShadow();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        shader.bind();
        shadowBox.update();
        for(EntityType entityType : EntityManager.instance.getEntitiesByTypeAndID().keySet()) {
            HashMap<Integer, ArrayList<Entity>> entitiesByBlueprintID = EntityManager.instance.getEntitiesByTypeAndID().get(entityType);
            for(Integer blueprintID : entitiesByBlueprintID.keySet()) {
                ArrayList<Entity> entitiesWithID = entitiesByBlueprintID.get(blueprintID);
                MeshComponent meshComponent;
                Entity baseEntity = entitiesWithID.get(0);
                if((meshComponent = (MeshComponent) baseEntity.getComponent(ComponentType.MESH)) == null) continue;
                for(PanelObject panelObject : meshComponent.getMeshes()) {
                    if(!(panelObject instanceof Mesh)) continue;
                    Mesh mesh = (Mesh) panelObject;
                    mesh.getVao().bind();
                    GL20.glEnableVertexAttribArray(0);
                    for(Entity entity : entitiesWithID) {
                        if(entity.getPosition().distance(Camera.instance.getPosition()) > Variables.VIEW_DISTANCE)
                            continue;
                        entity.updateMatrices();
                        shader.loadUniforms(new Matrix4f().set(projectionViewMatrix).mul(entity.getTransformationMatrix()));
                        OpenGLUtils.renderVao(mesh.getVao().getVertexCount());
                    }
                    GL20.glDisableVertexAttribArray(0);
                    mesh.getVao().unbind();
                }
            }
        }
        shader.unbind();
        shadowFbo.stopShadow();
	}

	@Override
	public void delete() {
	    shader.delete();
	    shadowFbo.delete();
	}

	private void updateLightViewMatrix(Vector3f direction, Vector3f center) {
		center.negate();
		lightViewMatrix
                .identity();
		float pitch = (float) Math.acos(new Vector2f(direction.x, direction.z).length());
		float yaw = (float) Math.toDegrees(((float) Math.atan(direction.x / direction.z)));
		yaw = direction.z > 0 ? yaw - 180 : yaw;
		lightViewMatrix
                .rotate(pitch, VectorUtils.X_AXIS)
                .rotate((float) -Math.toRadians(yaw), VectorUtils.Y_AXIS)
                .translate(center);
	}

	private void updateOrthographicProjectionMatrix(Vector3f bounds) {
	    projectionMatrix
                .identity()
                .m00(2f / bounds.x)
                .m11(2f / bounds.y)
                .m22(-2f / bounds.z)
                .m33(1);
	}

    public Matrix4f getToShadowMapSpaceMatrix() {
        return new Matrix4f(offset).mul(projectionViewMatrix);
    }

    public int getShadowMap() {
        return shadowFbo.getDepthBuffer();
    }

    protected Matrix4f getLightSpaceTransform() {
        return lightViewMatrix;
    }
}
