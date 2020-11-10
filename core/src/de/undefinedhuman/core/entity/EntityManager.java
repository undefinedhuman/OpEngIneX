package de.undefinedhuman.core.entity;

import de.undefinedhuman.core.entity.ecs.system.RenderSystem;
import de.undefinedhuman.core.entity.ecs.system.System;
import de.undefinedhuman.core.manager.Manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EntityManager extends Manager {

    public static EntityManager instance;

    private HashMap<Integer, Entity> entities = new HashMap<>();
    private HashMap<EntityType, HashMap<Integer, ArrayList<Entity>>> entitiesByTypeAndID = new HashMap<>();
    private ArrayList<Integer> entitiesToRemove = new ArrayList<>();
    private ArrayList<System> systems = new ArrayList<>();

    public EntityManager() {
        if (instance == null) instance = this;
        addSystems(new RenderSystem());
    }

    @Override
    public void init() {
        clearEntities();
        for(System system : systems)
            system.init();
        for(EntityType type : EntityType.values())
            entitiesByTypeAndID.put(type, new HashMap<>());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        for(System system : systems)
            system.resize(width, height);
    }

    @Override
    public void update(float delta) {
        for (System system : systems)
            system.update(delta);
        if (entitiesToRemove.size() == 0) return;
        for (int worldID : entitiesToRemove) {
            Entity entity = entities.get(worldID);
            ArrayList<Entity> entitiesByID = entitiesByTypeAndID
                    .get(entity.getEntityType())
                    .get(entity.getBlueprintID());
            entitiesByID.remove(entity);
            if(entitiesByID.size() == 0)
                entitiesByTypeAndID
                        .get(entity.getEntityType())
                        .remove(entity.getBlueprintID());
            entities.remove(worldID);
        }
        entitiesToRemove.clear();
    }

    @Override
    public void render() {
        for(Entity entity : entities.values())
            entity.updateMatrices();
        for(System system : systems)
            system.render();
    }

    @Override
    public void delete() {
        systems.clear();
        clearEntities();
    }

    public void addEntity(int worldID, Entity entity) {
        if(entity == null) return;
        this.entities.put(worldID, entity.setWorldID(worldID));

        HashMap<Integer, ArrayList<Entity>> entityTypeList = entitiesByTypeAndID.get(entity.getEntityType());
        if(entityTypeList.containsKey(entity.getBlueprintID())) entityTypeList.get(entity.getBlueprintID()).add(entity);
        else entityTypeList.put(entity.getBlueprintID(), addEntityArrayList(entity));
    }

    private ArrayList<Entity> addEntityArrayList(Entity entity) {
        ArrayList<Entity> entityArrayList = new ArrayList<>();
        entityArrayList.add(entity);
        return entityArrayList;
    }

    public Entity getEntity(int worldID) {
        if(!entities.containsKey(worldID)) return null;
        return entities.get(worldID);
    }

    public void removeEntity(int worldID) {
        if(!entities.containsKey(worldID)) return;
        this.entitiesToRemove.add(worldID);
    }

    private void addSystems(System... systems) {
        this.systems.addAll(Arrays.asList(systems));
    }

    public HashMap<EntityType, HashMap<Integer, ArrayList<Entity>>> getEntitiesByTypeAndID() {
        return entitiesByTypeAndID;
    }

    private void clearEntities() {
        entitiesToRemove.clear();
        for(HashMap<Integer, ArrayList<Entity>> entitiesByType : entitiesByTypeAndID.values()) {
            for(ArrayList<Entity> entitiesByBlueprintID : entitiesByType.values())
                entitiesByBlueprintID.clear();
            entitiesByType.clear();
        }
        entitiesByTypeAndID.clear();
        for(Entity entity : entities.values())
            entity.delete();
        entities.clear();
    }

}
