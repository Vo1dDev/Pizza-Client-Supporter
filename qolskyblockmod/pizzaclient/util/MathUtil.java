package qolskyblockmod.pizzaclient.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;

public class MathUtil {
   public static final float PI = 3.1415927F;
   public static final float PI_180 = 0.017453292F;
   public static final double ROTATION_NUMBER = 57.29577951308232D;
   public static final float PLAYER_REACH_SQ = 20.25F;

   public static int floor(float value) {
      int i = (int)value;
      return value < (float)i ? i - 1 : i;
   }

   public static int floor(double value) {
      int i = (int)value;
      return value < (double)i ? i - 1 : i;
   }

   public static long floor_long(double value) {
      long i = (long)value;
      return value < (double)i ? i - 1L : i;
   }

   public static int round(float value) {
      int floor = floor(value);
      return (double)value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int round(double value) {
      int floor = floor(value);
      return value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundUp(float value) {
      int floor = floor(value);
      return (double)value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundUp(double value) {
      int floor = floor(value);
      return value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundDown(float value) {
      int floor = floor(value);
      return (double)value > (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundDown(double value) {
      int floor = floor(value);
      return value > (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int ceil(float value) {
      int i = (int)value;
      return value > (float)i ? i + 1 : i;
   }

   public static int ceil(double value) {
      int i = (int)value;
      return value > (double)i ? i + 1 : i;
   }

   public static int abs(int value) {
      return value >= 0 ? value : -value;
   }

   public static float abs(float value) {
      return value >= 0.0F ? value : -value;
   }

   public static long abs(long value) {
      return value >= 0L ? value : -value;
   }

   public static double abs(double value) {
      return value >= 0.0D ? value : -value;
   }

   public static double randomDouble() {
      return Utils.random.nextBoolean() ? Utils.random.nextDouble() : -Utils.random.nextDouble();
   }

   public static float randomFloat() {
      return Utils.random.nextBoolean() ? Utils.random.nextFloat() : -Utils.random.nextFloat();
   }

   public static float positiveFloat() {
      return Utils.random.nextFloat();
   }

   public static float negativeFloat() {
      return -Utils.random.nextFloat();
   }

   public static float randomFloat(float multiplier) {
      return Utils.random.nextBoolean() ? Utils.random.nextFloat() * multiplier : -Utils.random.nextFloat() * multiplier;
   }

   public static float positiveFloat(float multiplier) {
      return Utils.random.nextFloat() * multiplier;
   }

   public static float negativeFloat(float multiplier) {
      return -Utils.random.nextFloat() * multiplier;
   }

   public static int randomNumber(int bound) {
      return Utils.random.nextBoolean() ? Utils.random.nextInt(bound) : -Utils.random.nextInt(bound);
   }

   public static boolean inBetween(float value, float i, float i2) {
      return max(i, i2) >= value && min(i, i2) <= value;
   }

   public static boolean inBetween(double value, float i, float i2) {
      return (double)max(i, i2) >= value && (double)min(i, i2) <= value;
   }

   public static boolean inBetween(double value, double i, double i2) {
      return max(i, i2) >= value && min(i, i2) <= value;
   }

   public static Vec3 randomVec() {
      return new Vec3(Utils.random.nextDouble(), Utils.random.nextDouble(), Utils.random.nextDouble());
   }

   public static double min(double a, double b) {
      return a < b ? a : b;
   }

   public static float min(float a, float b) {
      return a < b ? a : b;
   }

   public static long min(long a, long b) {
      return a < b ? a : b;
   }

   public static int min(int a, int b) {
      return a < b ? a : b;
   }

   public static double max(double a, double b) {
      return a >= b ? a : b;
   }

   public static float max(float a, float b) {
      return a >= b ? a : b;
   }

   public static int max(int a, int b) {
      return a >= b ? a : b;
   }

   public static double max(Vec3 vec) {
      return max(max(vec.field_72450_a, vec.field_72448_b), vec.field_72449_c);
   }

   public static double min(Vec3 vec) {
      return min(min(vec.field_72450_a, vec.field_72448_b), vec.field_72449_c);
   }

   public static int clamp(int num, int min, int max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static float clamp(float num, float min, float max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static double clamp(double num, double min, double max) {
      return num < min ? min : (num > max ? max : num);
   }

   public static Vec3 randomAABBPoint(AxisAlignedBB aabb) {
      return new Vec3(getRandomInBetween(aabb.field_72340_a, aabb.field_72336_d), getRandomInBetween(aabb.field_72338_b, aabb.field_72337_e), getRandomInBetween(aabb.field_72339_c, aabb.field_72334_f));
   }

   public static Vec3 randomAABBPoint(BlockPos pos) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      return new Vec3(getRandomInBetween(aabb.field_72340_a, aabb.field_72336_d), getRandomInBetween(aabb.field_72338_b, aabb.field_72337_e), getRandomInBetween(aabb.field_72339_c, aabb.field_72334_f));
   }

   public static Vec3 randomAABBPoint(Vec3 pos) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      return new Vec3(getRandomInBetween(aabb.field_72340_a, aabb.field_72336_d), getRandomInBetween(aabb.field_72338_b, aabb.field_72337_e), getRandomInBetween(aabb.field_72339_c, aabb.field_72334_f));
   }

   public static float getRandomInBetween(float i, float i2) {
      float min = min(i, i2);
      return Utils.random.nextFloat() * (max(i, i2) - min) + min;
   }

   public static double getRandomInBetween(double i, double i2) {
      double min = min(i, i2);
      return Utils.random.nextDouble() * (max(i, i2) - min) + min;
   }

   public static float randomNegative(float numb) {
      return Utils.random.nextBoolean() ? numb : -numb;
   }

   public static double randomNegative(double numb) {
      return Utils.random.nextBoolean() ? numb : -numb;
   }

   public static float clampPitch(float pitch) {
      return pitch < -90.0F ? -90.0F : (pitch > 90.0F ? 90.0F : pitch);
   }

   public static float clampPitch(double pitch) {
      return pitch < -90.0D ? -90.0F : (float)(pitch > 90.0D ? 90.0D : pitch);
   }

   public static float getDecimals(float num) {
      return num - (float)floor(num);
   }

   public static double getDecimals(double num) {
      return num - (double)floor(num);
   }

   public static int getPositveOrZero(int num) {
      return num > 0 ? num : 0;
   }

   public static long getPositveOrZero(long num) {
      return num > 0L ? num : 0L;
   }

   public static float getPositveOrZero(float num) {
      return num > 0.0F ? num : 0.0F;
   }

   public static double getPositveOrZero(double num) {
      return num > 0.0D ? num : 0.0D;
   }

   public static int compare(int x, int y) {
      return x < y ? -1 : 1;
   }

   public static long compare(long x, long y) {
      return x < y ? -1L : 1L;
   }

   public static float compare(float x, float y) {
      return x < y ? -1.0F : 1.0F;
   }

   public static double compare(double x, double y) {
      return x < y ? -1.0D : 1.0D;
   }

   public static float calculateGaussianValue(float x, float radius) {
      return (float)(1.0D / Math.sqrt(6.2831854820251465D * (double)(radius * radius)) * Math.exp((double)(-(x * x)) / (2.0D * (double)(radius * radius))));
   }

   public static double interpolate(double oldValue, double newValue, double interpolationValue) {
      return oldValue + (newValue - oldValue) * interpolationValue;
   }

   public static float interpolate(float oldValue, float newValue, double interpolationValue) {
      return (float)((double)oldValue + (double)(newValue - oldValue) * interpolationValue);
   }

   public static int interpolate(int oldValue, int newValue, double interpolationValue) {
      return (int)((double)oldValue + (double)(newValue - oldValue) * interpolationValue);
   }

   public static double interpolate(double oldValue, double newValue) {
      return oldValue + (newValue - oldValue) * (double)Utils.getPartialTicks();
   }

   public static float interpolate(float oldValue, float newValue) {
      return oldValue + (newValue - oldValue) * Utils.getPartialTicks();
   }

   public static int interpolate(int oldValue, int newValue) {
      return (int)((float)oldValue + (float)(newValue - oldValue) * Utils.getPartialTicks());
   }

   public static double getEuclideanToPos(BlockPos pos) {
      return abs(PizzaClient.mc.field_71439_g.field_70165_t - (double)pos.func_177958_n()) + abs(PizzaClient.mc.field_71439_g.field_70161_v - (double)pos.func_177952_p());
   }

   public static double getEuclideanToPos(Vec3 pos) {
      return abs(PizzaClient.mc.field_71439_g.field_70165_t - pos.field_72450_a) + abs(PizzaClient.mc.field_71439_g.field_70161_v - pos.field_72449_c);
   }

   public static double getEuclideanToPos(Vec3 pos, Vec3 goal) {
      return abs(pos.field_72450_a - goal.field_72450_a) + abs(pos.field_72449_c - goal.field_72449_c);
   }

   public static double getEuclideanToPos(Entity pos, Entity goal) {
      return abs(pos.field_70165_t - goal.field_70165_t) + abs(pos.field_70161_v - goal.field_70161_v);
   }

   public static float formatFloat(float value) {
      return (float)round(value * 1000.0F) / 1000.0F;
   }

   public static double formatDouble(double value) {
      return (double)round(value * 1000.0D) / 1000.0D;
   }

   public static boolean isEven(int param0) {
      // $FF: Couldn't be decompiled
   }
}
