import java.util.*;
import java.util.concurrent.*;

class Task {
    String name;
    int difficulty;   // 1-10
    int urgency;      // 1-10
    int estimatedTime; // in minutes
    double priorityScore;

    public Task(String name, int difficulty, int urgency, int estimatedTime) {
        this.name = name;
        this.difficulty = difficulty;
        this.urgency = urgency;
        this.estimatedTime = estimatedTime;
        calculatePriority(100); // default energy
    }

    public void calculatePriority(int energyLevel) {
        // Quantum-inspired dynamic scoring formula
        this.priorityScore = (urgency * 2.5 + difficulty * 1.5)
                * (energyLevel / 100.0)
                / Math.log(estimatedTime + 2);
    }

    @Override
    public String toString() {
        return name + " | Score: " + String.format("%.2f", priorityScore);
    }
}

public class QuantumFocusScheduler {

    private static int energyLevel = 100;

    public static void main(String[] args) {

        PriorityQueue<Task> taskQueue = new PriorityQueue<>(
                Comparator.comparingDouble((Task t) -> t.priorityScore).reversed()
        );

        List<Task> tasks = Arrays.asList(
                new Task("Build Blockchain Module", 9, 8, 120),
                new Task("DSA Practice", 7, 9, 60),
                new Task("Hackathon UI Design", 6, 7, 90),
                new Task("System Design Reading", 8, 6, 80)
        );

        // Dynamic recalculation based on energy
        Runnable energyDrainer = () -> {
            try {
                while (energyLevel > 0) {
                    Thread.sleep(3000);
                    energyLevel -= 5;
                    System.out.println("⚡ Energy Level: " + energyLevel + "%");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(energyDrainer).start();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            taskQueue.clear();

            for (Task t : tasks) {
                t.calculatePriority(energyLevel);
                taskQueue.add(t);
            }

            System.out.println("\n🔥 Optimized Task Order:");
            taskQueue.forEach(System.out::println);

        }, 0, 5, TimeUnit.SECONDS);
    }
}
