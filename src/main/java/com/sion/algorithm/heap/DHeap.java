package com.sion.algorithm.heap;

import java.util.function.Function;

/**
 * D叉堆
 */
public class DHeap<T extends Comparable<T>> {
  private final int d;
  private int size;
  private final Object[] heap;

  @SuppressWarnings("unchecked")
  DHeap(int d, int capacity) {
    if (d < 2) {
      throw new IllegalArgumentException("d must be greater than 1");
    }
    this.d = d;
    this.size = 0;
    this.heap = new Object[capacity + 1];
  }

  void maxHeapify(int i) {

  }

  @SuppressWarnings("unchecked")
  public void printHeap(Function<T, String> toStringFunc) {
    System.out.println("Heap:");
    int k = 1;
    int dd = d;
    for (int i = 0; i < size; ) {
      System.out.print(toStringFunc == null ? heap[i].toString() : toStringFunc.apply((T) heap[i]));
      System.out.print(" ");
      if (++i == k) {
        System.out.println();
        k = k + dd;
        dd *= d;
      }
    }
  }

  public void printHeap() {
    printHeap(null);
  }

//  public static void main(String[] args) {
//    int size = 63;
//    DHeap<Integer> heap = new DHeap<>(2, size);
//    heap.size = size;
//    for (int i = 0; i < size; i++) {
//      heap.heap[i] = 0;
//    }
//    heap.printHeap();
//  }
}
