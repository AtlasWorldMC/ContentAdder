package fr.atlasworld.content.datagen.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.atlasworld.content.api.utils.Identifier;

import java.util.*;

public class ItemModelFile {
    private Identifier parent;
    private Map<String, Identifier> textures;
    private List<AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<String, Float>, Identifier>> overrides;

    public ItemModelFile() {
        this.textures = new HashMap<>();
        this.overrides = new ArrayList<>();
    }

    public void parent(Identifier parent) {
        this.parent = parent;
    }

    public void addTexture(String layer, Identifier texture) {
        textures.put(layer, texture);
    }

    public void addOverride(String condition, float conditionVal, Identifier model) {
        overrides.forEach(entry -> {
            if (entry.getKey().getKey().equals(condition) && entry.getKey().getValue().equals(conditionVal)) {
                overrides.remove(entry);
            }
        });
        overrides.add(new AbstractMap.SimpleEntry<>(new AbstractMap.SimpleEntry<>(condition, conditionVal), model));
    }

    public Identifier getParent() {
        return parent;
    }

    public Map<String, Identifier> getTextures() {
        return textures;
    }

    public void setTextures(Map<String, Identifier> textures) {
        this.textures = textures;
    }

    public List<AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<String, Float>, Identifier>> getOverrides() {
        return overrides;
    }

    public void setOverrides(List<AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<String, Float>, Identifier>> overrides) {
        this.overrides = overrides;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        if (this.parent != null) {
            json.addProperty("parent", this.parent.toString());
        }

        if (!this.textures.isEmpty()) {
            JsonObject textures = new JsonObject();
            for (Map.Entry<String, Identifier> e : this.textures.entrySet()) {
                textures.addProperty(e.getKey(), e.getValue().toString());
            }
            json.add("textures", textures);
        }

        if (!this.overrides.isEmpty()) {
            JsonArray overridesJson = new JsonArray();
            this.overrides.forEach(entry -> {
                JsonObject override = new JsonObject();
                JsonObject predicate = new JsonObject();
                predicate.addProperty(entry.getKey().getKey(), entry.getKey().getValue());
                override.add("predicate", predicate);
                override.addProperty("model", entry.getValue().toString());
                overridesJson.add(override);
            });
            json.add("overrides", overridesJson);
        }
        return json;
    }

    public static ItemModelFile getModelFromString(String str) {
        JsonObject json = JsonParser.parseString(str).getAsJsonObject();
        ItemModelFile model = new ItemModelFile();

        if (json.get("parent") != null) {
            model.parent(new Identifier(json.get("parent").getAsString()));
        }
        if (json.get("textures") != null) {
            json.get("textures").getAsJsonObject().entrySet().forEach(entry ->
                    model.addTexture(entry.getKey(), new Identifier(entry.getValue().getAsString())));
        }
        if (json.get("overrides") != null) {
            json.get("overrides").getAsJsonArray().forEach(jsonElement ->
                    model.addOverride(jsonElement.getAsJsonObject().get("predicate").getAsJsonObject().entrySet()
                            .stream().toList().get(0).getKey(), jsonElement.getAsJsonObject().get("predicate").getAsJsonObject().entrySet()
                            .stream().toList().get(0).getValue().getAsFloat(), new Identifier(jsonElement.getAsJsonObject().get("model").getAsString())));
        }
        return model;
    }
}
