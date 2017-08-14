package br.com.luque.model2gether;

/**
 * ****************************************************************************
 * Compilation: javac DoublyLinkedList.java Execution: java DoublyLinkedList
 * Dependencies: StdOut.java
 *
 * A list implemented with a doubly linked list. The elements are stored (and
 * iterated over) in the same order that they are inserted.
 *
 * % java DoublyLinkedList 10 10 random integers between 0 and 99 24 65 2 39 86
 * 24 50 47 13 4
 *
 * add 1 to each element via next() and set() 25 66 3 40 87 25 51 48 14 5
 *
 * multiply each element by 3 via previous() and set() 75 198 9 120 261 75 153
 * 144 42 15
 *
 * remove elements that are a multiple of 4 via next() and remove() 75 198 9 261
 * 75 153 42 15
 *
 * remove elements that are even via previous() and remove() 75 9 261 75 153 15
 *
 * @author http://algs4.cs.princeton.edu/13stacks/DoublyLinkedList.java.html
 * Modified by Leandro Luque
 * ****************************************************************************
 */
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<Item> implements Iterable<Item> {

    // Leandro Luque: name changed to size.
    private int size;        // number of elements on list
    private Node pre;        // sentinel before first item
    private Node post;       // sentinel after last item

    public DoublyLinkedList() {
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
    }

    // linked list node helper data type
    public class Node {

        private Item item;
        private Node next;
        private Node prev;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // add the item to the list
    public void add(Item item) {
        Node last = post.prev;
        Node x = new Node();
        x.item = item;
        x.next = post;
        x.prev = last;
        post.prev = x;
        last.next = x;
        size++;
    }

    public ListIterator<Item> iterator() {
        return new DoublyLinkedListIterator();
    }

    // assumes no calls to DoublyLinkedList.add() during iteration
    private class DoublyLinkedListIterator implements ListIterator<Item> {

        // Leandro Luque: current default value changed to null.
        private Node current = null;  // the node that is returned by next()
        private Node lastAccessed = null;      // the last node to be returned by prev() or next()
        // reset to null upon intervening remove() or add()
        private int index = 0;

        public boolean hasNext() {
            return index < size;
        }

        public boolean hasPrevious() {
            return index > 0;
        }

        public int previousIndex() {
            return index - 1;
        }

        public int nextIndex() {
            return index;
        }

        public Item current() {
            if(current == null) {
                return null;
            }
            return current.item;
        }

        public Item next() {
            if(current == null) {
                current = pre.next;
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastAccessed = current;
            Item item = current.item;
            current = current.next;
            index++;
            return item;
        }

        public Item previous() {
            if(current == null) {
                return null;
            }
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            current = current.prev;
            index--;
            lastAccessed = current;
            return current.item;
        }

        // replace the item of the element that was last accessed by next() or previous()
        // condition: no calls to remove() or add() after last call to next() or previous()
        public void set(Item item) {
            if (lastAccessed == null) {
                throw new IllegalStateException();
            }
            lastAccessed.item = item;
        }

        // remove the element that was last accessed by next() or previous()
        // condition: no calls to remove() or add() after last call to next() or previous()
        public void remove() {
            if(current == null) {
                throw new IllegalStateException();
            }
            if (lastAccessed == null) {
                throw new IllegalStateException();
            }
            Node x = lastAccessed.prev;
            Node y = lastAccessed.next;
            x.next = y;
            y.prev = x;
            size--;
            if (current == lastAccessed) {
                current = y;
            } else {
                index--;
            }
            lastAccessed = null;
        }

        // add element to list 
        public void add(Item item) {
            if(current == null) {
                current = pre.next;
            }
            Node x = current.prev;
            Node y = new Node();
            Node z = current;
            y.item = item;
            x.next = y;
            y.next = z;
            z.prev = y;
            y.prev = x;
            size++;
            index++;
            lastAccessed = null;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }

}
