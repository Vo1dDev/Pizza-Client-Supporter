package qolskyblockmod.pizzaclient.features.macros.pathfinding.util;

import java.util.Comparator;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.util.MathUtil;

public class PathComparator implements Comparator<PathBase> {
   public int compare(PathBase o1, PathBase o2) {
      return (int)MathUtil.compare(o1.currentPos.distanceToSq(o1.goalPos), o2.currentPos.distanceToSq(o2.goalPos));
   }
}
