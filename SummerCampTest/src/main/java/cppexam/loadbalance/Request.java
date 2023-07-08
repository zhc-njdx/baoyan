package cppexam.loadbalance;

public class Request {
    String tag;
    Server server;

    public Request(String tag) {
        this.tag = tag;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return tag + " " + server.id;
    }
}
