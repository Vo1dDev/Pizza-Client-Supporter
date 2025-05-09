package qolskyblockmod.pizzaclient.util.collections.fastutil.maps;

import java.util.Arrays;
import qolskyblockmod.pizzaclient.util.collections.fastutil.HashUtil;

public class IntObjectOpenHashMap<V> {
   protected int mask;
   protected int n;
   protected int maxFill;
   protected final int minN;
   protected int size;
   protected final float f;
   protected int[] key;
   protected V[] value;

   public IntObjectOpenHashMap(int expected, float f) {
      this.f = f;
      this.minN = this.n = HashUtil.arraySize(expected, f);
      this.mask = this.n - 1;
      this.maxFill = HashUtil.maxFill(this.n, f);
      this.key = new int[this.n + 1];
      this.value = (Object[])(new Object[this.n + 1]);
   }

   public IntObjectOpenHashMap(int expected) {
      this(expected, 0.75F);
   }

   public void put(int k, V v) {
      int pos = this.find(k);
      if (pos < 0) {
         this.insert(-pos - 1, k, v);
      } else {
         this.value[pos] = v;
      }
   }

   private void insert(int pos, int k, V v) {
      this.key[pos] = k;
      this.value[pos] = v;
      if (this.size++ >= this.maxFill) {
         this.rehash(HashUtil.arraySize(this.size + 1, this.f));
      }

   }

   private int find(int k) {
      int[] key = this.key;
      int curr;
      int pos;
      if ((curr = key[pos = HashUtil.mix(k) & this.mask]) == 0) {
         return -(pos + 1);
      } else if (k == curr) {
         return pos;
      } else {
         while((curr = key[pos = pos + 1 & this.mask]) != 0) {
            if (k == curr) {
               return pos;
            }
         }

         return -(pos + 1);
      }
   }

   private void rehash(int newN) {
      int[] key = this.key;
      V[] value = this.value;
      int mask = newN - 1;
      int[] newKey = new int[newN + 1];
      V[] newValue = (Object[])(new Object[newN + 1]);
      int i = this.n;

      int pos;
      for(int var9 = this.size; var9-- != 0; newValue[pos] = value[i]) {
         do {
            --i;
         } while(key[i] == 0);

         if (newKey[pos = HashUtil.mix(key[i]) & mask] != 0) {
            while(newKey[pos = pos + 1 & mask] != 0) {
            }
         }

         newKey[pos] = key[i];
      }

      newValue[newN] = value[this.n];
      this.n = newN;
      this.mask = mask;
      this.maxFill = HashUtil.maxFill(this.n, this.f);
      this.key = newKey;
      this.value = newValue;
   }

   public V get(int k) {
      int[] key = this.key;
      int curr;
      int pos;
      if ((curr = key[pos = HashUtil.mix(k) & this.mask]) == 0) {
         return null;
      } else if (k == curr) {
         return this.value[pos];
      } else {
         while((curr = key[pos = pos + 1 & this.mask]) != 0) {
            if (k == curr) {
               return this.value[pos];
            }
         }

         return null;
      }
   }

   public int getIndex(int k) {
      int[] key = this.key;
      int curr;
      int pos;
      if ((curr = key[pos = HashUtil.mix(k) & this.mask]) == 0) {
         return -1;
      } else if (k == curr) {
         return pos;
      } else {
         while((curr = key[pos = pos + 1 & this.mask]) != 0) {
            if (k == curr) {
               return pos;
            }
         }

         return -1;
      }
   }

   public V getFromIndex(int index) {
      return this.value[index];
   }

   public boolean containsKey(int k) {
      int[] key = this.key;
      int curr;
      int pos;
      if ((curr = key[pos = HashUtil.mix(k) & this.mask]) == 0) {
         return false;
      } else if (k == curr) {
         return true;
      } else {
         while((curr = key[pos = pos + 1 & this.mask]) != 0) {
            if (k == curr) {
               return true;
            }
         }

         return false;
      }
   }

   private void removeEntry(int pos) {
      this.value[pos] = null;
      --this.size;
      this.shiftKeys(pos);
      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
         this.rehash(this.n / 2);
      }

   }

   public void remove(int k) {
      int[] key = this.key;
      int curr;
      int pos;
      if ((curr = key[pos = HashUtil.mix(k) & this.mask]) != 0) {
         if (k == curr) {
            this.removeEntry(pos);
         } else {
            while((curr = key[pos = pos + 1 & this.mask]) != 0) {
               if (k == curr) {
                  this.removeEntry(pos);
                  return;
               }
            }

         }
      }
   }

   protected final void shiftKeys(int pos) {
      int[] key = this.key;

      while(true) {
         int last = pos;
         pos = pos + 1 & this.mask;

         int curr;
         while(true) {
            if ((curr = key[pos]) == 0) {
               key[last] = 0;
               this.value[last] = null;
               return;
            }

            int slot = HashUtil.mix(curr) & this.mask;
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
         this.value[last] = this.value[pos];
      }
   }

   public void clear() {
      if (this.size != 0) {
         this.size = 0;
         Arrays.fill(this.key, 0);
         Arrays.fill(this.value, (Object)null);
      }
   }

   public int size() {
      return this.size;
   }
}
