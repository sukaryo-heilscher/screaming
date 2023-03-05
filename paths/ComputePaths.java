package paths;


public class ComputePaths {


    public void compute(int graph_size, int num_threads) {
      Thread[] threads = new Thread[10];
      int min = 0;
      int div = 2520 / num_threads;
      for (short i = 0; i < num_threads; i++) {
        threads[i] = new Thread(new Worker(graph_size, min, min + div));
        threads[i].start();
        min += div;
      }
      for (short i = 0; i < num_threads; i++) {
        try {
          threads[i].join();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: java ComputePaths <graph size> <# threads>");
            System.exit(1);
        }

        int graph_size = 0;
        int num_threads = 0;
        try {
            graph_size = Integer.parseInt(args[0]);
            num_threads = Integer.parseInt(args[1]);
            if ((graph_size <= 0) || (num_threads < 1)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid command-line arguments");
            System.exit(1);
        }

        double now = System.currentTimeMillis();

        new ComputePaths().compute(graph_size, num_threads);

        System.out.println("All graphs computed in " + (System.currentTimeMillis() - now) / 1000 + " seconds");

    }

    private class Worker implements Runnable {
      private int graph_size, min, max;
      
      Worker(int graph_size, int min, int max) {
        this.graph_size = graph_size;
        this.min = min;
        this.max = max;
      }
      
      @Override
      public void run() {
        for (long i = min; i < max; i++) {
          new FloydWarshall(graph_size, i).floydWarshall();
        }
      }
    }
}
