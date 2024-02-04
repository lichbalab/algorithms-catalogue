import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Time: O(n*m/2)
 * Memory: O(n + m)
 */
public class Encryption {

    static int count = 0;

    public static void main(String[] args) throws IOException {

        count++;
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
            result = encrypt(data, n, m);
        } else {
            result = decrypt(data, n, m);
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

    static class RearrangingIntList {


        int[] permutation;

        int m;

        int head;

        int tail;

        Map<Integer, Integer> valueToIndex = new HashMap<>();

        AugmentedAVLTree avl;

        public RearrangingIntList(int n, int m, boolean encryption) {
            avl = new AugmentedAVLTree();

            if (!encryption) {
                permutation = new int[n + m];
            } else {
                valueToIndex = new HashMap<>(m);
            }

            for (int i = 0; i < m; i++) {
                if (!encryption) {
                    avl.insert(i + n);
                    permutation[i + n] = i + 1;
                } else {
                    valueToIndex.put(i + 1, i + n);
                }
            }

            this.m = m;
            this.head = n;
            this.tail = n + m - 1;
        }

        public int findIndexAndMoveToBeginning(int value) {
            int index = valueToIndex.get(value);

            if (index == head) {
                return 1;
            }

            int shift = avl.countElementsNotMoreThan(index);
            int pos   = index - head - shift + 1;

            head = head - 1;
            avl.insert(index);
            valueToIndex.put(value, head);
            return pos;
        }

        public int getValueAndMoveToBeginning(int pos) {
            if (pos == 1) {
                return permutation[head];
            }

            int index = avl.findElementByPosition(pos - 1);

            int value = permutation[index];

            head = head - 1;
            permutation[head] = value;
            avl.remove(index);
            avl.insert(head);

            return value;
        }
    }

    public static int[] encrypt1(int[] text, int n, int m) {
        int[] permutation = new int[m];
        for (int i = 0; i < m; i++) {
            permutation[i] = i + 1;
        }

        int[] encryptedText = new int[n];

        for (int i = 0; i < n; i++) {
            int x = text[i];
            int y = findIndexAndMoveToBeginning(permutation, x, m);
            encryptedText[i] = y;
        }

        return encryptedText;
    }

    public static int[] encrypt(int[] text, int n, int m) {
        RearrangingIntList permutation   = new RearrangingIntList(n, m, true);
        int[]              encryptedText = new int[n];

        for (int i = 0; i < n; i++) {
            int x = text[i];
            int y = permutation.findIndexAndMoveToBeginning(x);
            encryptedText[i] = y;
        }

        return encryptedText;
    }

    public static int[] decrypt(int[] encryptedText, int n, int m) {
        RearrangingIntList permutation = new RearrangingIntList(n, m, false);

        int[] decryptedText = new int[n];
        for (int i = 0; i < n; i++) {
            int y = encryptedText[i];
            int x = permutation.getValueAndMoveToBeginning(y);
            decryptedText[i] = x;
        }

        return decryptedText;
    }

    public static int[] decrypt1(int[] encryptedText, int n, int m) {

        int[] permutation = new int[m];
        for (int i = 0; i < m; i++) {
            permutation[i] = i + 1;
        }

        int[] decryptedText = new int[n];
        for (int i = 0; i < n; i++) {
            int y = encryptedText[i];
            int x = permutation[y - 1];
            decryptedText[i] = x;
            moveToBeginning(permutation, y - 1);
        }

        return decryptedText;
    }

    public static void moveToBeginning(int[] arr, int position) {
        // Save the element to move
        int toMove = arr[position];

        // Shift elements to the left to fill the gap
        for (int i = position; i > 0; i--) {
            arr[i] = arr[i - 1];
        }

        // Place the saved element at the beginning
        arr[0] = toMove;
    }

    public static int findIndexAndMoveToBeginning(int[] arr, int target, int m) {
        int previous = -1;
        for (int i = 0; i < m; i++) {
            int current = arr[i];
            if (previous != -1) {
                arr[i] = previous;
            }
            previous = current;
            if (current == target) {
                arr[0] = current;
                return i + 1;
            }
        }
        return -1; // Element not found
    }


    public static class ArrayTreeSet {
        private int[] arr;
        private int   size;

        public ArrayTreeSet(int capacity) {
            arr = new int[capacity];
            size = 0;
        }

        // Adds a new element if it doesn't already exist in the set
        public boolean add(int value, int pos) {
            // Shift elements to the right to make space for the new element
            for (int i = size; i > pos; i--) {
                arr[i] = arr[i - 1];
            }

            // Insert the new element
            arr[pos] = value;
            size++;
            return true;
        }

        // Binary search to find the insertion point or existing element
        public int binarySearch(int value) {
            int low  = 0;
            int high = size - 1;

            while (low <= high) {
                int mid    = (low + high) >>> 1;
                int midVal = arr[mid];

                if (midVal < value)
                    low = mid + 1;
                else if (midVal > value)
                    high = mid - 1;
                else
                    return mid; // Element found
            }

            return low; // Element not found, should be inserted at low
        }

        // Checks if the set contains the specified element
        public boolean contains(int value) {
            int pos = binarySearch(value);
            return pos < size && arr[pos] == value;
        }
    }

    static class AVLNode {
        int     value;
        AVLNode left, right;
        int height;
        int count; // Number of nodes in the subtree rooted at this node, including this node

        AVLNode(int d) {
            value = d;
            height = 1; // New node is initially added at leaf
            count = 1;
        }
    }

    static class AugmentedAVLTree {
        AVLNode root;

        // A utility function to get the height of the tree
        private int height(AVLNode N) {
            if (N == null) return 0;
            return N.height;
        }

        // A utility function to get size of the tree rooted at N
        private int size(AVLNode N) {
            if (N == null) return 0;
            return N.count;
        }

        // A utility function to right rotate subtree rooted with y
        private AVLNode rightRotate(AVLNode y) {
            AVLNode x  = y.left;
            AVLNode T2 = x.right;

            // Perform rotation
            x.right = y;
            y.left = T2;

            // Update heights
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            // Update sizes
            y.count = size(y.left) + size(y.right) + 1;
            x.count = size(x.left) + size(x.right) + 1;

            // Return new root
            return x;
        }

        // A utility function to left rotate subtree rooted with x
        private AVLNode leftRotate(AVLNode x) {
            AVLNode y  = x.right;
            AVLNode T2 = y.left;

            // Perform rotation
            y.left = x;
            x.right = T2;

            // Update heights
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;

            // Update sizes
            x.count = size(x.left) + size(x.right) + 1;
            y.count = size(y.left) + size(y.right) + 1;

            // Return new root
            return y;
        }

        // Get Balance factor of node N
        private int getBalance(AVLNode N) {
            if (N == null) return 0;
            return height(N.left) - height(N.right);
        }

        // Recursive function to insert a node with given key in the subtree rooted
        // with node and returns the new root of the subtree.
        AVLNode insert(AVLNode node, int value) {
            /* 1. Perform the normal BST insertion */
            if (node == null) return (new AVLNode(value));

            if (value < node.value) node.left = insert(node.left, value);
            else if (value > node.value) node.right = insert(node.right, value);
            else return node; // Duplicate values not allowed

            /* 2. Update height and size of this ancestor node */
            node.height = 1 + Math.max(height(node.left), height(node.right));
            node.count = 1 + size(node.left) + size(node.right);

        /* 3. Get the balance factor of this ancestor node to check whether
           this node became unbalanced */
            int balance = getBalance(node);

            // If this node becomes unbalanced, then there are 4 cases

            // Left Left Case
            if (balance > 1 && value < node.left.value) return rightRotate(node);

            // Right Right Case
            if (balance < -1 && value > node.right.value) return leftRotate(node);

            // Left Right Case
            if (balance > 1 && value > node.left.value) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Right Left Case
            if (balance < -1 && value < node.right.value) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            /* return the (unchanged) node pointer */
            return node;
        }

        // Function to insert a new value with unique value
        public void insert(int value) {
            root = insert(root, value);
        }

        private int countLessThanEqualTo(AVLNode node, int value) {
            if (node == null) {
                return 0;
            }

            if (value < node.value) {
                // If the value is less than the current node's value,
                // then all elements greater than the value are in the right subtree,
                // which we don't need to consider.
                return countLessThanEqualTo(node.left, value);
            } else if (value > node.value) {
                // If the value is greater than the current node's value,
                // include the current node, all of the left subtree (as all these values are less),
                // and then recurse on the right subtree.
                return size(node.left) + 1 + countLessThanEqualTo(node.right, value);
            } else {
                // If the value is equal to the current node's value,
                // include the current node and all of the left subtree.
                return size(node.left) + 1;
            }
        }


        // Public wrapper method to call the private recursive method
        public int countElementsNotMoreThan(int value) {
            return countLessThanEqualTo(root, value);
        }

        private int countMoreThanEqualTo(AVLNode node, int value) {
            if (node == null) {
                return 0;
            }
            if (value > node.value) {
                return countMoreThanEqualTo(node.right, value);
            } else {
                // Include the current node + all nodes in its right subtree
                return size(node.right) + 1 + countMoreThanEqualTo(node.left, value);
            }
        }

        public int countMoreThanEqualTo(int value) {
            return countMoreThanEqualTo(root, value);
        }

        // Method to remove a value (simplified placeholder)
        public void remove(int value) {
            root = removeRecursive(root, value);
        }

        private AVLNode removeRecursive(AVLNode node, int value) {
            if (node == null) {
                return null;
            }

            // Step 1: Perform standard BST delete
            if (value < node.value) {
                node.left = removeRecursive(node.left, value);
            } else if (value > node.value) {
                node.right = removeRecursive(node.right, value);
            } else {
                // nodes with only one child or no child
                if (node.left == null || node.right == null) {
                    node = (node.left != null) ? node.left : node.right;
                } else {
                    // node with two children: Get the inorder successor
                    AVLNode temp = minValueNode(node.right);
                    node.value = temp.value;
                    node.right = removeRecursive(node.right, temp.value);
                }
            }

            // If the tree had only one node then return
            if (node == null) return null;

            // Step 2: Update height of the current node
            node.height = Math.max(height(node.left), height(node.right)) + 1;

            // Step 3: Update the count of the current node
            node.count = 1 + size(node.left) + size(node.right);

            // Step 4: Rebalance the node if needed
/*
            int balance = getBalance(node);

            // Left Left Case
            if (balance > 1 && getBalance(node.left) >= 0) {
                return rightRotate(node);
            }

            // Left Right Case
            if (balance > 1 && getBalance(node.left) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Right Right Case
            if (balance < -1 && getBalance(node.right) <= 0) {
                return leftRotate(node);
            }

            // Right Left Case
            if (balance < -1 && getBalance(node.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

*/
            return node;
        }

        private AVLNode minValueNode(AVLNode node) {
            AVLNode current = node;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        // Method to find an element by its position in sorted order
        private int findElementByPositionRecursive(AVLNode node, int targetPos) {
            if (node == null) {
                return -1; // Element not found or error
            }

            int leftSubtreeSize = size(node.left); // Size of the left subtree

            if (targetPos <= leftSubtreeSize) {
                // The target position is within the left subtree
                return findElementByPositionRecursive(node.left, targetPos);
            } else if (targetPos == leftSubtreeSize + 1) {
                // The target position matches the current node (considering leftSubtreeSize)
                return node.value;
            } else {
                // The target position is within the right subtree
                // Adjust targetPos by subtracting the size of the left subtree and the current node
                return findElementByPositionRecursive(node.right, targetPos - leftSubtreeSize - 1);
            }
        }

        public int findElementByPosition(int position) {
            // Ensure position is within valid range before proceeding
            if (position < 0 || position >= size(root)) {
                return -1; // Invalid position
            }
            return findElementByPositionRecursive(root, position + 1); // Adjust for 1-based indexing
        }
    }
}