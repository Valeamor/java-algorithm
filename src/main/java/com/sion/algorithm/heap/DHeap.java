package com.sion.algorithm.heap;

import com.sion.algorithm.heap.com.sion.algorithm.utils.ArrayUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * D叉大顶堆
 */
@SuppressWarnings("unchecked")
public class DHeap<T extends Comparable<T>> implements Collection<T> {
  private int d;
  private int size;
  private Object[] heap;
  private boolean isMaxHeap;

  private static final int MIN_CAPACITY = 16;

  public DHeap(int d, int capacity) {
    if (capacity < MIN_CAPACITY) {
      capacity = MIN_CAPACITY;
    }
    this.size = 0;
    this.heap = new Object[capacity];
    setD(d);
  }

  public DHeap(int d, T[] heap) {
    this.size = heap.length;
    if (heap.length < MIN_CAPACITY) {
      heap = Arrays.copyOf(heap, MIN_CAPACITY);
    }
    this.heap = heap;
    setD(d);
  }

  public void setD(int d) {
    if (d < 2) {
      throw new IllegalArgumentException("d must be greater than 1");
    }
    if (this.d == d) {
      return;
    }
    this.d = d;
    this.isMaxHeap = false;
    buildMaxHeap();
  }

  public T delete(int i) {
    if (size == 0 || i >= size || i < 0) {
      return null;
    }
    T v = (T) heap[i];
    heap[i] = heap[--size];
    maxHeapify(i);
    return v;
  }

  public void shuffle() {
    ArrayUtil.shuffle(heap, size);
    isMaxHeap = false;
  }

  private int parent(int i) {
    return (i - 1) / d;
  }

  private int child(int i, int k) {
    return d * i + k;
  }

  public void sort() {
    buildMaxHeap();
    int sizeCopy = size;
    while (--size > 0) {
      ArrayUtil.swap(heap, 0, size);
      maxHeapify(0);
    }
    size = sizeCopy;
    isMaxHeap = false;
  }

  private void buildMaxHeap() {
    if (isMaxHeap) {
      return;
    }
    for (int i = (size - 1) / d; i >= 0; --i) {
      maxHeapify(i);
    }
    isMaxHeap = true;
  }

  // 将大的元素向上移动
  private void heapifyUp(int i) {
    T t = (T) heap[i];
    while (i > 0 && t.compareTo((T) heap[parent(i)]) > 0) {
      heap[i] = heap[parent(i)];
      i = parent(i);
    }
    heap[i] = t;
  }

  // 将小的元素向下移动
  private void maxHeapify(int i) {
    while (true) {
      T max = (T) heap[i];
      int maxI = i;
      for (int k = child(i, 1), l = Math.min(child(i, d), size - 1); k <= l; ++k) {
        if (max.compareTo((T) heap[k]) < 0) {
          max = (T) heap[k];
          maxI = k;
        }
      }
      if (maxI == i) {
        return;
      } else {
        heap[maxI] = heap[i];
        heap[i] = max;
        i = maxI;
      }
    }
  }

  public void printHeap(Function<T, String> toStringFunc) {
    System.out.println("Print Heap:");
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
    System.out.println();
  }

  public void printHeap() {
    printHeap(null);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean contains(Object o) {
    for (int i = 0; i < size; i++) {
      if (heap[i].equals(o)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return new Itr();
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(heap, size);
  }

  @Override
  public <E> E[] toArray(E[] a) {
    if (a.length < size) {
      return (E[]) Arrays.copyOf(heap, size, a.getClass());
    }
    System.arraycopy(heap, 0, a, 0, size);
    if (a.length > size) {
      a[size] = null;
    }
    return a;
  }

  @Override
  public boolean add(T e) {
    if (size == heap.length) {
      heap = Arrays.copyOf(heap, size + (size >> 1));
    }
    buildMaxHeap();
    heap[size++] = e;
    heapifyUp(size - 1);
    return true;
  }

  @Override
  public boolean remove(Object o) {
    for (int i = 0; i < size; i++) {
      if (heap[i].equals(o)) {
        delete(i);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    for (Object e : c) {
      if (!contains(e)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    boolean modified = false;
    for (T e : c) {
      if (add(e)) {
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    Objects.requireNonNull(c);
    boolean modified = false;
    Iterator<?> it = iterator();
    while (it.hasNext()) {
      if (c.contains(it.next())) {
        it.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    Objects.requireNonNull(c);
    boolean modified = false;
    Iterator<T> it = iterator();
    while (it.hasNext()) {
      if (!c.contains(it.next())) {
        it.remove();
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public void clear() {
    size = 0;
  }

  private class Itr implements Iterator<T> {
    int cursor;       // index of next element to return
    int lastRet = -1; // index of last element returned; -1 if no such

    // prevent creating a synthetic constructor
    Itr() {
    }

    public boolean hasNext() {
      return cursor != size;
    }

    public T next() {
      int i = cursor;
      if (i >= size) {
        throw new NoSuchElementException();
      }
      Object[] elementData = DHeap.this.heap;
      if (i >= elementData.length) {
        throw new ConcurrentModificationException();
      }
      cursor = i + 1;
      return (T) elementData[lastRet = i];
    }

    public void remove() {
      if (lastRet < 0) {
        throw new IllegalStateException();
      }
      try {
        DHeap.this.remove(lastRet);
        cursor = lastRet;
        lastRet = -1;
      } catch (IndexOutOfBoundsException ex) {
        throw new ConcurrentModificationException();
      }
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
      Objects.requireNonNull(action);
      final int size = DHeap.this.size;
      int i = cursor;
      if (i < size) {
        final Object[] es = heap;
        if (i >= es.length) {
          throw new ConcurrentModificationException();
        }
        for (; i < size; i++) {
          action.accept((T) es[i]);
        }
        // update once at end to reduce heap write traffic
        cursor = i;
        lastRet = i - 1;
      }
    }
  }
}
