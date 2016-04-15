package net.senmori.hunted.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.senmori.hunted.loot.adapter.InheritanceAdapter;
import net.senmori.hunted.loot.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.Random;


public class RandomValueRange {

	private float min;
	private float max;

	public RandomValueRange(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public RandomValueRange(float value) {
		this.min = value;
		this.max = value;
	}

	public float getMin() { return this.min; }

	public float getMax() { return this.max; }

	public int generateInt(Random rand) {
		int vMin = floorFloat(min);
		int vMax = floorFloat(max);
		return vMin > vMax ? vMin : rand.nextInt(vMax - vMin +1) + vMin;
	}

	public float generateFloat(Random rand) {
		return min >= max ? min : rand.nextFloat() * (max - min) + min;
	}

	public boolean isInRange(int value) { return (float)value <= this.max && (float)value >= this.min; }

	private int floorFloat(float value) {
		int i = (int)value;
		return value < (float)i ? i-1 : i;
	}


	public static class Serializer extends InheritanceAdapter<RandomValueRange> {
		public Serializer() {}

		@Override
		public RandomValueRange deserialize(JsonElement element, Type type, JsonDeserializationContext context) {
			if (JsonUtils.isNumber(element)) {
				return new RandomValueRange(JsonUtils.getFloat(element, "value"));
            } else {
				JsonObject object = JsonUtils.getJsonObject(element, "value");
				float min = JsonUtils.getFloat(object, "min");
                float max = JsonUtils.getFloat(object, "max");
                return new RandomValueRange(min, max);
            }
        }

		@Override
		public JsonElement serialize(RandomValueRange range, Type type, JsonSerializationContext context) {
            if(range.min == range.max) {
				return new JsonPrimitive((int) range.min);
			} else {
                JsonObject object = new com.google.gson.JsonObject();
				object.addProperty("min", (int) range.min);
				object.addProperty("max", (int) range.max);
				return object;
            }
		}
	}

}
