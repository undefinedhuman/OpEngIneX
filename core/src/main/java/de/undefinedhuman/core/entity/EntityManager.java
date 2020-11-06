package de.undefinedhuman.core.entity;

import de.undefinedhuman.core.entity.ecs.system.System;
import de.undefinedhuman.core.manager.Manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class EntityManager extends Manager {

    public static EntityManager instance;

    private HashMap<Integer, Entity> entities = new HashMap<>();
    private ArrayList<Integer> entitiesToRemove = new ArrayList<>();
    private ArrayList<System> systems = new ArrayList<>();

    public EntityManager() {
        if(instance == null) instance = this;
        systems.add(new RenderSystem());
    }

    @Override
    public void init() {
        for(System system : systems) system.initSystem();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        for(System system : systems) system.resize(width, height);
    }

    @Override
    public void update(float delta) {
        for(System system : systems) for(Entity entity : entities.values()) system.update(entity, delta);
        if(entitiesToRemove.size() > 0) for(Integer worldID : entitiesToRemove) { entities.get(worldID).delete(); entities.remove(worldID); }
        entitiesToRemove.clear();
    }

    @Override
    public void render() {
        for(Entity entity : entities.values()) entity.getTransform().update();
        RenderSystem.instance.render(entities.values());
    }

    @Override
    public void delete() {
        deleteEntities(entities.values());
        clearEntityLists();
        systems.clear();
    }

    public void addEntity(int worldID, Entity entity) {
        this.entities.put(worldID, entity.setWorldID(worldID));
        for(System system : systems) system.initEntity(entity);
        RenderSystem.instance.sorted = false;
    }

    public Entity getEntity(int worldID) {
        if(!hasEntity(worldID)) return null;
        return entities.get(worldID);
    }

    private void deleteEntities(Collection<Entity> entities) {
        for(Entity entity : entities) entity.delete();
    }

    public void removeEntity(int worldID) {
        if(!hasEntity(worldID)) return;
        this.entitiesToRemove.add(worldID);
        RenderSystem.instance.sorted = false;
    }

    public boolean hasEntity(int worldID) {
        return entities.containsKey(worldID);
    }

    private void clearEntityLists() {
        entitiesToRemove.clear();
        entities.clear();
    }

}
