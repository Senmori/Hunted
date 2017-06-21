package net.senmori.hunted.lib.selectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class RadiusPredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        double min = (double)EntitySelector.getInt(arguments, EntitySelector.ARG_RANGE_MIN, -1);
        double max = (double)EntitySelector.getInt(arguments, EntitySelector.ARG_RANGE_MAX, -1);
        final boolean negMin = min < -0.5D;
        final boolean negMax = max < -0.5D;
        if(negMin && negMax) {
            return Collections.emptyList();
        }

        double minRound = Math.max(min, 1.0E-4D);
        final double minRoundSq = minRound * minRound;
        double maxRound = Math.max(max, 1.0E-4D);
        final double maxRoundSq = maxRound * maxRound;
        return Lists.newArrayList(new Predicate<Entity>() {
            @Override
            public boolean apply(@Nullable Entity entity) {
                if(entity == null) {
                    return false;
                }
                double dist = location.distance(entity.getLocation().toVector());
                return (negMin || dist >= minRoundSq) && (negMax || dist <= maxRoundSq);
            }
        });
    }
}
