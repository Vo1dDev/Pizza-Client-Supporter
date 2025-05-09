package qolskyblockmod.pizzaclient.features.slayers;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotater;

public class AshfangAura {
   public static boolean onRenderOrb(Entity entityToInteract) {
      Vec3 ashfang = null;
      Iterator var2 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var2.hasNext()) {
         Entity entity = (Entity)var2.next();
         if (entity instanceof EntityArmorStand && entity.func_145818_k_() && StringUtils.func_76338_a(entity.func_95999_t()).contains("Lv200] Ashfang")) {
            ashfang = new Vec3(entity.field_70165_t, entity.field_70163_u - 0.5D, entity.field_70161_v);
            break;
         }
      }

      if (ashfang != null) {
         FakeRotater.clickEntity(Rotation.getRotation(entityToInteract.func_174824_e(1.0F), ashfang), entityToInteract);
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Clicking Blazing Soul!"));
         return true;
      } else {
         return false;
      }
   }
}
