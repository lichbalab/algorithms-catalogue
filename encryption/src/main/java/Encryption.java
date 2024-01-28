import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Time: O(n*m/2)
 * Memory: O(n + m)
 */
public class Encryption {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[]       params = reader.readLine().split(" ");
        int            n      = Integer.parseInt(params[0]);
        int            m      = Integer.parseInt(params[1]);
        int            type   = Integer.parseInt(params[2]);

        String[] values = reader.readLine().split(" ");
        int[]    data   = new int[n];
        int      i      = 0;
        for (String value : values) {
            data[i] = Integer.parseInt(value);
            i++;
        }

        List<Integer> result;

        if (type == 1) {
            result = encryptData(data, m);
        } else {
            result = decryptData(data, m);
        }


        BufferedWriter    writer   = new BufferedWriter(new OutputStreamWriter(System.out));
        Iterator<Integer> iterator = result.iterator();
        while (iterator.hasNext()) {
            writer.write(String.valueOf(iterator.next()));
            if (iterator.hasNext()) {
                writer.write(" ");
            }
        }

        writer.close();
        reader.close();
    }

    static List<Integer> encryptData(int[] data, int m) {
        BiDirectionalIntList cipher = new BiDirectionalIntList(m, true);

        List<Integer> encryptedData = new ArrayList<>();

        for (int value : data) {
            encryptedData.add(cipher.getPosByValueAndRearrange(value));
        }

        return encryptedData;
    }

    static List<Integer> decryptData(int[] data, int m) {
        BiDirectionalIntList cipher = new BiDirectionalIntList(m, false);

        List<Integer> decryptedData = new ArrayList<>();

        for (int pos : data) {
            decryptedData.add(cipher.getValueAndRearrange(pos, m));
        }

        return decryptedData;

    }

    static class BiDirectionalLinkedList {
        private List<Integer> list = new ArrayList<>();

        public BiDirectionalLinkedList(int m) {
            for (int i = 1; i <= m; i++) {
                list.add(i);
            }
        }

        private int getValueAndRearrange(int i) {
            int value = list.get(i - 1);
            list.remove(i - 1);
            list.add(0, value);

            return value;
        }
    }

    static class BiDirectionalIntList {
        private Node first;
        private Node last;

        private final int m;

        private int maxValue = -1;

        Map<Integer, Node> map = new HashMap<>();

        public BiDirectionalIntList(int m, boolean addMap) {
            for (int i = 1; i <= m; i++) {
                add(i, addMap);
            }
            this.m = m;
        }

        private void add(int value, boolean addMap) {
            Node node = new Node(value);

            if (first == null) {
                first = node;
            } else {
                node.previous = last;
                last.next = node;
            }
            last = node;
            if (addMap) {
                map.put(value, node);
            }
        }

        private int getValueAndRearrange(int i, int m) {
            int pos;



            if (i < m / 2) {
                pos = 1;
                Node node = this.first;
                while (node != null) {
                    if (i == pos) {
                        if (i > 1) {
                            remove(node);
                            addAtBeginning(node);
                        }
                        return node.value;
                    }
                    node = node.next;
                    pos++;
                }
            } else {
                pos = m;
                Node node = this.last;
                while (node != null) {
                    if (i == pos) {
                        if (i > 1) {
                            remove(node);
                            addAtBeginning(node);
                        }
                        return node.value;
                    }
                    node = node.previous;
                    pos--;
                }
            }
            return -1;
        }

        private int getPosByValueAndRearrange(int value) {

            if (first.value == value) {
                return 1;
            }

            Node node = map.get(value);
            int  pos;

            if (value > maxValue) {
                maxValue = value;
                pos = maxValue;
            } else {
                Node left  = node.previous;
                Node right = node.next;
                int shift = 1;

                while (left != null && right != null) {
                    left = left.previous;
                    right = right.next;
                    shift++;
                }

                if (left == null) {
                    pos = shift;
                } else {
                    pos = m - shift + 1;
                }
            }

            if (pos > 1) {
                remove(node);
                addAtBeginning(node);
            }

            return pos;
        }

        private void remove(Node node) {
            if (node == first) {
                first = node.next;
                first.previous = null;
                last = first;
                node.next = null;
            } else if (node == last) {
                last = node.previous;
                last.next = null;
                node.previous = null;
            } else {
                Node previous = node.previous;
                Node next     = node.next;
                previous.next = next;
                next.previous = previous;
                node.next = null;
                node.previous = null;
            }
        }

        public void addAtBeginning(Node node) {
            if (first == null) {
                first = node;
                last = node;
            } else {
                node.next = first;
                first.previous = node;
                first = node;
            }
        }

    }

    static class Node {
        public int value;

        public int  pos;
        public Node next;
        public Node previous;

        public Node(int value) {
            this.value = value;
            this.pos = value;
        }

        int getPos() {
            if (previous == null) {
                return 1;
            }

            return previous.getPos() + 1;
        }

    }

    public static List<Integer> encrypt(int[] text, int m) {
        List<Integer> permutation = new LinkedList<>();
        for (int i = 1; i <= m; i++) {
            permutation.add(i);
        }

        List<Integer> encryptedText = new ArrayList<>();
        for (int x : text) {
            int pos = permutation.indexOf(x);
            encryptedText.add(pos + 1);
            permutation.remove(pos);
            permutation.add(0, x);
        }

        return encryptedText;
    }

    public static List<Integer> decrypt(int[] encryptedText, int m) {
        List<Integer> permutation = new ArrayList<>();
        for (int i = 1; i <= m; i++) {
            permutation.add(i);
        }

        List<Integer> decryptedText = new ArrayList<>();
        for (int x : encryptedText) {
            int symbol = permutation.get(x - 1);
            decryptedText.add(symbol);
            permutation.remove((Integer) symbol);
            permutation.add(0, symbol);
        }

        return decryptedText;
    }
}