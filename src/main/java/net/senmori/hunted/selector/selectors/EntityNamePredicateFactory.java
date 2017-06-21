package net.senmori.hunted.selector.selectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityNamePredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        List<Predicate<Entity>> list = Lists.newArrayList();
        String name = EntitySelector.getArgument(arguments, EntitySelector.ARG_PLAYER_NAME);
        final boolean invert = name != null && name.startsWith("!");

        if(invert) {
            name = name.substring(1);
        }

        if(name != null) {
            final String eName = name;
            list.add(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    return entity != null && entity.getName().equals(eName) != invert;
                }
            });
        }
        return list;
    }
}
