package net.senmori.hunted.selector;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.senmori.hunted.util.HuntedUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntitySelector {
    private static final String MINECRAFT_SELECTOR_PERMISSION = "minecraft.command.selector";
    private static final String SCORE_PREFIX = "score_";

    protected static final Pattern TOKEN_PATTERN = Pattern.compile("^@([pares])(?:\\[([^ ]*)\\])?$");
    protected static final Splitter COMMA_SPLITTER = Splitter.on(',').omitEmptyStrings();
    protected static final Splitter EQUAL_SPLITTER = Splitter.on('-').limit(2);
    protected static final Set<String> VALID_ARGS = Sets.newHashSet();
    protected static final String ARG_RANGE_MAX = addArgument("r");
    protected static final String ARG_RANGE_MIN = addArgument("rm");
    protected static final String ARG_LEVEL_MAX = addArgument("l");
    protected static final String ARG_LEVEL_MIN = addArgument("lm");
    protected static final String ARG_COORD_X = addArgument("x");
    protected static final String ARG_COORD_Y = addArgument("y");
    protected static final String ARG_COORD_Z = addArgument("z");
    protected static final String ARG_DELTA_X = addArgument("dx");
    protected static final String ARG_DELTA_Y = addArgument("dy");
    protected static final String ARG_DELTA_Z = addArgument("dz");
    protected static final String ARG_ROTX_MAX = addArgument("rx");
    protected static final String ARG_ROTX_MIN = addArgument("rxm");
    protected static final String ARG_ROTY_MAX = addArgument("ry");
    protected static final String ARG_ROTY_MIN = addArgument("rym");
    protected static final String ARG_COUNT = addArgument("c");
    protected static final String ARG_MODE = addArgument("m");
    protected static final String ARG_TEAM_NAME = addArgument("team");
    protected static final String ARG_PLAYER_NAME = addArgument("name");
    protected static final String ARG_ENTITY_TYPE = addArgument("type");
    protected static final String ARG_ENTITY_TAG = addArgument("tag");
    private static final Predicate<String> IS_VALID_ARGUMENT = new Predicate<String>() {
        @Override
        public boolean apply(@Nullable String s) {
            return s != null && (EntitySelector.VALID_ARGS.contains(s) || (s.length() > SCORE_PREFIX.length() && s.startsWith(SCORE_PREFIX)));
        }
    };
    // ALL default selectors
    private static Set<String> WORLD_BINDING_ARGS = Sets.newHashSet(ARG_COORD_X, ARG_COORD_Y, ARG_COORD_Z, ARG_DELTA_X, ARG_DELTA_Y, ARG_DELTA_Z, ARG_RANGE_MIN, ARG_RANGE_MAX);

    public static String addArgument(String argument) {
        VALID_ARGS.add(argument);
        return argument;
    }

    @Nullable
    public static Player matchOnePlayer(CommandSender sender, String token) {
        return null;
    }

    public static List<Player> matchAllPlayers(CommandSender sender, String token) {
        return null;
    }

    public static Entity matchOneEntity(CommandSender sender, String token) {
        return null;
    }

    public static List<Entity> matchAllEntities(CommandSender sender, String token) {
        return null;
    }


    protected static <T extends Entity> List<T> matchEntities(CommandSender sender, String token, Class<? extends T> targetClass) {
        Matcher matcher = TOKEN_PATTERN.matcher(token);

        if(matcher.matches() && sender.hasPermission(MINECRAFT_SELECTOR_PERMISSION)) {
            Map<String, String> map = getArgumentMap(matcher.group(2));

            if(! isEntityTypeValid(sender, map)) {
                return Collections.<T>emptyList();
            } else {
                String s = matcher.group(1);
                Location location = getLocationFromArguments(map, sender);
                Vector vector3D = getPositionFromArguments(map, sender);
                List<World> worlds = getWorlds(sender, map);
                List<T> entities = Lists.<T>newArrayList();

                for(World world : worlds) {
                    if(world != null) {
                        List<Predicate<Entity>> predicates = Lists.newArrayList();
                        // fill predicates list
                            //TODO: Add predicates to list from factory list

                        // get predicates above this
                        if("s".equalsIgnoreCase(s) && sender instanceof Entity) {
                            Entity entity = ( (Entity) sender );
                            if(entity != null && targetClass.isAssignableFrom(entity.getClass())) {

                                if(map.containsKey(ARG_DELTA_X) || map.containsKey(ARG_DELTA_Y) || map.containsKey(ARG_DELTA_Z)) {
                                    int x = getInt(map, ARG_DELTA_X, 0);
                                    int y = getInt(map, ARG_DELTA_Y, 0);
                                    int z = getInt(map, ARG_DELTA_Z, 0);
                                    AxisAlignedBB boundingBox = getAABB(entity.getLocation(), x, y, z);

                                    if(!boundingBox.c(((CraftEntity)entity).getHandle().al())) {
                                        return Collections.emptyList();
                                    }

                                    for(Predicate<Entity> predicate : predicates) {
                                        if(!predicate.apply(entity)) {
                                            return Collections.emptyList();
                                        }
                                    }

                                    return Lists.newArrayList((T)entity);
                                }
                            }
                            // filter results

                        }
                    }
                    // return entities from valid predicates
                }
            }
        } // end matcher.matches()
        return Collections.emptyList();
    }

    protected static <T extends Entity> boolean isEntityTypeValid(CommandSender sender, Map<String, String> map) {
        String s = getArgument(map, ARG_ENTITY_TYPE);
        if(s == null) {
            return true;
        }

        if(EntityType.fromName(s) != null) {
            return true;
        }
        return false;
    }

    protected static Location getLocationFromArguments(Map<String, String> map, CommandSender sender) {
        if(!(sender instanceof Entity)) {
            return new Location(getWorlds(sender,map).get(0),getInt(map, ARG_COORD_X, 0), getInt(map, ARG_COORD_Y, 0), getInt(map, ARG_COORD_Z, 0));
        }
        return ((Entity)sender).getLocation();
    }

    protected static Vector getPositionFromArguments(Map<String, String> map, CommandSender sender) {
        if(!(sender instanceof Entity)) {
            return new Vector(getCoordinate(map, ARG_COORD_X, 0.0D, true), getCoordinate(map, ARG_COORD_Y, 0.0D, false), getCoordinate(map, ARG_COORD_Z, 0.0D, true));
        }
        return ((Entity)sender).getLocation().toVector();
    }

    protected static AxisAlignedBB getAABB(Location pos, int x, int y, int z) {
        boolean negativeX = x < 0;
        boolean negativeY = y < 0;
        boolean negativeZ = z < 0;
        double originX = pos.getX() + (negativeX ? x : 0);
        double originY = pos.getY() + (negativeY ? y : 0);
        double originZ = pos.getZ() + (negativeZ ? z : 0);
        double dx = pos.getX() + (negativeX ? 0 : x) + 1;
        double dy = pos.getY() + (negativeY ? 0 : y) + 1;
        double dz = pos.getZ() + (negativeZ ? 0 : z) + 1;
        return new AxisAlignedBB(originX, originY, originZ, dx, dy, dz);
    }

    protected static List<World> getWorlds(CommandSender sender, Map<String,String> argumentMap) {
        return sender.getServer().getWorlds();
    }

    protected static boolean hasWorldArgument(Map<String,String> map) {
        for(String s : WORLD_BINDING_ARGS) {
            if(map.containsKey(s)) {
                return true;
            }
        }
        return false;
    }

    protected static double getCoordinate(Map<String, String> arguments, String key, double defaultValue, boolean offset) {
        return arguments.containsKey(key) ? (double) HuntedUtil.getInt(arguments.get(key), HuntedUtil.floor(defaultValue)) + (offset ? 0.5D : 0.0D) : defaultValue;
    }

    protected static int getInt(Map<String, String> map, String key, int defaultValue) {
        return map.containsKey(key) ? HuntedUtil.getInt(map.get(key), defaultValue) : defaultValue;
    }

    @Nullable
    protected static String getArgument(Map<String, String> map, String key) {
        return map.get(key);
    }

    protected static boolean matchesMultiplePlayers(String token) {
        Matcher matcher = TOKEN_PATTERN.matcher(token);
        if(!matcher.matches()) {
            return false;
        }
        Map<String, String> map = getArgumentMap(matcher.group(2));
        String s = matcher.group(1);
        int i = !"a".equals(s) && !"e".equals(s) ? 1 : 0;
        return getInt(map, ARG_COUNT, i) != 1;
    }

    protected static Map<String, Integer> getScoreMap(Map<String, String> map) {
        Map<String, Integer> scores = Maps.newHashMap();

        for(String s : map.keySet()) {
            if(s.startsWith(SCORE_PREFIX) && s.length() > SCORE_PREFIX.length()) {
                scores.put(s.substring(SCORE_PREFIX.length()), HuntedUtil.getInt(map.get(s), 1));
            }
        }
        return scores;
    }


    public static boolean isSelector(String token) {
        return TOKEN_PATTERN.matcher(token).matches();
    }

    protected static Map<String, String> getArgumentMap(@Nullable String token) {
        Map<String, String> map = Maps.newHashMap();
        if(token == null) {
            return null;
        }
        for(String s : COMMA_SPLITTER.split(token)) {
            Iterator<String> iterator = EQUAL_SPLITTER.split(token).iterator();
            String next = iterator.next();

            if(!IS_VALID_ARGUMENT.apply(next)) {
                // throw exception?
            }
            map.put(next, iterator.hasNext() ? (String)iterator.next() : "");
        }
        return map;
    }


    private static <T extends Entity> List<T> filterResults(Map<String, String> args, Class<? extends T> entityClass, List<Predicate<Entity>> inputList, String type, World world, Location location) {
        List<T> list = Lists.newArrayList();
        String sType = getArgument(args, ARG_ENTITY_TYPE);
        sType = sType != null && sType.startsWith("!") ? sType.substring(1) : sType;

        boolean allEntityFlag = !type.equals("e");
        boolean randomFlag = type.equals("r") && sType != null;
        int dx = getInt(args, ARG_DELTA_X, 0);
        int dy = getInt(args, ARG_DELTA_Y, 0);
        int dz = getInt(args, ARG_DELTA_Z, 0);
        int rangeMax = getInt(args, ARG_RANGE_MAX, -1);
        Predicate<Entity> inputPredicate = Predicates.and(inputList);
        Predicate<Entity> alivePredicate = Predicates.and(new Predicate<Entity>() {
            @Override
            public boolean apply(@Nullable Entity entity) {
                return entity != null && !entity.isDead();
            }
        }, inputPredicate);

        if(!args.containsKey(ARG_DELTA_X) && !args.containsKey(ARG_DELTA_Y) && !args.containsKey(ARG_DELTA_Z)) {
            if(rangeMax >= 0) {
                AxisAlignedBB boundingBox = new AxisAlignedBB((double)location.getX() -1, (double)location.getY() - 1, (double)location.getZ() -1,
                                                                     (double)location.getX() + dx + 1, (double)location.getY() + dy + 1, (double)location.getZ() + dz + 1);
                if(allEntityFlag && !randomFlag) {
                    //list.addAll( ((CraftWorld)world).getHandle().b(entityClass, alivePredicate)); // getPlayers
                } else {
                    //list.addAll( ((CraftWorld)world).getHandle().a(entityClass, boundingBox, alivePredicate)); // getEntitiesWithinBoundingBox
                }
            }
            else if(type.equals("a")) {
                //list.addAll(((CraftWorld)world).getHandle().b(entityClass, inputPredicate)); // getPlayers
            }
            else if(!type.equals("p") && (!type.equals("r") || allEntityFlag)) {
                //list.addAll(((CraftWorld)world).getHandle().a(entityClass, inputPredicate)); // getEntities
            }
            else {
                //list.addAll(((CraftWorld)world).getHandle().b(entityClass, alivePredicate)); // getPlayers
            }
        } else {
            final AxisAlignedBB boundingBox = getAABB(location, dx, dy, dz);

            if(allEntityFlag && !randomFlag) {
                Predicate<Entity> intersect = new Predicate<Entity>() {
                    @Override
                    public boolean apply(@Nullable Entity entity) {
                        return entity != null && ((CraftEntity)entity).getHandle().al().c(boundingBox);
                    }
                };
                //list.addAll(((CraftWorld)world).getHandle().b(entityClass, Predicates.and(alivePredicate, intersect))); // getPlayers
            } else {
                //list.addAll(((CraftWorld)world).getHandle().a(entityClass, boundingBox, alivePredicate)); // getEntitiesWithinBoundingBox
            }
        }
        return list;
    }
}
