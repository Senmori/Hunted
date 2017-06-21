package net.senmori.hunted.lib.selectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntityGamemodeSelectorFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        List<Predicate<Entity>> list = Lists.newArrayList();
        String mode = EntitySelector.getArgument(arguments, EntitySelector.ARG_MODE);

        if(mode == null) {
            return list;
        }

        final boolean invert = mode.startsWith("!");

        if(invert) {
            mode = mode.substring(1);
        }

        GameMode gameMode;
        try {
            int i = Integer.parseInt(mode);
            gameMode = GameMode.getByValue(i);
        } catch(NumberFormatException e) {
            gameMode = GameMode.valueOf(mode.toUpperCase());
        } catch(IllegalArgumentException e) {
            gameMode = null;
        }

        final GameMode type = gameMode;

        list.add(new Predicate<Entity>() {
            @Override
            public boolean apply(@Nullable Entity entity) {
                if(!(entity instanceof Player ) || type == null) {
                    return false;
                }
                Player player = (Player)entity;
                GameMode playerMode = player.getGameMode();
                return invert ? playerMode != type : playerMode == type;
            }
        });
        return list;
    }
}
