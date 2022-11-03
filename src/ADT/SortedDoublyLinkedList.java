package ADT;

import java.util.Iterator;

/**
 *
 * @author Wong Kai Hao
 */
public class SortedDoublyLinkedList<T extends Comparable<T>> implements SortedList<T> {

    private Node firstNode;
    private int size;

    public SortedDoublyLinkedList() {
        firstNode = null;
        size = 0;
    }

    @Override
    public void add(T newEntry) {
        Node newNode = new Node(newEntry);

        Node nodeBefore = null;
        Node currentNode = firstNode;
        while (currentNode != null && newEntry.compareTo(currentNode.data) > 0) {
            nodeBefore = currentNode;
            currentNode = currentNode.next;
        }
        if (!isEmpty()) {
            if (nodeBefore == null) { // CASE 1: add at beginning
                newNode.next = firstNode;
                firstNode = newNode;
                firstNode.next.previous = firstNode;
            } else {	// CASE 2: add in the middle or at the end, i.e. after nodeBefore
                newNode.next = currentNode;
                nodeBefore.next = newNode;
                if (currentNode == null) {
                    newNode.previous = nodeBefore;
                } else {
                    currentNode.previous = newNode;
                    newNode.previous = nodeBefore;
                }
            }
        }else{
            firstNode = newNode;
        }
        size++;
    }

    @Override
    public boolean remove(T anEntry) {//by using the combination of sorted and doubly implementation,we can enjoy the advantages brought from both implementation
        Node currentNode = firstNode;
        //Node nodeBefore = null; //saving memory allocation for variable and it can run faster
        if (currentNode == null) {//if the list is empty
            return false;
        } else {
            while (currentNode != null && anEntry.compareTo(currentNode.data) > 0) {//tranversal of the list until it is ending or greater than or equal to the anEntry
                currentNode.previous = currentNode;
                currentNode = currentNode.next;
            }//to find the location of the anEntry
            if (anEntry.equals(currentNode.data)) {
                if (currentNode.previous == null) { // CASE 1: delete element at beginning
                    firstNode = currentNode.next;
                    currentNode.next.previous = null;
                } else {	// CASE 2: delete element in the middle or at the end, i.e. after nodeBefore
                    if (currentNode.next == null) {
                        currentNode.previous.next = null;
                    } else {
                        currentNode.previous.next = currentNode.next;
                        currentNode.next.previous = currentNode.previous;
                    }
                }
                size--;
                return true;
            } else {// anEntry does not exist in the list
                return false;
            }
        }
    }

    @Override
    public void clear() {
        firstNode = null;
        size = 0;
    }

    @Override
    public boolean contains(T anEntry) {
        return search(firstNode, anEntry);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0)&&(firstNode==null);
    }

    @Override
    public String toString() {
        return toString(firstNode);
    }

    @Override//( effiency : O(n))
    public T getEntry(int givenPosition) {
        Node currentNode = firstNode;
        int count = 0;
        if (givenPosition >= 1 && givenPosition <= size) {
            while (currentNode != null && count != (givenPosition - 1)) {
                count++;
                currentNode = currentNode.next;
            }
        }else{
            currentNode=null;
        }
        return currentNode.data;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public Iterator<T> getIterator() {
        return new SortedDoublyLinkedListIterator();
    }

    private boolean search(Node currentNode, T desiredItem) {
        boolean found;
        if (currentNode == null) {
            found = false;
        } else if (desiredItem.equals(currentNode.data)) {
            found = true;
        } else {
            found = search(currentNode.next, desiredItem);
        }
        return found;
    }

    private String toString(Node currentNode) {
        String outputStr = "";
        if (currentNode.next == null) {
            return currentNode.data + "\n";
        }
        outputStr += currentNode.data + "\n";
        return outputStr + toString(currentNode.next);
    }

    private class SortedDoublyLinkedListIterator implements Iterator<T> {

        private Node currentNode;

        public SortedDoublyLinkedListIterator() {
            currentNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T returnData = currentNode.data;
                currentNode = currentNode.next;
                return returnData;
            } else {
                return null;
            }
        }
    }

    private class Node {

        private T data;
        private Node previous;
        private Node next;

        private Node(T data) {
            this.data = data;
            next = null;
            previous = null;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        private Node(T data, Node next, Node previous) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }
}
