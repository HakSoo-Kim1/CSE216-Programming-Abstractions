import javafx.concurrent.Task;

import java.util.*;
import java.util.Set;

public class ActionTester {
    public static class Subject {
        String s;
        public Subject (String s) { this.s = s;}
        public void print(){System.out.println(s);
        }

    }
    public static Set<Task> tasks = new HashSet<>();

    public static class Task extends Thread {
        public void run() {
        }
    }
    public static void init (Task task) {
        task.start();
        tasks.add(task);
    }

    public static boolean process(){
        boolean processing = false;
        for (Task task : tasks) { if (task.isAlive()) processing = true;}
        return processing;

    }

    public static void main(String... args) {
        List <Subject> values = new ArrayList<>();
        for ( int i = 1; i<15; i++)
            values.add(new Subject (String.valueOf(i)));
        int numberOfObjectsPerThread = 6;
        int cursor = 0;

        List<Subject> subjectList = new ArrayList<>();
        for (Subject v : values) {
            cursor++;
            subjectList.add(v);

            if (cursor < numberOfObjectsPerThread) continue;

            final List<Subject> subjectList2 = new ArrayList<> (subjectList);
            Task task = new Task() {
                public void run() {
                    Thread mainThread = Thread.currentThread();  // 현재 쓰레드 얻기
                    System.out.println(mainThread);
                    for (Subject o : subjectList2) o.print();
                }
            };
            init(task);
            subjectList.clear();
            cursor = 0;
        }
        while (process()) { continue;}
        System.out.println("Done.");
    }
}