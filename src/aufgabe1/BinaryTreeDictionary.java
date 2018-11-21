package aufgabe1;

import java.util.Iterator;

 public class BinaryTreeDictionary<K, V> implements Dictionary<K, V> {
     static private class Node<K, V> {
         private K key;
         private V value;
         private int height;
         private Node<K, V> parent;
         private Node<K, V> left;
         private Node<K, V> right;

         private Node(K k, V v) {
             key = k;
             value = v;
             height = 0;
             left = null;
             right = null;
             parent = null;
         }
     }

     private Node<K, V> root = null;
     private V oldValue;

     @Override
     public V insert(K key, V value) {
         root = insertR(key,value,root);
         return oldValue;
     }

     private Node<K,V> insertR(K key,V value, Node<K,V>p){
         Comparable<? super K> comparableKey = (Comparable<? super K>) key;
         if (p == null){
            p = new Node(key,value);
            oldValue = null;
         }else if (comparableKey.compareTo(p.key)<0){
             p.left = insertR(key,value,p.left);
         }else if( comparableKey.compareTo(p.key)>0){
             p.right = insertR(key,value,p.left);
         }else{
             oldValue = p.value;
             p.value = value ;

         }
         return p;
     }

     @Override
     public V search(K key) {
         return searchR(key, root);
     }

     private V searchR(K key, Node<K, V> p) {

         Comparable<? super K> comparableKey = (Comparable<? super K>) key;

         if (p == null)
             return null;
         else if (comparableKey.compareTo(p.key) < 0) //Key kleiner als der gesuchte Key, geh nach Links
             return searchR(key, p.left);
         else if (comparableKey.compareTo(p.key) > 0) // //Key großer als der gesuchte Key, geh nach Rechts
             return searchR(key, p.right);
         else
             return p.value;
     }

     @Override
     public V remove(K key) {
         root = removeR(key, root);
         return oldValue;

     }

     private Node<K, V> removeR(K key, Node<K, V> p) {
         Comparable<? super K> comparableKey = (Comparable<? super K>) key;
         if (p == null) {
             return null;
         } else if (comparableKey.compareTo(p.key) < 0) {
             p.left = removeR(key, p.left);
             if (p.left != null)
                 p.left.parent = p;
//
         } else if (comparableKey.compareTo(p.key) > 0) {
             p.right = removeR(key, p.right);
             if (p.right != null)
                 p.right.parent = p;
         } else {
             if (p.left == null || p.right == null) {
                 // p hat ein oder kein Kind:
                 oldValue = p.value;
                 p = (p.left != null) ? p.left : p.right;
             } else {
                 // p hat zwei Kinder:
                 MinEntry<K, V> min = new MinEntry<K, V>();
                 // p.right = getRemMinR(p.right, min);
                 if (p.right != null)
                     p.right.parent = p;
                 oldValue = p.value;
                 p.key = min.key;
                 p.value = min.value;
             }
         }

         return p;
     }

     private int getHeight(Node<K,V> p) {
         if(p == null)
             return -1;
         else
             return p.height;
     }



     private static class MinEntry<K, V> {
         private K key;
         private V value;
     }

     @Override
     public int size() {
         return 0;
     }

     @Override
     public Iterator<Entry<K, V>> iterator() {
         return null;
     }
 }

