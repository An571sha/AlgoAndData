package aufgabe1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K,V>,Iterable<Dictionary.Entry<K,V>> {
	private static class Entry<K,V> {
		K key;
		V value;
		Entry(K k, V v) {
			key = k; value = v;
			}
		};
		private static final int DEF_CAPACITY = 16;
		private int size;
		private Entry<K,V>[] data;

	public SortedArrayDictionary() {
		size=0;
		data = new Entry[DEF_CAPACITY];
	}
	
	private int searchKey(K key){
		int li = 0;
		int re = size - 1;
		while (re >= li) {
			int m = (li + re)/2;
			if (key.compareTo(data[m].key) < 0)
			re = m - 1;
			else if (key.compareTo(data[m].key) > 0)
			li= m + 1;
			else
			return m; // key gefunden
			}
			return -1; // key nicht gefunden
	}
	
	private void ensureCapacity(int newCapacity) {
		if (newCapacity < size)
		return;
		Entry[] old = data;
		data = new Entry[newCapacity];
		System.arraycopy(old, 0, data, 0, size);
	}

	@Override
	public int size() {
		return size;
	}


    @Override
	public Iterator<Dictionary.Entry<K, V>> iterator() {
		Iterator<Dictionary.Entry<K,V>> it = new Iterator<Dictionary.Entry<K,V>>() {
            private int currentIndex;
            @Override
            public boolean hasNext() {
                return null;
            }

            @Override
            public Object next() {
                Entry<K,V> currentEntry = new SortedArrayDictionary[currentIndex;];
            }

            @Override
            public void remove() {

            }
        };
	}

	/*private class DicIterator implements Iterator<Entry<K,V>> {
		private Entry<K,V> current;
		private K key;

		@Override
		public boolean hasNext() {
			int i = searchKey(key);
			V r = data[i].value;
			for (int j = i; j < size - 1; j++) {
				if (data[i].key != null)
					return true;
			}
			return false;
		}

		@SuppressWarnings("unused")
		@Override
		public Entry<K, V> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			int i = searchKey(key);
			V r = data[i].value;
			Entry<K,V> currentEntry = SortedArraydictionary[current];


			}
			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}*/


	@Override
	public V search(K key) {
		int i = searchKey(key);
		if (i >= 0)
		return data[i].value;
		else
		return null;
	}

	@Override
	public V remove(K key) {
		int i = searchKey(key);
		if (i == -1)
		return null;
		// Datensatz loeschen und Lücke schließen
		V r = data[i].value;
		for (int j = i; j < size-1; j++)
		data[j] = data[j+1];
		data[--size] = null;		
		return r;
	}

	@Override
	public V insert(K key, V value) {
		int i = searchKey(key);
		// Vorhandener Eintrag wird überschrieben:
		if (i != -1) {
		V r = data[i].value;
		data[i].value = value;
		return r;
		}
		// Neueintrag:
		if (data.length == size) {
		ensureCapacity(2*size);
		}
		int j = size-1;
		while (j >= 0 && key.compareTo(data[j].key) < 0) {
		data[j+1] = data[j];
		j--;
		}
		data[j+1] = new Entry<K,V>(key,value);
		size++;
		return null;
		
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		if(size !=0){
			for (int j = size(); j > 1; j--) {
				for (int i = 0; i < j - 1; i++) {
					if (data[i].key.compareTo(data[i+1].key)< 0) {
						Entry<K, V> tmp = data[i + 1];
						data[i + 1] = data[i];
						data[i] = tmp;

					}

				}

			}

		}
		return s.toString();
	}


	}
