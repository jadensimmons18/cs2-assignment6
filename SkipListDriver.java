/*  INSERT STUDENT NAME
    UCF Student ID Skip List
    COP3503 Computer Science 2
    SkipListDriver.java
    Compile: javac SkipList.java SkipListDriver.java
    Run: java SkipListDriver [CASE]
*/

import java.util.Arrays;

public class SkipListDriver
{
    public static void main(String args[])
    {
        int testCase = Integer.parseInt(args[0]);

        switch (testCase)
        {
            case 1:
            {
                int[] insertIDs = {1023456, 1456789, 1765432, 1987654, 1543210};
                int[] searchIDs = {1543210, 1111111, 1456789, 2000000};
                int[] deleteIDs = {1543210, 9999999, 1456789};

                runAndPrint("Case 1: ", insertIDs, searchIDs, deleteIDs);
                break;
            }

            case 2:
            {
                int[] insertIDs = {
                    1200004, 1200008, 1200012, 1200016, 1200003,
                    1200005, 1200011, 1200020, 1200001, 1200002
                };
                int[] searchIDs = {1200001, 1200012, 1200015, 1200020, 1300000};
                int[] deleteIDs = {1200004, 1200016, 5555555, 1200001};

                runAndPrint("Case 2: ", insertIDs, searchIDs, deleteIDs);
                break;
            }

            case 3:
            {
                int[] insertIDs = buildStudentIDs(20, 2000000);
                int[] searchIDs = {2000001, 2000005, 2000010, 2000020, 2000030};
                int[] deleteIDs = {2000002, 2000004, 2000006, 2000008, 2000010};

                runAndPrint("Case 3: ", insertIDs, searchIDs, deleteIDs);
                break;
            }

            case 4:
            {
                int[] insertIDs = {
                    3141593, 2718281, 1618033, 1414213, 1732051,
                    2236069, 2449489, 2645751, 3162277, 3464101, 4000000
                };
                int[] searchIDs = {4000000};
                int[] deleteIDs = {4000000};

                runAndPrint("Case 4: ", insertIDs, searchIDs, deleteIDs);
                break;
            }

            case 5:
            {
                int[] insertIDs = {
                    4001024, 4002048, 4003072, 4004096, 4005120,
                    4001001, 4001003, 4001005, 4001007, 4001009,
                    4002002, 4002006, 4002010, 4002014, 4002018
                };

                int[] searchIDs = {
                    4001024, 4001005, 4002018, 4003000, 4004096, 1234567
                };

                int[] deleteIDs = {
                    4002048, 4001001, 4002010, 7000000, 4005120
                };

                runAndPrint("Case 5: ", insertIDs, searchIDs, deleteIDs);
                break;
            }

            default:
                System.out.println("Invalid Test Case...");
        }
    }

    private static int[] buildStudentIDs(int n, int base)
    {
        int[] ids = new int[n];

        for (int i = 0; i < n; i++)
            ids[i] = base + i + 1;

        return ids;
    }

    private static void runAndPrint(String label, int[] insertIDs, int[] searchIDs, int[] deleteIDs)
    {
        SkipList list = new SkipList();

        System.out.println(label);
        System.out.println();

        System.out.println("Student IDs to Insert:");
        System.out.println(Arrays.toString(insertIDs));
        System.out.println();

        for (int id : insertIDs)
            list.insert(id);

        System.out.println("Size after insertions: " + list.size());
        System.out.println("Height after insertions: " + list.height());
        System.out.println();

        System.out.println("Search Results Before Deletions:");
        printSearchResults(list, searchIDs);
        System.out.println();

        System.out.println("Student IDs to Delete:");
        System.out.println(Arrays.toString(deleteIDs));
        System.out.println();

        for (int id : deleteIDs)
            list.delete(id);

        System.out.println("Size after deletions: " + list.size());
        System.out.println("Height after deletions: " + list.height());
        System.out.println();

        System.out.println("Search Results After Deletions:");
        printSearchResults(list, searchIDs);
        System.out.println();
    }

    private static void printSearchResults(SkipList list, int[] studentIDs)
    {
        boolean[] results = new boolean[studentIDs.length];

        for (int i = 0; i < studentIDs.length; i++)
            results[i] = list.search(studentIDs[i]);

        System.out.println("Student IDs to Search:");
        System.out.println(Arrays.toString(studentIDs));

        System.out.println("Found:");
        System.out.println(Arrays.toString(results));
    }
}