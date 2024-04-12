package tests.L5_6.models;

public class TaskAnnotated {
    @MapField(columnName = "Name")
    private String name;
    @MapField(columnName = "Memory")
    private Double memory;

    @MapField(columnName = "CPU")
    private Double cpu;

    @MapField(columnName = "Network")
    private Double network;

    @MapField(columnName = "Disk")
    private Double disk;

    @MapField(columnName = "Temperature")
    private Double temp;


    public  TaskAnnotated(){
    }

    public String getName() {
        return name;
    }

    public TaskAnnotated setName(String name) {
        this.name = name;
        return this;
    }

    public Double getMemory() {
        return memory;
    }

    public TaskAnnotated setMemory(Double memory) {
        this.memory = memory;
        return this;
    }

    public Double getCpu() {
        return cpu;
    }

    public TaskAnnotated setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getNetwork() {
        return network;
    }

    public TaskAnnotated setNetwork(Double network) {
        this.network = network;
        return this;
    }

    public Double getDisk() {
        return disk;
    }

    public TaskAnnotated setDisk(Double disk) {
        this.disk = disk;
        return this;
    }


    public static Double parseCellToDouble(String str){
            return Double.parseDouble(str.split(" ")[0].trim());
        }

    @Override
    public String toString() {
        return "TaskAnnotated{" +
                "name='" + name + '\'' +
                ", memory=" + memory +
                ", cpu=" + cpu +
                ", network=" + network +
                ", disk=" + disk +
                '}';
    }



}
