package net.senmori.hunted.lib.selectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.senmori.hunted.util.HuntedUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class RotationPredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        List<Predicate<Entity>> list = Lists.newArrayList();
        if(arguments.containsKey(EntitySelector.ARG_ROTY_MIN) || arguments.containsKey(EntitySelector.ARG_ROTY_MAX)) {
            final int rotYMin = HuntedUtil.clampAngle(EntitySelector.getInt(arguments, EntitySelector.ARG_ROTY_MIN, 0));
            final int rotYMax = HuntedUtil.clampAngle(EntitySelector.getInt(arguments, EntitySelector.ARG_ROTY_MAX, 359));

            list.add(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    if(entity == null) {
                        return false;
                    }
                    int yaw = HuntedUtil.clampAngle(HuntedUtil.floor(entity.getLocation().getYaw()));
                    if(rotYMin > rotYMax) {
                        return yaw >= rotYMin || yaw <= rotYMax;
                    } else {
                        return yaw >= rotYMin && yaw <= rotYMax;
                    }
                }
            });
        }

        if(arguments.containsKey(EntitySelector.ARG_ROTX_MIN) || arguments.containsKey(EntitySelector.ARG_ROTX_MAX)) {
            final int rotXMin = HuntedUtil.clampAngle(EntitySelector.getInt(arguments, EntitySelector.ARG_ROTX_MIN, 0));
            final int rotXMax = HuntedUtil.clampAngle(EntitySelector.getInt(arguments, EntitySelector.ARG_ROTX_MAX, 359));

            list.add(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    if(entity == null) {
                        return false;
                    }
                    int pitch = HuntedUtil.clampAngle(HuntedUtil.floor(entity.getLocation().getPitch()));
                    if(rotXMin > rotXMax) {
                        return pitch >= rotXMin || pitch <= rotXMax;
                    } else {
                        return pitch >= rotXMin && pitch <= rotXMax;
                    }
                }
            });
        }
        return list;
    }
}
