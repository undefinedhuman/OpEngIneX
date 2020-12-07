package de.undefinedhuman.core.entity;

import de.undefinedhuman.core.entity.ecs.component.ComponentType;
import de.undefinedhuman.core.entity.ecs.system.RenderSystem;
import de.undefinedhuman.core.entity.ecs.system.System;
import de.undefinedhuman.core.manager.Manager;
import de.undefinedhuman.core.transform.Transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class EntityManager implements Manager {

    public static EntityManager instance;

    private HashMap<Integer, Entity> entities = new HashMap<>();
    private HashMap<EntityType, HashMap<Integer, ArrayList<Entity>>> entitiesByTypeAndID = new HashMap<>();
    private ArrayList<Integer> entitiesToRemove = new ArrayList<>();
    private ArrayList<System> systems = new ArrayList<>();

    private int maxWorldID = 0;

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
        systems.forEach(system -> system.resize(width, height));
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
        entities.values()
                .forEach(Transform::updateMatrices);

        systems.forEach(System::render);
    }

    @Override
    public void delete() {
        systems.clear();
        clearEntities();
    }

    public void addEntity(int worldID, Entity entity) {
        if(entity == null) return;
        if(worldID > maxWorldID) maxWorldID = worldID;
        this.entities.put(worldID, entity.setWorldID(worldID));
        ArrayList<Entity> entityArrayList = entitiesByTypeAndID.get(entity.getEntityType()).computeIfAbsent(entity.getBlueprintID(), k -> new ArrayList<>());
        entityArrayList.add(entity);
    }

    public void addEntity(Entity entity) {
        if(entity == null) return;
        this.entities.put(++maxWorldID, entity.setWorldID(maxWorldID));
        ArrayList<Entity> entityArrayList = entitiesByTypeAndID.get(entity.getEntityType()).computeIfAbsent(entity.getBlueprintID(), k -> new ArrayList<>());
        entityArrayList.add(entity);
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

    public Stream<Entity> getEntitiesWithComponent(ComponentType type) {
        return entities
                .values()
                .stream()
                .filter(entity -> entity.hasComponent(type));
    }

    private void clearEntities() {
        entitiesToRemove.clear();
        entitiesByTypeAndID.values().forEach(entitiesByType -> {
            entitiesByType.values().forEach(ArrayList::clear);
            entitiesByType.clear();
        });
        entitiesByTypeAndID.clear();
        entities
                .values()
                .forEach(Entity::delete);
        entities.clear();
    }

}
