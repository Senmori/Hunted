package net.senmori.hunted.lib.selectors;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public interface EntitySelectorFactory {

    /**
     *  Called every time {@link EntitySelector#}
     * @param arguments
     * @param tokenSelector
     * @param sender
     * @param location
     * @return
     */
    @Nonnull
    List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location);
}
