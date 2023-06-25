
import java.util.*;

public class Main {
    public static <T> T getInput(boolean type) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of ");
        System.out.println((type) ? "Partitions" : "Processes");
        int n = sc.nextInt();
        List<T> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.print((type) ? "Enter partition #" : "Enter processes #");
            System.out.println(i + "'s size: ");
            int size = sc.nextInt();
            list.add((T) (type ? new Partition(i, size) : new Process(i, size)));
        }
        return (T) (new Vector<>(list));
    }

    public static void main(String[] args) {
        Vector<Partition> partitions = getInput(true);
        Vector<Process> processes = getInput(false);
        Policy p = new Policy(processes, partitions);
        Policy firstFit = p.Clone(p.processes, p.partitions);
        Policy worstFit = p.Clone(p.processes, p.partitions);
        Policy bestFit = p.Clone(p.processes, p.partitions);
        firstFit.firstFit();
        worstFit.worstFit();
        bestFit.bestFit();
    }
}
/*
 6 90 20 5 30 120 80
 4 15 90 30 100
 */
