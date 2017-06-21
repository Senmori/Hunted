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

public class EntityTagPredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        List<Predicate<Entity>> list = Lists.newArrayList();
        String tags = EntitySelector.getArgument(arguments, EntitySelector.ARG_ENTITY_TAG);
        final boolean invert = tags != null && tags.startsWith("!");

        if(invert) {
            tags = tags.substring(1);
        }

        if(tags != null) {
            final String type = tags;

            list.add(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    if(entity == null) {
                        return false;
                    }
                    if("".equals(type)) {
                        return entity.getScoreboardTags().isEmpty() != invert;
                    } else {
                        return entity.getScoreboardTags().contains(type) != invert;
                    }
                }
            });
        }
        return list;
    }
}
