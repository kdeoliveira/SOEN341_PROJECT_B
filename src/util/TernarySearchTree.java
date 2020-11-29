package util;

import java.util.NoSuchElementException;

public class TernarySearchTree<K extends Comparable<K>, V> {

    private Node root; // root of BST

    private class Node {
        private K key; // sorted by key
        private BinaryAddress val; // associated data
        private V value;
        private Node left;
        private Node right; // left and right subtrees
        private int size; // number of nodes in subtree

        public Node(K key, BinaryAddress val, V value, int size) {
            this.key = key;
            this.val = val;
            this.value = value;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public TernarySearchTree() {
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
        return getAddress(key) != null;
    }

    public BinaryAddress getAddress(K key) {
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

    public V getValue(K key) {
        return getValue(this.root, key);
    }

    private V getValue(Node x, K key) {
        if (key == null)
            throw new IllegalArgumentException("calls get() with a null key");
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return getValue(x.left, key);
        else if (cmp > 0)
            return getValue(x.right, key);
        else
            return x.value;
    }

    public void put(K key, BinaryAddress val, V value) {
        if (key == null)
            throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val, value);
    }

    private Node put(Node x, K key, BinaryAddress val, V value) {
        if (x == null)
            return new Node(key, val, value, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val, value);
        else if (cmp > 0)
            x.right = put(x.right, key, val, value);
        else {
            x.val = val;
            x.value = value;
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
        TernarySearchTree<String, String> instructions = new TernarySearchTree<>();
        String[] a = { "ldv", "halt", "trap", "add" };
        String[] comment = { "Load Variable", "Stop Machine", "Return exit", "Addition of constant" };
        int[] add = { 0x44, 0x33, 0x55, 0x40 };

        for (int i = 0; i < a.length; i++) {
            instructions.put(a[i], new BinaryAddress(add[i], false), comment[i]);
        }

        System.out.println(instructions.getAddress("halt"));
    }
}
