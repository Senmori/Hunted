package net.senmori.hunted.selector.selectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public class EntityScorePredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        Map<String, Integer> scoresMap = EntitySelector.getScoreMap(arguments);
        if(scoresMap.isEmpty()) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(new Predicate<Entity>() {
            @Override
            public boolean apply(@Nullable Entity entity) {
                if(entity == null) {
                    return false;
                }

                Scoreboard scoreboard = entity.getServer().getScoreboardManager().getMainScoreboard();

                for(Map.Entry<String, Integer> entry : scoresMap.entrySet()) {
                    String key = entry.getKey();
                    boolean isMin = false;

                    if(key.endsWith("_min") && key.length() > 0) {
                        isMin = true;
                        key = key.substring(0, key.length() - 4);
                    }

                    Objective objective = scoreboard.getObjective(key);

                    if(objective == null) {
                        return false;
                    }

                    String entityname = (entity instanceof Player) ? entity.getName() : entity.getUniqueId().toString();

                    Score score = objective.getScore(entityname);
                    if(score == null) {
                        return false;
                    }

                    int currentScore = score.getScore();
                    if(currentScore < entry.getValue() && isMin) {
                        return false;
                    }
                    if(currentScore > entry.getValue() && !isMin) {
                        return false;
                    }
                }
                return true;
            }
        });
    }
}
