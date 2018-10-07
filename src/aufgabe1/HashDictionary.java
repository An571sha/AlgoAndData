package aufgabe1;

import java.util.Iterator;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
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


    public HashDictionary() {
        size = 0;
        data = new Entry[DEF_CAPACITY];
    }



    @Override
    public V insert(K key, V value) {
        int hash = getHashcode(key);
        for (Entry<K, V> e = data[hash]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                V old = e.value;
                e.value = value;
                return old;
            }

            else{
                data[hash]  = new  Entry<K,V>(key,value);

            }
        }

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

    public int getHashcode(K key){
        int a = key.hashCode();
        if(a<0)
            a=-a;
        a=a%data.length;
        return a;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
}
