package ADT;

import java.util.Iterator;

/**
 *
 * @author Wong Kai Hao
 */
/**
 * SortedListInterface - An interface for the ADT sorted list.
 */
public interface SortedList<T extends Comparable<T>> {

    /**
     * Task: Adds a new entry to the sorted list in its proper order.
     *
     * @param newEntry the object to be added as a new entry
     * @return true if the addition is successful
     */
    public void add(T newEntry);

    /**
     * Task: Removes a specified entry from the sorted list.
     *
     * @param anEntry the object to be removed
     * @return true if anEntry was located and removed
     */
    public boolean remove(T anEntry);

    public T getEntry(int givenPosition);

    public boolean contains(T anEntry);

    public void clear();

    public int getSize();

    public boolean isEmpty();

    public boolean isFull();

    public Iterator<T> getIterator();

    public String toString();
}
