/* Jaden Simmons
Fast Lanes: Skip List Implementation
COP3503 Computer Science 2
SkipList.java
*/

public class SkipList {

    // Node class for each node
    private class Node {
        int studentID;
        int height;
        Node[] next;

        // Constructors
        Node(int studentID, int height) 
        {
            this.studentID = studentID;
            this.height = height;
            this.next = new Node[height];
        }
        Node(int height) 
        {
            this.height = height;
            this.next = new Node[height];
        }
    }

    private Node head;
    private int size;
    private int height;

    

    // if divisible by 4 then height = 3
    // if divisible by 2 then height = 2
    // otherwise height = 1
    private int getHeight(int studentID) 
    {
        if (studentID % 4 == 0) return 3;
        if (studentID % 2 == 0) return 2;
        return 1;
    }

    // Returns an array of the nodes that come before the target node
    private Node[] getPrevious(int studentID) 
    {
        Node[] previous = new Node[3];
        Node current = head;

        for (int i = height - 1; i >= 0; i--) 
        {
            while (current.next[i] != null && current.next[i].studentID < studentID) 
            {
                current = current.next[i];
            }
            previous[i] = current;
        }

        return previous;
    }
    
    // Initializes the skip list with height = 3
    public SkipList() 
    {
        head = new Node(3);
        size = 0;
        height = 1; // Start with height 1; grows as nodes are inserted
    }

    // Returns true if the studentID is found otherwise false
    public boolean search(int studentID) 
    {
        Node current = head;

        for (int i = height - 1; i >= 0; i--) 
        {
            while (current.next[i] != null && current.next[i].studentID < studentID) 
            {
                current = current.next[i];
            }
        }

        // if found return true
        if (current.next[0] != null && current.next[0].studentID == studentID)
        {
            return true;
        }
        return false;
    }

    // Insert into skip list in sorted order
    public void insert(int studentID) 
    {
        // if the ID is already in the list then do nothing
        if (search(studentID)) return;

        int nodeHeight = getHeight(studentID);

        // expand height if necessarry
        if (nodeHeight > height) 
        {
            height = nodeHeight;
        }

        Node[] previous = getPrevious(studentID);

        // set previous as head
        for (int i = 0; i < nodeHeight; i++) 
        {
            if (previous[i] == null) 
            {
                previous[i] = head;
            }
        }

        Node newNode = new Node(studentID, nodeHeight);
        for (int i = 0; i < nodeHeight; i++) 
        {
            newNode.next[i] = previous[i].next[i];
            previous[i].next[i] = newNode;
        }
        size++;
    }

    // Remove studentID from list
    public void delete(int studentID) 
    {
        // if the ID doesnt exist then do nothing
        if (!search(studentID)) return;

        Node[] previous = getPrevious(studentID);

        // Remove the node from every level it appears in
        for (int i = 0; i < height; i++) 
        {
            if (previous[i] != null && previous[i].next[i] != null && previous[i].next[i].studentID == studentID) 
            {
                previous[i].next[i] = previous[i].next[i].next[i];
            }
        }

        size--;

        // shrink height if levels are empty
        while (height > 1 && head.next[height - 1] == null) 
        {
            height--;
        }
    }

    // Returns the total number of student IDs
    public int size() 
    {
        return size;
    }

    // Returns the current maximum height
    public int height() 
    {
        return height;
    }
}