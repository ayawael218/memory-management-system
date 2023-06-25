
import java.util.*;

public class Policy {
    Queue<Process> processes, notAllocated;
    Vector<Partition> partitions;
    private int last;

    Policy Clone(Queue<Process> processes, Vector<Partition> partitions) {
        Vector<Process> temp = new Vector<>();
        Vector<Partition> temp2 = new Vector<>();
        for (Process p : processes)
            temp.add(p.Clone(p.id, p.size));
        for (Partition p : partitions)
            temp2.add(p.Clone(p.id, p.size));
        return new Policy(temp, temp2);
    }

    public Policy(Vector<Process> processes, Vector<Partition> partitions) {
        this.processes = new ArrayDeque<>();
        this.processes.addAll(processes);
        this.partitions = partitions;
        notAllocated = new ArrayDeque<>();
        last = partitions.size();
    }

    public void firstFit() {
        this.allocate("FIRST");
        this.print("FIRST FIT");
        this.compact("FIRST FIT");
    }

    public void bestFit() {
        this.allocate("BEST");
        this.print("BEST FIT");
        this.compact("BEST FIT");
    }

    public void worstFit() {
        this.allocate("WORST");
        this.print("WORST FIT");
        this.compact("WORST FIT");
    }

    public void print(String type) {
        System.out.println("\n*** " + type + " ***\n");
        for (Partition p : partitions) {
            System.out.print("Partition " + (p.id - 1) + " (" + p.size + " KB) => ");
            System.out.println((p.process == null) ? "External Fragment" : "Process " + p.process.id);
        }
        System.out.println();
        while (!notAllocated.isEmpty()) {
            Process p = notAllocated.poll();
            System.out.println("Process " + p.id + " can not be allocated");
            processes.add(p);
        }
    }

    private void compact(String type) {
        Partition externalFrag = new Partition(++last, 0);
        for (int i = 0; i < partitions.size(); i++) {
            if (partitions.get(i).process == null) {
                externalFrag.resize(partitions.get(i).size);
                partitions.remove(i--);
            }
        }
        if (externalFrag.size != 0) partitions.add(externalFrag);
        // we may need recursion here
        this.allocate(type);
        type += " WITH COMPACTION";
        this.print(type);
    }

    private void allocate(String type) {
        while (!processes.isEmpty()) {
            Process p = processes.poll();
            int best = -1, worst = -1, first = -1, mn = Integer.MAX_VALUE, mx = Integer.MIN_VALUE;
            for (int i = 0; i < partitions.size(); i++) {
                if (partitions.get(i).process == null && partitions.get(i).size >= p.size) {
                    if (mn > partitions.get(i).size) {
                        mn = partitions.get(i).size;
                        best = i;
                    }
                    if (mx < partitions.get(i).size) {
                        mx = partitions.get(i).size;
                        worst = i;
                    }
                    if (first == -1)
                        first = i;
                }
            }
            int idx;
            if (Objects.equals(type, "WORST"))
                idx = worst;
            else if (Objects.equals(type, "BEST"))
                idx = best;
            else idx = first;
            if (idx == -1) {
                notAllocated.add(p);
                continue;
            }
            Partition externalFrag = partitions.get(idx).addProcess(p, last);
            if (externalFrag.size > 0) {
                partitions.insertElementAt(externalFrag, idx + 1);
                last++;
            }
        }
    }
}
