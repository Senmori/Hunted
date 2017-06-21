package net.senmori.hunted.lib.selectors;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class EntityTeamPredicateFactory implements EntitySelectorFactory {
    @Nonnull
    @Override
    public List<Predicate<Entity>> createPredicates(Map<String, String> arguments, String tokenSelector, CommandSender sender, Vector location) {
        List<Predicate<Entity>> list = Lists.newArrayList();
        String team = EntitySelector.getArgument(arguments, EntitySelector.ARG_TEAM_NAME);
        final boolean invert = team != null && team.startsWith("!");

        if(invert) {
            team = team.substring(1);
        }

        if(team != null) {
            final String name = team;

            list.add(new Predicate<Entity>() {
                @Override
                public boolean apply(@Nullable Entity entity) {
                    if(!(entity instanceof LivingEntity)) {
                        return false;
                    }

                    LivingEntity livingEntity = (LivingEntity) entity;
                    Team team = livingEntity.getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(livingEntity.getName());
                    String teamName = team == null ? "" : team.getName();
                    return teamName.equals(name) != invert;
                }
            });
        }
        return list;
    }
}
