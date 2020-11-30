package util;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class BinarySearchTree<K extends Comparable<K>> implements Map<K, BinaryAddress> {

    private Node root; // root of BST

    private class Node {
        private K key; // sorted by key
        private BinaryAddress val; // associated data
        private boolean onlyLabels;
        private Node left;
        private Node right; // left and right subtrees
        private int size; // number of nodes in subtree

        public Node(K key, BinaryAddress val, boolean onlyLabels, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.onlyLabels = onlyLabels;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BinarySearchTree() {
        // Empty
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.size;
    }

    public boolean contains(K key) {
        if (key == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object arg0) {
        if (!(arg0 instanceof Comparable))
            throw new IllegalArgumentException("argument to contains() is not of type Comparable");
        return get((K) arg0) != null;
    }

    @Override
    public boolean containsValue(Object arg0) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }



    @Override
    public Set<Entry<K, BinaryAddress>> entrySet() {
        throw new UnsupportedOperationException();
    }



    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends BinaryAddress> arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<BinaryAddress> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public BinaryAddress get(Object arg0) {
        if(arg0 instanceof Comparable)
            return getAddress(root, (K) arg0);
        else
            return null;
    }

    public BinaryAddress get(K key) {
        return getAddress(root, key);
    }

    private BinaryAddress getAddress(Node x, K key) {
        if (key == null)
            throw new IllegalArgumentException("calls get() with a null key");
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return getAddress(x.left, key);
        else if (cmp > 0)
            return getAddress(x.right, key);
        else
            return x.val;
    }

    public boolean isOnlyLabels(K key){
        return checkForLabels(root, key);
    }

    private boolean checkForLabels(Node x, K key){
        if(key == null)     throw new IllegalArgumentException("call checkForLabels() with a null key");
        if(x == null)       throw new NoSuchElementException("No element found with key: "+key);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return checkForLabels(x.left, key);
        else if (cmp > 0)
            return checkForLabels(x.right, key);
        else
            return x.onlyLabels;
    }

    public BinaryAddress put(K key, BinaryAddress val) {
        if (key == null)
            throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
        }
        root = put(root, key, val);
        return root.val;
    }

    private Node put(Node x, K key, BinaryAddress val) {
        if (x == null){
            if(key.toString().contains("*")){
                if(key instanceof CharSequence){
                    @SuppressWarnings("unchecked")
                    K k = (K) key.toString().replace("*", "");
                    return new Node(k , val, true, 1);
                }
                else{
                    return new Node(key , val, false, 1);
                }
            }
            else
                return new Node(key, val, false, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else {
            x.val = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BinaryAddress remove(Object arg0) {
        if (arg0 instanceof Comparable)
            root = delete(root, (K) arg0);
        else
            throw new IllegalArgumentException("calls delete() with a null key");
        return null;
    }

    public void delete(K key) {
        if (key == null)
            throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public K min() {
        if (isEmpty())
            throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        else
            return min(x.left);
    }

    public K max() {
        if (isEmpty())
            throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        else
            return max(x.right);
    }

    public static void main(String[] args) {
        Map<String,BinaryAddress> instructions = new BinarySearchTree<>();

        String[] a = { "ldv*", "halt", "trap", "add" };
        int[] add = { 0x44, 0x33, 0x55, 0x40 };

        for (int i = 0; i < a.length; i++) {
            instructions.put(a[i], new BinaryAddress(add[i]));
        }

        System.out.println(((BinarySearchTree<String>) instructions).isOnlyLabels("ldv"));
    }

}
