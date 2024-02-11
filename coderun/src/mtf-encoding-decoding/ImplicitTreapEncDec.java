import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class ImplicitTreapEncDec {
    // root of the tree
    Node root = null;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[]       params = reader.readLine().split(" ");
        int            n      = Integer.parseInt(params[0]);
        int            m      = Integer.parseInt(params[1]);
        int            type   = Integer.parseInt(params[2]);

        String[] values = reader.readLine().split(" ");
        int[]    data   = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = Integer.parseInt(values[i]);
        }
        int[] result;

        if (type == 1) {
            result = ImplicitTreapEncDec.encode(data, n, m);
        } else {
            result = ImplicitTreapEncDec.decode(data, n, m);
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int j = 0; j < n; j++) {
            writer.write(String.valueOf(result[j]));
            if (j != n - 1) {
                writer.write(" ");
            }
        }

        writer.close();
        reader.close();
    }

    static int[] encode(int[] text, int n, int m) {
        ImplicitTreapEncDec treap   = new ImplicitTreapEncDec();
        int[]               encText = new int[n];
        int[]               values  = new int[n];
        for (int i = 0; i < m; i++) {
            treap.add(i, i + 1);
        }
        for (int i = 0; i < n; i++) {
            values[i] = i + 1;
        }

        int minVal = 0;
        for (int i = 0; i < n; i++) {
            int      ind   = text[i] - 1;
            int      value = values[ind];
            NodePair pair  = treap.splitByValue(treap.root, value - 1);
            NodePair pair1 = treap.splitByValue(pair.right, value);
            encText[i] = pair.left != null ? pair.left.size + 1 : 1;
            values[ind] = --minVal;
            Node node = treap.merge(new Node(minVal), pair.left);
            treap.root = treap.merge(node, pair1.right);
        }
        return encText;
    }

    static int[] decode(int[] text, int n, int m) {
        ImplicitTreapEncDec   treap   = new ImplicitTreapEncDec();
        Map<Integer, Integer> values  = new HashMap<>(m);
        int[]                 decText = new int[n];
        for (int i = 0; i < m; i++) {
            treap.add(i, i + 1);
            values.put(i + 1, i + 1);

        }

        int minVal = 0;
        for (int i = 0; i < n; i++) {
            int      pos   = text[i];
            NodePair pair  = treap.split(treap.root, pos - 1);
            NodePair pair1 = treap.split(pair.right, 1);
            int      value = values.get(pair1.left.value);
            decText[i] = value;
            values.put(--minVal, value);
            Node node = treap.merge(new Node(minVal), pair.left);
            treap.root = treap.merge(node, pair1.right);
        }
        return decText;
    }


    public void remove(Integer k) {
        NodePair res = split(root, k);
        root = merge(res.left.left, merge(res.left.right, res.right));
    }

    public Integer get(Integer k) {
        if (k > getSize(root) || k <= 0)
            throw new IllegalArgumentException();
        return get(root, k);
    }

    // auxiliary function for get
    private Integer get(Node n, Integer k) {
        if (n == null)
            return null;
        Integer key = getSize(n.left) + 1;
        int     cmp = k.compareTo(key);
        if (cmp < 0)
            return get(n.left, k);
        else if (cmp > 0)
            return get(n.right, k - getSize(n.left) - 1);
        return n.value;
    }

    public void pushBack(Integer val) {
        root = merge(root, new Node(val));
    }

    public void add(Integer key, Integer val) {
        NodePair n = split(root, key);
        root = merge(n.left, merge(new Node(val), n.right));
    }

    public void modify(Integer key, Integer val) {
        modify(root, key, val);
    }

    // auxiliary method for modify
    private void modify(Node n, Integer key, Integer val) {
        Integer nKey = getSize(n.left) + 1;
        if (Objects.equals(nKey, key))
            n.value = val;
        else if (nKey > key)
            modify(n.left, key, val);
        else
            modify(n.right, key, val - getSize(n.left) - 1);
    }

    public void traverse() {
        traverse(root);
    }

    // auxiliary method for traverse
    private void traverse(Node n) {
        if (n == null)
            return;
        traverse(n.left);
        System.out.println(n.value + " " + n.priority);
        traverse(n.right);
    }

    public void clear() {
        this.root = null;
    }

    private void resetSize(Node n) {
        if (n != null)
            n.size = getSize(n.left) + getSize(n.right) + 1;
    }

    private int getSize(Node n) {
        return n == null ? 0 : n.size;
    }

    // auxiliary function to merge
    private Node merge(Node t1, Node t2) {
        if (t1 == null)
            return t2;
        else if (t2 == null)
            return t1;

        Node newRoot = null;
        if (t1.priority > t2.priority) {
            t1.right = merge(t1.right, t2);
            newRoot = t1;
        } else {
            t2.left = merge(t1, t2.left);
            newRoot = t2;
        }
        resetSize(newRoot);
        return newRoot;
    }

    // auxiliary function to split
    private NodePair split(Node n, Integer key) {
        NodePair res = new NodePair(null, null);
        if (n == null)
            return res;
        int nKey = getSize(n.left) + 1;
        if (nKey > key) {
            res = split(n.left, key);
            n.left = res.right;
            res.right = n;
            resetSize(res.left);
            resetSize(res.right);
            return res;
        } else {
            res = split(n.right, key - getSize(n.left) - 1);
            n.right = res.left;
            res.left = n;
            resetSize(res.left);
            resetSize(res.right);
            return res;
        }
    }

    private NodePair splitByValue(Node n, Integer value) {
        NodePair res = new NodePair(null, null);
        if (n == null)
            return res;
        Integer nValue = n.value;
        if (nValue > value) {
            res = splitByValue(n.left, value);
            n.left = res.right;
            res.right = n;
            resetSize(res.left);
            resetSize(res.right);
            return res;
        } else {
            res = splitByValue(n.right, value);
            n.right = res.left;
            res.left = n;
            resetSize(res.left);
            resetSize(res.right);
            return res;
        }
    }

    // object representing the nodes of the tree
    static class Node {
        Integer size;
        Integer value;
        Double  priority;
        Node    left;
        Node    right;

        Node(int value) {
            this.value = value;
            this.size = 1;
            priority = Math.random();
        }
    }

    // object representing a pair of nodes of the tree
    class NodePair {
        Node left;
        Node right;

        NodePair(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }
}