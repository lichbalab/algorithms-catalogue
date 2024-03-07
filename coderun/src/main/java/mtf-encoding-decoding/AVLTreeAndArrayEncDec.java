import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * MTF (Move-To-Front) encryption implemented based on AVL Tree and int Array.
 * The fastest implementation withing provider implementations based on AVL Tree, Treap, Array.
 *
 * <p>
 * Time: O(n*log(m))
 * Memory: O(n + m)
 */
public class AVLTreeAndArrayEncDec {

    static PrintStream ps = System.out;

    public static void main(Object[] args) throws IOException {
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
            long start = System.currentTimeMillis();
            result = encrypt(data, n, m);
            long stop = System.currentTimeMillis();
            ((PrintStream)args[0]).println("------- " + (stop - start) + "ms");
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

        int priority;

        int[] permutation;

        int m;

        int n;

        int head;

        int tail;

        Map<Integer, Integer> valueToIndex = new HashMap<>();

        AugmentedAVLTree avl;

        AugmentedAVLTreePrioritized avlp;

        public RearrangingIntList(int n, int m, boolean encryption) {

            if (!encryption) {
                priority = n;
                avl = new AugmentedAVLTree();
                permutation = new int[n + m];
            } else {
                avl = new AugmentedAVLTree();
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
            this.n = n;
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
            avl.insert(head);
            avl.remove(index);
            return value;
        }
    }

    public static int[] encrypt(int[] text, int n, int m) {
        RearrangingIntList permutation   = new RearrangingIntList(n, m, true);
        int[]              encryptedText = new int[n];

        for (int i = 0; i < n; i++) {
            encryptedText[i] = permutation.findIndexAndMoveToBeginning(text[i]);
        }

        return encryptedText;
    }

    public static int[] decrypt(int[] encryptedText, int n, int m) {
        RearrangingIntList permutation = new RearrangingIntList(n, m, false);

        int[] decryptedText = new int[n];
        for (int i = 0; i < n; i++) {
            decryptedText[i] = permutation.getValueAndMoveToBeginning(encryptedText[i]);
        }

        return decryptedText;
    }

    static class AVLNode {
        int value;
        int priority;

        AVLNode left, right;
        int height;
        int count; // Number of nodes in the subtree rooted at this node, including this node

        AVLNode(int d) {
            value = d;
            height = 1; // New node is initially added at leaf
            count = 1;
        }

        AVLNode(int pr, int vl) {
            value = vl;
            height = 1; // New node is initially added at leaf
            count = 1;
            priority = pr;
        }

    }

    static class AugmentedAVLTree {

        private AVLNode minNode = null;

        int     holder;
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
            if (node == null) {
                return (new AVLNode(value));
            }

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

/*
        public void insert(int value) {
            if (root == null) {
                root = new AVLNode(value);
                minNode = root; // Initialize minNode on first insert
            } else if (value < minNode.value) {
                // New minimum: insert directly to the left of minNode and update reference
                minNode.left = new AVLNode(value);
                // Update the entire path back to root to ensure tree balance
                root = insert(root, value);
                minNode = minNode.left; // Update minNode to new minimum
            } else {
                // Standard AVL insert and rebalance
                root = insert(root, value);
            }
        }
*/

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

        public int removeAtPosition(int position) {
            //int[] valueHolder = new int[1]; // To hold the value of the node to be removed.
            root = removeAtPositionRecursive(root, position);
            int value = holder;
            holder = -1;
            return value; // Return the value of the removed node.
        }

        private AVLNode removeAtPositionRecursive(AVLNode node, int position) {
            if (node == null) {
                return null;
            }

            int leftSize = size(node.left);

            if (position < leftSize) {
                node.left = removeAtPositionRecursive(node.left, position);
            } else if (position > leftSize) {
                // Adjust position for the right subtree
                node.right = removeAtPositionRecursive(node.right, position - leftSize - 1);
            } else {
                // This is the node to be removed
                holder = node.value; // Capture the value before removing
                // Proceed with removal as in the removeRecursive method
                if (node.left == null || node.right == null) {
                    node = (node.left != null) ? node.left : node.right;
                } else {
                    AVLNode temp = minValueNode(node.right);
                    node.value = temp.value;
                    node.right = removeRecursive(node.right, temp.value);
                }
            }

            if (node == null) return null; // In case of leaf deletion

            // Update height and balance the tree as in the removeRecursive method
            node.height = Math.max(height(node.left), height(node.right)) + 1;
            node.count = 1 + size(node.left) + size(node.right);
            return balanceNode(node);
        }

        private AVLNode balanceNode(AVLNode node) {
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
            return node;
        }

    }

    static class AugmentedAVLTreePrioritized {
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

        AVLNode insert(AVLNode node, AVLNode nodeToInsert) {
            if (node == null) return (nodeToInsert);

            int priority = nodeToInsert.priority;

            if (priority < node.priority) node.left = insert(node.left, nodeToInsert);
            else if (priority > node.priority) node.right = insert(node.right, nodeToInsert);
            else return node; // Duplicate values not allowed

            /* 2. Update height and size of this ancestor node */
            node.height = 1 + Math.max(height(node.left), height(node.right));
            node.count = 1 + size(node.left) + size(node.right);

            /**
             *3. Get the balance factor of this ancestor node to check whether
             this node became unbalanced*/

            int balance = getBalance(node);

            // If this node becomes unbalanced, then there are 4 cases

            // Left Left Case
            if (balance > 1 && priority < node.left.priority) return rightRotate(node);

            // Right Right Case
            if (balance < -1 && priority > node.right.priority) return leftRotate(node);

            // Left Right Case
            if (balance > 1 && priority > node.left.priority) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Right Left Case
            if (balance < -1 && priority < node.right.priority) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            /* return the (unchanged) node pointer */
            return node;
        }

        // Recursive function to insert a node with given key in the subtree rooted
        // with node and returns the new root of the subtree.
        AVLNode insert(AVLNode node, int priority, int value) {
            return insert(node, new AVLNode(priority, value));
        }

        // Function to insert a new value with unique value
        public void insert(int priority, int value) {
            root = insert(root, priority, value);
        }

        public void insert(AVLNode node) {
            root = insert(root, node);
        }


        private int countLessThanEqualTo(AVLNode node, int value) {
            if (node == null) {
                return 0;
            }

            if (value < node.priority) {
                // If the value is less than the current node's value,
                // then all elements greater than the value are in the right subtree,
                // which we don't need to consider.
                return countLessThanEqualTo(node.left, value);
            } else if (value > node.priority) {
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
            if (value > node.priority) {
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
            if (value < node.priority) {
                node.left = removeRecursive(node.left, value);
            } else if (value > node.priority) {
                node.right = removeRecursive(node.right, value);
            } else {
                // nodes with only one child or no child
                if (node.left == null || node.right == null) {
                    node = (node.left != null) ? node.left : node.right;
                } else {
                    // node with two children: Get the inorder successor
                    AVLNode temp = minValueNode(node.right);
                    node.priority = temp.priority;
                    node.right = removeRecursive(node.right, temp.priority);
                }
            }

            // If the tree had only one node then return
            if (node == null) return null;

            // Step 2: Update height of the current node
            node.height = Math.max(height(node.left), height(node.right)) + 1;

            // Step 3: Update the count of the current node
            node.count = 1 + size(node.left) + size(node.right);

            // Step 4: Rebalance the node if needed
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
                return node.priority;
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

        // Method to remove a node by its position in the sorted order and return its value.
        public int removeAtPosition(int position) {
            int[] valueHolder = new int[1]; // To hold the value of the node to be removed.
            root = removeAtPositionRecursive(root, position, valueHolder);
            return valueHolder[0]; // Return the value of the removed node.
        }

        private AVLNode removeAtPositionRecursive(AVLNode node, int position, int[] valueHolder) {
            if (node == null) {
                return null;
            }

            int leftSize = size(node.left);

            if (position < leftSize) {
                node.left = removeAtPositionRecursive(node.left, position, valueHolder);
            } else if (position > leftSize) {
                // Adjust position for the right subtree
                node.right = removeAtPositionRecursive(node.right, position - leftSize - 1, valueHolder);
            } else {
                // This is the node to be removed
                valueHolder[0] = node.value; // Capture the value before removing
                // Proceed with removal as in the removeRecursive method
                if (node.left == null || node.right == null) {
                    node = (node.left != null) ? node.left : node.right;
                } else {
                    AVLNode temp = minValueNode(node.right);
                    node.priority = temp.priority;
                    node.value = temp.value;
                    node.right = removeRecursive(node.right, temp.priority);
                }
            }

            if (node == null) return null; // In case of leaf deletion

            // Update height and balance the tree as in the removeRecursive method
            node.height = Math.max(height(node.left), height(node.right)) + 1;
            node.count = 1 + size(node.left) + size(node.right);
            return node;
        }

        private AVLNode balanceNode(AVLNode node) {
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
            return node;
        }

        private int findPositionByValue(AVLNode node, int value, int currentPos) {
            if (node == null) {
                return -1; // Element not found
            }
            if (value < node.value) {
                return findPositionByValue(node.left, value, currentPos);
            } else if (value > node.value) {
                // Add the size of the left subtree + 1 (for the current node) to currentPos
                int leftSize = (node.left != null) ? node.left.count : 0;
                return findPositionByValue(node.right, value, currentPos + leftSize + 1);
            } else {
                // Return the current position plus the size of the left subtree
                int leftSize = (node.left != null) ? node.left.count : 0;
                return currentPos + leftSize; // Node found
            }
        }

        private AVLNode findNodeByValue(AVLNode node, int value) {
            if (node == null) {
                return null; // Node not found
            }
            if (value < node.value) {
                return findNodeByValue(node.left, value);
            } else if (value > node.value) {
                return findNodeByValue(node.right, value);
            } else {
                return node; // Node found
            }
        }

        public int findPositionByValue(int value) {
            return findPositionByValue(root, value, 0);
        }
    }


    static class Node {
        int key;
        int left;
        int right;

        public Node(int left) {
            this.left = left;
        }
    }

    static class Tree {
        Node[] memory;

        int firstFree = 0;

        public Tree(int maxn) {
            memory = new Node[maxn];
            for (int i = 0; i< maxn; i++) {
                memory = new Node[i+1];
            }
        }

        public int newNode() {
            int nextFree = memory[firstFree].left;
            int allocatedIndex = firstFree;
            firstFree = nextFree;
            return allocatedIndex;
        }

        public void delNode(int index) {
            memory[index].left = firstFree;
            firstFree = index;
        }


        public int find(int root, int x) {
            int key = memory[root].key;
            if (x == key) {
                return root;
            } else if (x < key) {
                int left = memory[root].left;
                if (left == -1) {
                    return -1;
                }
                return find(left, x);
            } else {
                int right = memory[root].right;
                if (right == -1) {
                    return -1;
                }
                return find(root, x);
            }
        }
    }
}