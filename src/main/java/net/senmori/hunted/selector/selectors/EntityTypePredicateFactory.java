package net.senmori.hunted.selector.selectors;

import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntityTypePredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        String s = EntitySelector.getArgument(arguments, EntitySelector.ARG_ENTITY_TYPE);

        if(s == null || !s.equals("r") && !s.equals("s")) {
            return !s.equals("e") && !s.equals("s") ? Collections.singletonList(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    return entity instanceof Player;
                }
            }) : Collections.emptyList();
        } else {

            final boolean invert = s.startsWith("!");
            return Collections.singletonList(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    return entity.getType().getName().equals(invert ? s.substring(1) : s);
                }
            });
        }
    }
}
