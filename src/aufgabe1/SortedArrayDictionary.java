package Alda1;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayDictionary<K, V> implements Dictionary<K, V> {

	private static final int DEF_CAPACITY = 16;
	private int size;
	private Entry<K, V>[] data;

	@SuppressWarnings("unchecked")
	public SortedArrayDictionary() {
		size = 0;
		data = new Entry[DEF_CAPACITY];
	}

	@SuppressWarnings("unchecked")
	private void ensureCapacity(int newCapacity) {

		if (newCapacity < size)
			return;
		Entry[] old = data;
		data = new Entry[newCapacity];
		System.arraycopy(old, 0, data, 0, size);
	}

	@Override
	public V insert(K key, V value) {
		int i = searchKey(key);
		if (i != -1){
			V r = data[i].getValue();
			value = data[i].setValue(value);
			return r;
		}
		if (data.length == size) {
			ensureCapacity(2 * size);
		}
		int j = size-1;
		Comparable<? super K> comparableKey = (Comparable<? super K>) key;
		while (j >= 0 && comparableKey.compareTo(data[j].getKey()) < 0) {
			data[j+1] = data[j];
			j--;
		}
		data[j+1] = new Entry<K,V>(key,value);
		size++;
		return null;
	}

	private int searchKey(K key) {
		int li = 0;
		int re = this.size -1;
		Comparable<? super K> comparableKey = (Comparable<? super K>) key;
		
		while (re >= li) {
			int m = (li + re)/2;
			if (comparableKey.compareTo(data[m].getKey())< 0)
				re = m - 1;
			else if (comparableKey.compareTo(data[m].getKey()) > 0)
				li= m + 1;
			else 
				return m; // key gefunden
			}
		return -1; // key nicht gefunden
	}

	@Override
	public V search(K key) {
		int i = searchKey(key);
		if(i >= 0)
			return data[i].getValue();
		else
			return null;
	}

	@Override
	public V remove(K key) {
		int i = searchKey(key);
		if (i == -1)
			return null;
		// Datensatz loeschen und Lï¿½cke schlieï¿½en
		V r = data[i].getValue();
		for (int j = i; j < size-1; j++)
			data[j] = data[j+1];
		
		data[--size] = null;
		return r;
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new SortedArrayDictionaryIterator();
	}
	
    private class SortedArrayDictionaryIterator implements Iterator<Entry<K,V>> {
        private int current = 0;

        public boolean hasNext(){
        	
            return current < size;
        }
        
        public Entry<K,V> next() {   
        	if (!(hasNext()))
        		throw new NoSuchElementException();
            return data[current++];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

	
}
