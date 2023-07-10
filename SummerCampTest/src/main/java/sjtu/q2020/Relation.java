package sjtu.q2020;

public class Relation implements Comparable<Relation> {
    String bigIdUser;
    String smallIdUser;
    int value;
    public Relation(String a, String b, int value) {
        bigIdUser = a.compareTo(b) > 0 ? a : b;
        smallIdUser = a.compareTo(b) < 0 ? a : b;
        this.value = value;
    }

    public String findAnotherUser(String user) {
        if (smallIdUser.equals(user)) return bigIdUser;
        return smallIdUser;
    }
    @Override
    public int compareTo(Relation r) {
        if (value != r.value) return value - r.value;
        if (!smallIdUser.equals(r.smallIdUser)) return r.smallIdUser.compareTo(smallIdUser);
        return r.bigIdUser.compareTo(bigIdUser);
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", smallIdUser, bigIdUser, value);
    }
}
