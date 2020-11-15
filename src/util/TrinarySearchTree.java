package util;

import java.util.NoSuchElementException;
public class TrinarySearchTree{
    
    private Node root;             // root of BST

    private class Node {
        private String key;           // sorted by key
        private BinaryAddress val;         // associated data
        private String comment;
        private Node left;
        private Node right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(String key, BinaryAddress val, String comment, int size) {
            this.key = key;
            this.val = val;
            this.comment = comment;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public TrinarySearchTree() {
        //Empty
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return getAddress(key) != null;
    }

    public BinaryAddress getAddress(String key) {
        return getAddress(root, key);
    }

    private BinaryAddress getAddress(Node x, String key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) throw new NoSuchElementException();
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return getAddress(x.left, key);
        else if (cmp > 0) return getAddress(x.right, key);
        else              return x.val;
    }

    public String getComment(String key){
        return getComment(this.root, key);
    }

    private String getComment(Node x, String key){
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return getComment(x.left, key);
        else if (cmp > 0) return getComment(x.right, key);
        else              return x.comment;
    }

    public void put(String key, BinaryAddress val, String comment) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val, comment);
    }

    private Node put(Node x, String key, BinaryAddress val, String comment) {
        if (x == null) return new Node(key, val, comment,1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val, comment);
        else if (cmp > 0) x.right = put(x.right, key, val, comment);
        else              {
                x.val   = val;
                x.comment = comment;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }

    private Node delete(Node x, String key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else { 
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        } 
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    } 

    public String min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    public String max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    private Node max(Node x) {
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 


    public static void main(String[] args){
        TrinarySearchTree instructions = new TrinarySearchTree();
        String[] a = {"ldv", "halt", "trap", "add"};
        String[] comment = {"Load Variable", "Stop Machine", "Return exit", "Addition of constant"};
        int[] add = {0x44, 0x33, 0x55, 0x40};

        for(int i = 0 ; i < a.length ; i++){
            instructions.put(a[i], new BinaryAddress(add[i], false),comment[i]);
        }

        System.out.println(
            instructions.getAddress("halt")
        );
    }
}
