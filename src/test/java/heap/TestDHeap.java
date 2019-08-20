package heap;

import com.sion.algorithm.heap.DHeap;

public class TestDHeap {
  public static void main(String[] args) {
    int size = 127;
    DHeap<Integer> heap = new DHeap<>(2, 1);
    for (int i = 0; i < size; i++) {
      heap.add(i);
    }
    System.out.println("shuffle <<<<<<<<<");
    heap.shuffle();
    heap.printHeap();

    System.out.println("sort <<<<<<<<<");
    heap.sort();
    heap.printHeap();

    System.out.println("add 1000 <<<<<<<<<");
    heap.add(1000);
    heap.printHeap();

    System.out.println("sort <<<<<<<<<");
    heap.sort();
    heap.printHeap();

    heap.setD(4);
    System.out.println("setD(4) <<<<<<<<<");
    heap.printHeap();
  }
}
