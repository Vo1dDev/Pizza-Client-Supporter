package qolskyblockmod.pizzaclient.util.collections.fastutil.sets;

import java.util.Arrays;
import qolskyblockmod.pizzaclient.util.collections.fastutil.HashUtil;

public class ObjectOpenHashSet<K> {
   protected K[] key;
   protected int mask;
   protected int n;
   protected int maxFill;
   protected final int minN;
   protected int size;
   protected final float f;

   public ObjectOpenHashSet(int expected, float f) {
      this.f = f;
      this.minN = this.n = HashUtil.arraySize(expected, f);
      this.mask = this.n - 1;
      this.maxFill = HashUtil.maxFill(this.n, f);
      this.key = (Object[])(new Object[this.n + 1]);
   }

   public ObjectOpenHashSet(int expected) {
      this(expected, 0.7F);
   }

   public ObjectOpenHashSet(float expected) {
      this(16, expected);
   }

   public ObjectOpenHashSet() {
      this(16, 0.7F);
   }

   public void add(K k) {
      K[] key = this.key;
      int pos;
      Object curr;
      if ((curr = key[pos = HashUtil.mix(k.hashCode()) & this.mask]) != null) {
         if (curr.equals(k)) {
            return;
         }

         while((curr = key[pos = pos + 1 & this.mask]) != null) {
            if (curr.equals(k)) {
               return;
            }
         }
      }

      key[pos] = k;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashUtil.arraySize(this.size + 1, this.f));
      }

   }

   public boolean contains(Object k) {
      K[] key = this.key;
      Object curr;
      int pos;
      if ((curr = key[pos = HashUtil.mix(k.hashCode()) & this.mask]) == null) {
         return false;
      } else if (k.equals(curr)) {
         return true;
      } else {
         while((curr = key[pos = pos + 1 & this.mask]) != null) {
            if (k.equals(curr)) {
               return true;
            }
         }

         return false;
      }
   }

   public void remove(Object k) {
      K[] key = this.key;
      Object curr;
      int pos;
      if ((curr = key[pos = HashUtil.mix(k.hashCode()) & this.mask]) != null) {
         if (k.equals(curr)) {
            this.removeEntry(pos);
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != null) {
               if (k.equals(curr)) {
                  this.removeEntry(pos);
               }
            }

         }
      }
   }

   private void removeEntry(int pos) {
      --this.size;
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

   }

   protected final void shiftKeys(int pos) {
      Object[] key = this.key;

      while(true) {
         int last = pos;
         pos = pos + 1 & this.mask;

         Object curr;
         while(true) {
            if ((curr = key[pos]) == null) {
               key[last] = null;
               return;
            }

            int slot = HashUtil.mix(curr.hashCode()) & this.mask;
            if (last <= pos) {
               if (last >= slot || slot > pos) {
                  break;
               }
            } else if (last >= slot && slot > pos) {
               break;
            }

            pos = pos + 1 & this.mask;
         }

         key[last] = curr;
      }
   }

   protected void rehash(int newN) {
      K[] key = this.key;
      int mask = newN - 1;
      K[] newKey = (Object[])(new Object[newN + 1]);
      int i = this.n;

      int pos;
      for(int var7 = this.size; var7-- != 0; newKey[pos] = key[i]) {
         do {
            --i;
         } while(key[i] == null);

         if (newKey[pos = HashUtil.mix(key[i].hashCode()) & mask] != null) {
            while(newKey[pos = pos + 1 & mask] != null) {
            }
         }
      }

      this.n = newN;
      this.mask = mask;
      this.maxFill = HashUtil.maxFill(this.n, this.f);
      this.key = newKey;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public int size() {
      return this.size;
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         Arrays.fill(this.key, (Object)null);
      }
   }
}
