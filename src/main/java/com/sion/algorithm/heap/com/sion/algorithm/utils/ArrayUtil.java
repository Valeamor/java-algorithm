package com.sion.algorithm.heap.com.sion.algorithm.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayUtil {
  public static void shuffle(Object[] ar) {
    shuffle(ar, ar.length);
  }

  public static void shuffle(Object[] ar, int length) {
    Random rnd = ThreadLocalRandom.current();
    for (int i = length - 2; i > 0; i--) {
      int index = rnd.nextInt(i + 1);
      swap(ar, index, i);
    }
  }

  public static void swap(Object[] ar, int i, int j) {
    Object temp = ar[i];
    ar[i] = ar[j];
    ar[j] = temp;
  }

  public void reverse(Object[] ar) {
    reverse(ar, 0, ar.length);
  }

  public void reverse(Object[] ar, int offset, int length) {
    int s = length - 1;
    for (int i = 0, l = length / 2; i < l; ++i) {
      swap(ar, i + offset, s - i + offset);
    }
  }
}