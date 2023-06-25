public class Partition {
    int size, id;
    Process process;

    Partition Clone(int id, int size) {
        return new Partition(id, size);
    }

    public Partition(int id, int size) {
        this.size = size;
        this.id = id;
        process = null;
    }

    public Partition addProcess(Process process, int last) {
        this.process = process;
        int newSize = this.size - process.size;
        this.size = process.size;
        return new Partition(last + 1, newSize);
    }

    public void resize(int size) {
        this.size += size;
    }
}
