/* Jaden Simmons
Fast Lanes: Skip List Implementation
COP3503 Computer Science 2
SkipList.java
*/

public class SkipList {

    // Inner Node class to represent each element in the skip list
    private class Node {
        int studentID;
        int height;
        Node[] next; // next[0] = level 1, next[1] = level 2, next[2] = level 3

        // Constructor for regular nodes with a student ID
        Node(int studentID, int height) {
            this.studentID = studentID;
            this.height = height;
            this.next = new Node[height];
        }

        // Constructor for the head sentinel node (no student ID needed)
        Node(int height) {
            this.height = height;
            this.next = new Node[height];
        }
    }

    private Node head;   // Sentinel head node spanning all 3 levels
    private int size;    // Number of student IDs currently stored
    private int height;  // Current highest level in use

    // Initializes the skip list with a head node of height 3
    public SkipList() {
        head = new Node(3);
        size = 0;
        height = 1; // Start with height 1; grows as nodes are inserted
    }

    // Determines a node's height using the deterministic rule:
    // divisible by 4 -> height 3, divisible by 2 -> height 2, otherwise -> height 1
    private int getHeight(int studentID) {
        if (studentID % 4 == 0) return 3;
        if (studentID % 2 == 0) return 2;
        return 1;
    }

    // Finds the predecessor node at each level just before the target studentID.
    // Returns an array of predecessor nodes indexed by level (0 = bottom level).
    private Node[] getPredecessors(int studentID) {
        Node[] predecessors = new Node[3];
        Node current = head;

        // Traverse from the current top level down to level 0
        for (int i = height - 1; i >= 0; i--) {
            // Move forward while the next node exists and is smaller than target
            while (current.next[i] != null && current.next[i].studentID < studentID) {
                current = current.next[i];
            }
            predecessors[i] = current; // Record predecessor at this level
        }

        return predecessors;
    }

    // Returns true if the studentID exists in the skip list, false otherwise
    public boolean search(int studentID) {
        Node current = head;

        // Traverse top-down through all levels
        for (int i = height - 1; i >= 0; i--) {
            // Advance forward while next node is smaller than target
            while (current.next[i] != null && current.next[i].studentID < studentID) {
                current = current.next[i];
            }
        }

        // After traversal, check if the next node at level 0 is the target
        return current.next[0] != null && current.next[0].studentID == studentID;
    }

    // Inserts studentID into the skip list in sorted order (ignored if already present)
    public void insert(int studentID) {
        // Do nothing if the student ID already exists
        if (search(studentID)) return;

        int nodeHeight = getHeight(studentID);

        // If the new node is taller than the current list height, expand height
        // and set head as the predecessor for those new levels
        if (nodeHeight > height) {
            height = nodeHeight;
        }

        // Find predecessors at each level before inserting
        Node[] predecessors = getPredecessors(studentID);

        // For levels that weren't reached by getPredecessors, use head as predecessor
        for (int i = 0; i < nodeHeight; i++) {
            if (predecessors[i] == null) {
                predecessors[i] = head;
            }
        }

        // Create the new node and link it in at each of its levels
        Node newNode = new Node(studentID, nodeHeight);
        for (int i = 0; i < nodeHeight; i++) {
            newNode.next[i] = predecessors[i].next[i];
            predecessors[i].next[i] = newNode;
        }

        size++;
    }

    // Removes studentID from the skip list at all levels it appears (does nothing if not found)
    public void delete(int studentID) {
        // Do nothing if the student ID does not exist
        if (!search(studentID)) return;

        // Find predecessors at each level so we can re-link around the deleted node
        Node[] predecessors = getPredecessors(studentID);

        // Remove the node from every level it appears in
        for (int i = 0; i < height; i++) {
            if (predecessors[i] != null &&
                predecessors[i].next[i] != null &&
                predecessors[i].next[i].studentID == studentID) {
                predecessors[i].next[i] = predecessors[i].next[i].next[i];
            }
        }

        size--;
    }

    // Returns the total number of student IDs currently stored
    public int size() {
        return size;
    }

    // Returns the current maximum height of the skip list
    public int height() {
        return height;
    }
}