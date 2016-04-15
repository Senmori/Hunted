package net.senmori.hunted.loot.conditions;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.EntityTarget;
import net.senmori.hunted.loot.LootContext;
import net.senmori.hunted.loot.RandomValueRange;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Senmori on 4/13/2016.
 */
public class EntityHasScore implements LootCondition {

    private Map<String, RandomValueRange> scores;
    private EntityTarget target;

    public EntityHasScore(Map<String, RandomValueRange> scores, EntityTarget target) {
        this.scores = scores;
        this.target = target;
    }

    public void setTarget(EntityTarget target) { this.target = target; }

    public void addScore(String scoreName, RandomValueRange scoreRange) { scores.put(scoreName, scoreRange); }

    public EntityTarget getTarget() { return this.target; }

    public Map<String, RandomValueRange> getScores() { return this.scores; }

    public RandomValueRange getScore(String name) { return scores.get(name); }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        Entity entity = context.getEntity(this.target);
        if (entity == null) return false;

        Scoreboard globalScoreboard = entity.getServer().getScoreboardManager().getMainScoreboard();
        Iterator iter = this.scores.entrySet().iterator();
        Map.Entry entry;

        do {
            if (!iter.hasNext()) { return true; }
            entry = (Map.Entry) iter.next();
        }
        while (entityHasScore(entity, globalScoreboard, (String) entry.getKey(), (RandomValueRange) entry.getValue()));
        return false;
    }

    protected boolean entityHasScore(Entity entity, Scoreboard board, String scoreName, RandomValueRange range) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            // because Players can have their own scoreboard
            for (Objective objective : player.getScoreboard().getObjectives()) {
                if (objective.getName().equals(scoreName)) {
                    return range.isInRange(objective.getScore(player.getName()).getScore());
                }
            }
            // check global scoreboard just in case
            for (Objective obj : board.getObjectives()) {
                if (obj.getName().equals(scoreName)) {
                    return range.isInRange(obj.getScore(entity.getUniqueId().toString()).getScore());
                }
            }
        }
        if (board.getEntries().contains(entity.getUniqueId().toString())) {
            // check global scoreboard for this entity
            for (Objective objective : board.getObjectives()) {
                if (objective.getName().equals(scoreName)) {
                    return range.isInRange(objective.getScore(entity.getUniqueId().toString()).getScore());
                }
            }
        }
        return false;
    }

    public static class Serializer extends LootCondition.Serializer<EntityHasScore> {
        protected Serializer() { super(new ResourceLocation("entity_scores"), EntityHasScore.class); }

        @Override
        public void serialize(JsonObject json, EntityHasScore type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            Iterator iter = type.scores.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                jsonObject.add((String) entry.getKey(), context.serialize(entry.getValue()));
            }
            json.add("scores", jsonObject);
            json.addProperty("entity", type.target.getTargetType());
        }

        @Override
        public EntityHasScore deserialize(JsonObject json, JsonDeserializationContext context) {
            Set set = JsonUtils.getJsonObject(json, "scores").entrySet();
            LinkedHashMap map = Maps.newLinkedHashMap();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                map.put(entry.getKey(), JsonUtils.deserializeClass((JsonElement) entry.getValue(), "score", context, RandomValueRange.class));
            }
            return new EntityHasScore(map, EntityTarget.fromString(JsonUtils.getString(json, "entity")));
        }
    }
}
