package ADT;

import java.io.Serializable;

public class ArrayList<T> implements ListInterface<T>, Serializable {

    private T[] array;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Initial Capacity:" + initialCapacity);
        }
        array = (T[]) new Object[initialCapacity];
        size = 0;
    }

    @Override
    public void add(T newEntry) {
        if (isArrayFull()) {
            doubleArray();
        }
        array[size++] = newEntry;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        boolean check = false;
        if ((newPosition > 0) && (newPosition <= size + 1)) {
            if (isArrayFull()) {
                doubleArray();
            }
            makeRoom(newPosition);
            array[newPosition - 1] = newEntry;
            check = true;
            size++;
        }
        return check;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition > 0) && (givenPosition <= size)) {
            result = array[givenPosition - 1];
            if (givenPosition < size) {
                removeGap(givenPosition);
            }
            size--;
        }
        return result;
    }

    //extra : remove first occurance found in list based on object
    @Override
    public boolean removeFirst(T objectRemoved) {
        boolean isRemoved = false;
        for (int i = 0; !isRemoved && (i < size); i++) {
            if (array[i].equals(objectRemoved)) {
                isRemoved = true;
                if (i < (size - 1)) {
                    removeGap(i + 1);
                }
                size--;
            }
        }
        return isRemoved;
    }

    @Override//checked
    public void clear() {
        size = 0;
    }

    @Override//checked
    public boolean replace(int givenPosition, T newEntry) {
        boolean check = false;
        if ((givenPosition > 0) && (givenPosition <= size)) {
            array[givenPosition - 1] = newEntry;
            check=true;
        } 
        return check;
    }

    @Override//checked
    public T getEntry(int givenPosition) {
        T result = null;
        if ((givenPosition > 0) && (givenPosition <= size)) {
            result = array[givenPosition - 1];
        }
        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        return search(0, size - 1, anEntry);
    }

    //extra : Find the index of that object from the front else return -1 if not found
    @Override
    public int indexOf(T entry) {
        if (entry == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (entry.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    //extra : Find the last index of that object from the rear else return -1 if not found
    @Override
    public int lastIndexOf(T entry) {
        if (entry == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (entry.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override//O(1)
    public int getSize() {
        return size;
    }

    @Override//O(1)
    public boolean isEmpty() {
        return size == 0;
    }

    @Override//O(1)
    public boolean isFull() {
        return false;
    }

    @Override//ok change
    public String toString() {
        String outputStr = "";
        int index = 0;
        while (index < size) {
            outputStr += array[index] + "\n";
            index++;
        }
        return outputStr;
    }

    //utility function
    /**
     * Task: Makes room for a new entry at newPosition. Precondition: 1 <=
     * newPosition <= numberOfEntries + 1; numberOfEntries is array's
     * numberOfEntries before addition.
     */
    private void makeRoom(int newPosition) {//ok
        int newIndex = newPosition - 1;
        int lastIndex = size - 1;

        // move each entry to next higher index, starting at end of
        // array and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    /**
     * Task: Shifts entries that are beyond the entry to be removed to the next
     * lower position. Precondition: array is not empty; 1 <= givenPosition <
     * numberOfEntries; numberOfEntries is array's numberOfEntries before
     * removal.
     */
    private void removeGap(int givenPosition) {//ok
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        int removedIndex = givenPosition - 1;
        int lastIndex = size - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    private void doubleArray() {
        T[] oldArray = array;
        array = (T[]) new Object[oldArray.length * 2];

        System.arraycopy(oldArray, 0, array, 0, oldArray.length);
    }

    private boolean isArrayFull() {
        return size == array.length;
    }

    private boolean search(int first, int last, T desiredItem) {
        boolean found;

        if (first > last) {
            found = false; // no elements to search
        } else if (desiredItem.equals(array[first])) {
            found = true;
        } else {
            found = search(first + 1, last, desiredItem);
        }
        return found;
    }
}
