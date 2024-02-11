import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * MTF (Move-To-Front) encryption implemented based on AVL Tree.
 * AVL Tree is implemented based on custom memory manager.
 *
 * <p>
 * Time: O(n*log(m))
 * Memory: O(n + m)
 */
public class AVLTreeCustomMemoryManagerEncDec {

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

        int priority;

        int[] permutation;

        int m;

        int n;

        int head;

        int tail;

        Map<Integer, Integer> valueToIndex = new HashMap<>();

        AugmentedAVLTree avl;

        public RearrangingIntList(int n, int m, boolean encryption) {
            if (!encryption) {
                priority = n;
                permutation = new int[n + m];
                avl = new AugmentedAVLTree(m);
            } else {
                valueToIndex = new HashMap<>(m);
                avl = new AugmentedAVLTree(m + n);
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

            int index = avl.removeAtPosition(pos - 1);
            int value = permutation[index];

            head = head - 1;
            permutation[head] = value;
            //avl.remove(index);
            avl.insert(head);
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
        int leftIndex;
        int rightIndex; // Indices to left and right children in the CustomMemoryManager
        int height;
        int count;

        // Constructor should initialize indices to -1 to indicate 'null' pointers
        AVLNode(int value) {
            this.value = value;
            this.height = 1;
            this.count = 1;
            this.leftIndex = -1;
            this.rightIndex = -1;
        }

        AVLNode(int leftIndex, int value) {
            this.value = value;
            this.height = 1;
            this.count = 1;
            this.leftIndex = leftIndex;
            this.rightIndex = -1;
        }

    }

    static class CustomMemoryManager {
        private final AVLNode[] memory;
        private int       freeListHead; // Points to the first free index

        public CustomMemoryManager(int capacity) {
            memory = new AVLNode[capacity];
            for (int i = 0; i < capacity; i++) {
                memory[i] = new AVLNode(i + 1, -1);
            }
            freeListHead = 0; // The first index is initially free
        }

        public int allocateNode() {
            if (freeListHead == -1) {
                throw new RuntimeException("Out of memory");
            }
            int     nextFree       = memory[freeListHead].leftIndex;
            int     allocatedIndex = freeListHead;
            freeListHead = nextFree;
            return allocatedIndex;
        }

        public void deallocateNode(int index) {
            if (index < 0 || index >= memory.length) {
                throw new IllegalArgumentException("Invalid index");
            }
            AVLNode node = memory[index];
            node.value = -1; // Clear the node
            node.count = 0;
            node.height = 0;
            node.leftIndex = freeListHead;
            freeListHead = index; // Update the head to this index
        }

        public AVLNode getNode(int index) {
            if (index < 0 || index >= memory.length || memory[index] == null) {
                throw new IllegalArgumentException("Invalid index or node does not exist");
            }
            return memory[index];
        }

        public void setNode(int index, AVLNode node) {
            if (index < 0 || index >= memory.length) {
                throw new IllegalArgumentException("Invalid index");
            }
            memory[index] = node;
        }
    }

    public static class AugmentedAVLTree {
        private final CustomMemoryManager memoryManager;
        private       int                 rootIndex = -1; // -1 indicates no node

        public AugmentedAVLTree(int capacity) {
            memoryManager = new CustomMemoryManager(capacity);
        }

        private int createAndFillNode(int value) {
            int index = memoryManager.allocateNode();
            AVLNode newNode = memoryManager.getNode(index);
            newNode.value = value;
            newNode.leftIndex = -1;
            newNode.rightIndex = -1;
            newNode.height = 1;
            newNode.count = 1;
            return index;
        }

        private int height(int nodeIndex) {
            if (nodeIndex == -1) return 0;
            return memoryManager.getNode(nodeIndex).height;
        }

        private int size(int nodeIndex) {
            if (nodeIndex == -1) return 0;
            return memoryManager.getNode(nodeIndex).count;
        }

        private int getBalance(int nodeIndex) {
            if (nodeIndex == -1) return 0;
            AVLNode node = memoryManager.getNode(nodeIndex);
            return height(node.leftIndex) - height(node.rightIndex);
        }

        private void updateNodeHeightAndSize(int nodeIndex) {
            if (nodeIndex == -1) return;
            AVLNode node = memoryManager.getNode(nodeIndex);
            node.height = 1 + Math.max(height(node.leftIndex), height(node.rightIndex));
            node.count = 1 + size(node.leftIndex) + size(node.rightIndex);
            //memoryManager.setNode(nodeIndex, node); // Save the changes back to the memory manager
        }

        public void insert(int value) {
            rootIndex = insert(rootIndex, value);
        }

        private int insert(int nodeIndex, int value) {
            if (nodeIndex == -1) {
                return createAndFillNode(value); // Allocate a new node
            }

            AVLNode node = memoryManager.getNode(nodeIndex);
            if (value < node.value) {
                node.leftIndex = insert(node.leftIndex, value);
            } else if (value > node.value) {
                node.rightIndex = insert(node.rightIndex, value);
            } else {
                return nodeIndex; // Duplicate values not allowed
            }

            updateNodeHeightAndSize(nodeIndex); // Update height and size

            return balanceNode(nodeIndex);
        }

        public int remove(int value) {
            int[] index = new int[1];
            rootIndex = remove(rootIndex, value, index);
            return index[0];
        }

        private int remove(int nodeIndex, int value, int[] index) {
            if (nodeIndex == -1) {
                return -1; // Value not found
            }

            AVLNode node = memoryManager.getNode(nodeIndex);
            if (value < node.value) {
                node.leftIndex = remove(node.leftIndex, value, index);
            } else if (value > node.value) {
                node.rightIndex = remove(node.rightIndex, value, index);
            } else {
                index[0] = node.count;
                // Node with only one child or no child
                if (node.leftIndex == -1 || node.rightIndex == -1) {
                    //nodeIndex = node.leftIndex != -1 ? node.leftIndex : node.rightIndex;

                    int tempIndex = node.leftIndex != -1 ? node.leftIndex : node.rightIndex;
                    memoryManager.deallocateNode(nodeIndex);
                    nodeIndex =  tempIndex;
/*
                    if (tempIndex == -1) { // No child
                        tempIndex = nodeIndex;
                        nodeIndex = -1; // This node will be deleted
                        memoryManager.deallocateNode(tempIndex);
                    } else { // One child
                        memoryManager.deallocateNode(nodeIndex);
                        nodeIndex = tempIndex;
                        //memoryManager.getNode(nodeIndex);
                    }
*/
                    //memoryManager.deallocateNode(tempIndex); // Deallocate the node
                    //memoryManager.deallocateNode(tempIndex); // Deallocate the node

                } else {
                    // Node with two children: Get the inorder successor
                    int     tempIndex = minValueNode(node.rightIndex);
                    AVLNode temp      = memoryManager.getNode(tempIndex);
                    node.value = temp.value;
                    node.rightIndex = remove(node.rightIndex, temp.value, index);
                }
            }

            if (nodeIndex == -1) return -1; // In case the tree had only one node

            // Update height and balance the tree
            updateNodeHeightAndSize(nodeIndex);
            return nodeIndex;
        }

        // Finds the minimum value node's index starting from a given node index
        private int minValueNode(int nodeIndex) {
            int current = nodeIndex;
            while (current != -1) {
                AVLNode node = memoryManager.getNode(current);
                if (node.leftIndex == -1) break;
                current = node.leftIndex;
            }
            return current;
        }

        private int countLessThanEqualTo(int nodeIndex, int value) {
            if (nodeIndex == -1) {
                return 0;
            }

            AVLNode node = memoryManager.getNode(nodeIndex);

            if (value < node.value) {
                // If the value is less than the current node's value,
                // then all elements greater than the value are in the right subtree,
                // which we don't need to consider.
                return countLessThanEqualTo(node.leftIndex, value);
            } else if (value > node.value) {
                // If the value is greater than the current node's value,
                // include the current node, all of the left subtree (as all these values are less),
                // and then recurse on the right subtree.
                return size(node.leftIndex) + 1 + countLessThanEqualTo(node.rightIndex, value);
            } else {
                // If the value is equal to the current node's value,
                // include the current node and all of the left subtree.
                return size(node.leftIndex) + 1;
            }
        }


        // Public wrapper method to call the private recursive method
        public int countElementsNotMoreThan(int value) {
            return countLessThanEqualTo(rootIndex, value);
        }


        // Right rotate the subtree rooted with yIndex
        private int rightRotate(int yIndex) {
            AVLNode y      = memoryManager.getNode(yIndex);
            int     xIndex = y.leftIndex;
            AVLNode x      = memoryManager.getNode(xIndex);

            // Perform rotation
            y.leftIndex = x.rightIndex;
            x.rightIndex = yIndex;

            // Update heights and sizes
            updateNodeHeightAndSize(yIndex);
            updateNodeHeightAndSize(xIndex);

            //memoryManager.setNode(yIndex, y);
            //memoryManager.setNode(xIndex, x);

            return xIndex; // New root after rotation
        }

        // Left rotate the subtree rooted with xIndex
        private int leftRotate(int xIndex) {
            AVLNode x      = memoryManager.getNode(xIndex);
            int     yIndex = x.rightIndex;
            AVLNode y      = memoryManager.getNode(yIndex);

            // Perform rotation
            x.rightIndex = y.leftIndex;
            y.leftIndex = xIndex;

            // Update heights and sizes
            updateNodeHeightAndSize(xIndex);
            updateNodeHeightAndSize(yIndex);

            //memoryManager.setNode(xIndex, x);
            //memoryManager.setNode(yIndex, y);

            return yIndex; // New root after rotation
        }

        // Rebalance the node at nodeIndex if needed
        private int balanceNode(int nodeIndex) {
            if (nodeIndex == -1) return -1;
            AVLNode node = memoryManager.getNode(nodeIndex);

            int balance = getBalance(nodeIndex);

            // Left Left Case
            if (balance > 1 && getBalance(node.leftIndex) >= 0) {
                return rightRotate(nodeIndex);
            }

            // Left Right Case
            if (balance > 1 && getBalance(node.leftIndex) < 0) {
                node.leftIndex = leftRotate(node.leftIndex);
                return rightRotate(nodeIndex);
            }

            // Right Right Case
            if (balance < -1 && getBalance(node.rightIndex) <= 0) {
                return leftRotate(nodeIndex);
            }

            // Right Left Case
            if (balance < -1 && getBalance(node.rightIndex) > 0) {
                node.rightIndex = rightRotate(node.rightIndex);
                return leftRotate(nodeIndex);
            }

            return nodeIndex; // Return the unchanged node index if no rotation was done
        }

        // Method to find an element by its position in sorted order
        public int findElementByPosition(int position) {
            return findElementByPositionRecursive(rootIndex, position + 1); // Adjust for 1-based indexing
        }

        private int findElementByPositionRecursive(int nodeIndex, int targetPos) {
            if (nodeIndex == -1) return -1; // Element not found or error

            AVLNode node            = memoryManager.getNode(nodeIndex);
            int     leftSubtreeSize = size(node.leftIndex);

            if (targetPos <= leftSubtreeSize) {
                return findElementByPositionRecursive(node.leftIndex, targetPos);
            } else if (targetPos > leftSubtreeSize + 1) {
                return findElementByPositionRecursive(node.rightIndex, targetPos - leftSubtreeSize - 1);
            }

            return node.value; // The target position matches the current node
        }

        // Method to remove a node by its position in the sorted order
/*
        public void removeAtPosition(int position) {
            rootIndex = removeAtPositionRecursive(rootIndex, position);
        }
*/

        private int removeAtPositionRecursive(int nodeIndex, int position, int[] currentPosition) {
            if (nodeIndex == -1) {
                return -1; // Base case: node not found
            }

            AVLNode node        = memoryManager.getNode(nodeIndex);
            int     leftSize    = size(node.leftIndex);

            if (position < leftSize) {
                // The node to be removed is in the left subtree
                node.leftIndex = removeAtPositionRecursive(node.leftIndex, position, currentPosition);
            } else if (position > leftSize) {
                // The node to be removed is in the right subtree
                node.rightIndex = removeAtPositionRecursive(node.rightIndex, position - leftSize - 1, currentPosition);
            } else {
                currentPosition[0] = node.value;
                // This is the node to be removed
                if (node.leftIndex == -1 || node.rightIndex == -1) {
                    // Node with only one child or no child
                    int tempIndex = (node.leftIndex != -1) ? node.leftIndex : node.rightIndex;
                    memoryManager.deallocateNode(nodeIndex); // Free the node
                    return tempIndex; // Return the non-null child or -1
                } else {
                    // Node with two children
                    int     successorIndex = minValueNode(node.rightIndex);
                    AVLNode successor      = memoryManager.getNode(successorIndex);
                    node.value = successor.value; // Copy successor's value
                    node.rightIndex = remove(node.rightIndex, successor.value, new int[1]);
                }
            }

            if (nodeIndex == -1) return -1; // If the tree had only one node then return

            // Update height and balance the node
            updateNodeHeightAndSize(nodeIndex);
            //return balanceNode(nodeIndex); // Rebalance the node
            return nodeIndex;
        }

        public int removeAtPosition(int position) {
            int[] currentPosition = new int[]{0};
            rootIndex = removeAtPositionRecursive(rootIndex, position, currentPosition);
            return currentPosition[0];
        }
    }
}