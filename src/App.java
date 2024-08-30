import auxiliaries.queues.QuqueSimulation;
import auxiliaries.queues.DataWrapper;

import auxiliaries.Configs;

public class App {
    public static void main(String[] args) {
        QuqueSimulation queue = new QuqueSimulation();
        DataWrapper data;

        queue.run(Configs.ITERATIONS, Configs.FIRST_ARRIVAL);
        data = queue.getData();

        data.printInfo();
    }
}
