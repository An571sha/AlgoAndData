package Alda1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

	public SortedArrayDictionary(java.util.Comparator<? super K> cmp) {

	}

	private static class Entry<K,V> {
		K key;
		V value;
		Entry<K,V>next;
		Entry(K k, V v) {
			key = k; value = v;
		}
	};
	private static final int DEF_CAPACITY = 16;
	private int size;
	private Entry<K,V>[] data;


	public SortedArrayDictionary() {
		size = 0;
		data = new Entry[DEF_CAPACITY];
	}

	private void ensureCapacity(int newCapacity) {
		if (newCapacity < size) {
			return;
		}
		Entry<K, V>[] old = data;
		data = new Entry[newCapacity];
		System.arraycopy(old, 0, data, 0, size);
	}

	private int searchKey(K key) {
		int li  = 0;
		int re  = size -1;

		while (re >= 1) {
			int m =  (li + re) / 2;
			if (key.compareTo(data[m].key) < 0)
				re  = m - 1;
			else if (key.compareTo(data[m].key) > 0 )
				li  = m + 1;
			else
				return m; // key gefunden
		}

		return -1; // key nicht gefunden
	}



	@Override
	public V insert(K key, V value) {

		int i = searchKey(key);

		// Vorhander Eintrag wird überschrieben

		if (i != -1) {
			V r = data[i].value;
			data[i].value = value;
			return r;
		}

		// Neueintrag
		if (data.length == size) {
			ensureCapacity(2*size);
		}

		int j = size-1;
		while (j >= 0 && key.compareTo(data[i].key) < 0) {
			data[j + 1] = data[j];
			j--;
		}
		data[j+1] = new Entry<K, V>(key, value);
		size++;
		return null;
	}




	@Override
	public V search(K key) {
		int hash = getHashcode(key);
		for (Entry<K, V> e = data[hash]; e != null; e = e.next) {
			if (e.key.equals(key)) {
				return e.value;
			}
		}
		return null;
	}

	public int getHashcode(K key) {
		int a = key.hashCode();
		if(a<0)
			a=-a;
		a=a%data.length;
		return a;
	}

	@Override
	public V remove(K key) {
		int hash = getHashcode(key);
		V v = search(key);
		if(v !=null)
			for (Entry<K, V> e = data[hash]; e != null; e = e.next) {
				if (e.key.equals(key)) {
					data[hash] = e.next;
					size--;
					return e.value;

				}

			}
		return null;


	}

	@Override
	public int size() {
		return size;
	}

	@Override
	// Don't forget to change your Package since we have different ones
	public Iterator<Alda1.Dictionary.Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new SortedArrayDictionaryIterator();
	}

	// Here aswell or else it will give you an error
	private class SortedArrayDictionaryIterator implements Iterator<Alda1.Dictionary.Entry<K,V>> {
		private int current = 0;

		public boolean hasNext() {
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

