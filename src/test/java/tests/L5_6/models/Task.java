package tests.L5_6.models;

import java.util.Objects;

public class Task {


    private String name;

    private Double memory;

    private Double cpu;

    private Double network;

    private Double disk;

    public static Double parseCellToDouble(String str){
        return Double.parseDouble(str.split(" ")[0].trim());
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public Double getMemory() {
        return memory;
    }

    public Task setMemory(Double memory) {
        this.memory = memory;
        return this;
    }

    public Double getCpu() {
        return cpu;
    }

    public Task setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getNetwork() {
        return network;
    }

    public Task setNetwork(Double network) {
        this.network = network;
        return this;
    }

    public Double getDisk() {
        return disk;
    }

    public Task setDisk(Double disk) {
        this.disk = disk;
        return this;
    }

    public Task(){
    }


    public Task(String name, Double memory, Double cpu, Double network, Double disk) {
        this.name = name;
        this.memory = memory;
        this.cpu = cpu;
        this.network = network;
        this.disk = disk;
    }

    @Override
    public String toString() {
        return "Models{" +
                "name='" + name + '\'' +
                ", memory=" + memory +
                ", cpu=" + cpu +
                ", network=" + network +
                ", disk=" + disk +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(memory, task.memory) && Objects.equals(cpu, task.cpu) && Objects.equals(network, task.network) && Objects.equals(disk, task.disk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, memory, cpu, network, disk);
    }
}
