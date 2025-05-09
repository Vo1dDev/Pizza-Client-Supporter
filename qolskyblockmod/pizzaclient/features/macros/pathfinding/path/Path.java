package qolskyblockmod.pizzaclient.features.macros.pathfinding.path;

import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.vectors.IntVec3;

public final class Path extends PathBase {
   public double lastUnloadedChunkDistance = 9.99999999E8D;
   public IntVec3 unloaded;

   public Path(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public Path(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
   }

   public Path(BetterBlockPos to) {
      super(to);
   }

   public Path(Vec3 to) {
      super(to);
   }

   public void update() {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.checked.clear();
      this.moves = new ArrayList(Collections.singletonList(new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)));
      this.unloaded = null;
   }

   public void update(BetterBlockPos goalPos) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = goalPos;
      this.checked.clear();
      this.moves = new ArrayList(Collections.singletonList(new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)));
      this.unloaded = null;
   }

   public void moveUntilLoaded() {
      this.onBeginMove();

      while(!this.unloaded.isBlockLoaded()) {
         this.moveTo();
      }

      this.onEndMove();
      Movement.disableMovement();
      Utils.sleep(50);
      this.update();
   }

   public void moveTo() {
      this.useDefaultMovement();
   }

   public void move() {
      this.moveTo();
   }
}
