package cppexam.loadbalance;

public class Server {
    String id;
    int weight;
    int need;

    public Server(String id) {
        this.id = id;
    }

    public Server(String id, int weight) {
        this.id = id;
        this.weight = weight;
        this.need = weight;
    }
}
