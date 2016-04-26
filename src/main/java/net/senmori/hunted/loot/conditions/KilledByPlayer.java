package net.senmori.hunted.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.core.LootContext;
import net.senmori.hunted.loot.storage.ResourceLocation;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.util.Random;

/**
 * Created by Senmori on 4/13/2016.
 */
public class KilledByPlayer implements LootCondition {

    private boolean inverse;

    public KilledByPlayer(boolean inverse) { this.inverse = inverse; }

    public boolean getInverse() { return this.inverse; }

    public void setInverse(boolean inverse) { this.inverse = inverse; }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        boolean flag = context.getKillerPlayer() != null;
        return flag == !inverse;
    }

    public static class Serializer extends LootCondition.Serializer<KilledByPlayer> {
        protected Serializer() { super(new ResourceLocation("killed_by_player"), KilledByPlayer.class); }

        @Override
        public void serialize(JsonObject json, KilledByPlayer type, JsonSerializationContext context) {
            json.addProperty("inverse", type.getInverse());
        }

        @Override
        public KilledByPlayer deserialize(JsonObject json, JsonDeserializationContext context) {
            return new KilledByPlayer(JsonUtils.getBoolean(json, "inverse", false));
        }
    }
}
