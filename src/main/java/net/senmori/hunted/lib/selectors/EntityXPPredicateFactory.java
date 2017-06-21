package net.senmori.hunted.lib.selectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntityXPPredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        List<Predicate<Entity>> list = Lists.newArrayList();
        final int min = EntitySelector.getInt(arguments, EntitySelector.ARG_LEVEL_MIN, -1);
        final int max = EntitySelector.getInt(arguments, EntitySelector.ARG_LEVEL_MAX, -1);

        if(min > -1 || max > -1) {
            list.add(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    if(entity == null || !(entity instanceof Player)) {
                        return false;
                    }
                    Player player = (Player)entity;
                    return (min <= -1 || player.getExpToLevel() >= min) && (max <= -1 || player.getExpToLevel() <= max);
                }
            });
        }
        return list;
    }
}
