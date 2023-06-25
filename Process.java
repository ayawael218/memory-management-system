public class Process {
    int id, size;

    Process Clone(int id, int size) {
        return new Process(id, size);
    }
    public Process(int id, int size) {
        this.size = size;
        this.id = id;
    }
}
