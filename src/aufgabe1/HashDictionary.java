package Alda1;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Iterator;

public class HashDictionary<K, V> implements Dictionary<K, V> {
	
	private final static int DEF_CAPACITY = 31;
	private static int MAX_CAPACITY = 2147483647; 	
	private int size;
	private int capacity;
	LinkedList<Entry<K, V>> tab[];
	LinkedList<Entry<K, V>> loader;
	
	@SuppressWarnings("unchecked")
	public HashDictionary() {
		capacity = DEF_CAPACITY;
		size = 0;
		tab = (LinkedList<Entry<K, V>>[]) new LinkedList[DEF_CAPACITY];
	}
	
	
	
	public HashDictionary(int n) {
		capacity = n;
		tab = (LinkedList<Entry<K, V>>[]) new LinkedList[n];
	}
	
	
	
	int hash(K key) {
		int adr = key.hashCode();
		if(adr < 0) {
			adr = -adr;
		adr = adr % capacity;
		}
		return adr;
	}
	
	//Check if Empty, if empty - return null
	//
	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		if (this.size == 0)
			return null;
		
		int index = this.getIndex(key);
		
		if (index >= 0) {
			LinkedList<Entry<K, V>> list = tab[index];
			
			for(Entry<K, V> kv : list) {
				if(kv.getKey().equals(key)) {
					return kv.getValue();
				}
			}
		
			return null;
		
		} else {
			return null;
		}
	}
	
	// returns the exact Index of Hashtable
	private int getIndex(K key) {
		int index = Math.abs(key.hashCode() % this.tab.length);
		if (this.tab[index] == null)
			return (-index) -1;
		else
			return index;
	}
	

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		int index = this.getIndex(key);
		int list_index = 0;
		if(index < 0) 
			return null;
		LinkedList<Entry<K, V>> list = tab[index];

		V alt_value = null;
		
		for(Entry<K,V> kv : list) {
			if(kv.getKey().equals(key)) {
				alt_value = kv.getValue();
			}
			++list_index;
		}
		
		if(alt_value != null) {
			list.remove();
			this.size--;
			
			return alt_value;
		}
		
		return null;
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	
	
	@Override
	public V insert(K key, V value) {
		// TODO Auto-generated method stub
		
		int index = this.getIndex(key);
		if (this.size == 0)
		return null;
		
		if (size == capacity) { // load factor 2
			boolean isPrime = false;
			for(int n = capacity*2; n < MAX_CAPACITY; ++n) {
				isPrime = true;
				for(int check = 2; check < n; check = check +1) {
					if (n % check == 0) {
						isPrime = false;
						break;
					}
				}
				if (isPrime) {
					capacity = n;
					break;
				}
			}
			tab = Arrays.copyOf(tab, capacity);
			
		if (index < 0)
			index  = (-index) -1;
	
			LinkedList<Entry<K,V>> list = new LinkedList<Entry<K,V>>();
			list.addLast(new Entry<K,V>(key, value));
			this.tab[index] = list;
			this.size++;
			
			return null;
		} else {
			LinkedList<Entry<K,V>> list = tab[index];
			V old_value = null;
			
			int list_index = 0;
			for(Entry<K,V> kv : list) {
				if(kv.getKey().equals(key)) {
					old_value = kv.getValue();
					break;
				}
				++list_index;
			}
			
			if(old_value != null)
				list.remove(list_index);
			
			list.addLast(new Entry<K,V>(key, value));
			
			return old_value;
		}
	
	}
	
	
	// Return iterator to iterato over all the keys in this map
	@Override 
	public Iterator<Entry<K,V>> iterator() {
		return new HashDictionaryIterator();
	}
	
	private class HashDictionaryIterator implements Iterator<Entry<K,V>> {

		private int current = 0;
		private int bucketIndex = 0;
		LinkedList<Entry<K,V>> list = new LinkedList<Entry<K,V>>();
		
		public boolean hasNext() { 	
			// TODO Auto-generated method stub
			return current <= size;
		}

		public Entry<K, V> next() {
			// TODO Auto-generated method stub
			if(!hasNext())
				throw new NoSuchElementException();
			if(bucketIndex < tab[current].size())
				return tab[current].get(bucketIndex++);
			bucketIndex = 0;
			return tab[current++].get(bucketIndex++);
			
			
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	@Override public String toString() {
		StringBuilder sb = new StringBuilder();	
		sb.append("{");
		for(int i = 0; i < capacity; ++i) {
			if(tab[i] == null)
				break;
			for(Entry<K,V> entry : tab[i])
				sb.append(entry + ", ");
		
		}
		sb.append("}");
		return sb.toString();
		
	}
	
}