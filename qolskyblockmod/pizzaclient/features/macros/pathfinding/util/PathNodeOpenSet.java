package qolskyblockmod.pizzaclient.features.macros.pathfinding.util;

import java.util.Arrays;
import qolskyblockmod.pizzaclient.features.macros.pathfinding.pathfinder.PathNode;

public final class PathNodeOpenSet {
   private PathNode[] array;
   public int size;

   public PathNodeOpenSet() {
      this.array = new PathNode[1024];
   }

   public PathNodeOpenSet(int size) {
      this.array = new PathNode[size];
   }

   public final void insert(PathNode value) {
      if (this.size >= this.array.length - 1) {
         this.array = (PathNode[])Arrays.copyOf(this.array, this.array.length << 1);
      }

      ++this.size;
      value.heapPosition = this.size;
      this.array[this.size] = value;
      int index = this.size;
      int parentInd = index >>> 1;

      for(PathNode parentNode = this.array[parentInd]; index > 1 && parentNode.combinedCost > value.combinedCost; parentNode = this.array[parentInd]) {
         this.array[index] = parentNode;
         this.array[parentInd] = value;
         value.heapPosition = parentInd;
         parentNode.heapPosition = index;
         index = parentInd;
         parentInd >>>= 1;
      }

   }

   public final void update(PathNode val) {
      int index = val.heapPosition;
      int parentInd = index >>> 1;

      for(PathNode parentNode = this.array[parentInd]; index > 1 && parentNode.combinedCost > val.combinedCost; parentNode = this.array[parentInd]) {
         this.array[index] = parentNode;
         this.array[parentInd] = val;
         val.heapPosition = parentInd;
         parentNode.heapPosition = index;
         index = parentInd;
         parentInd >>>= 1;
      }

   }

   public final PathNode removeLowest() {
      PathNode result = this.array[1];
      PathNode val = this.array[this.size];
      this.array[1] = val;
      val.heapPosition = 1;
      this.array[this.size] = null;
      --this.size;
      result.heapPosition = -1;
      if (this.size < 2) {
         return result;
      } else {
         int index = 1;
         int smallerChild = 2;
         double cost = val.combinedCost;

         do {
            PathNode smallerChildNode = this.array[smallerChild];
            double smallerChildCost = smallerChildNode.combinedCost;
            if (smallerChild < this.size) {
               PathNode rightChildNode = this.array[smallerChild + 1];
               double rightChildCost = rightChildNode.combinedCost;
               if (smallerChildCost > rightChildCost) {
                  ++smallerChild;
                  smallerChildCost = rightChildCost;
                  smallerChildNode = rightChildNode;
               }
            }

            if (cost <= smallerChildCost) {
               break;
            }

            this.array[index] = smallerChildNode;
            this.array[smallerChild] = val;
            val.heapPosition = smallerChild;
            smallerChildNode.heapPosition = index;
            index = smallerChild;
         } while((smallerChild <<= 1) <= this.size);

         return result;
      }
   }

   public void clear() {
      if (this.size != 0) {
         for(int i = 0; i < this.size; ++i) {
            this.array[i] = null;
         }

         this.size = 0;
      }
   }
}
