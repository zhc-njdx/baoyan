package cppexam.blacksmith;

public class Weapon {
    int oid; // 订单的 id，保证 id 不相同，且为正整数
    int priority; // 订单优先级，保证所有的兵器订单优先级均不相同，且为正整数
    int time; // 订单完成需要的时间单位，为正整数
    int type; // 订单的类型，订单只能被与自身type 一致的铁匠处理
    String state; // 订单状态
    int need; // 还需要多少时间完成订单

    public static final String DONE = "done";
    public static final String DOING = "doing";
    public static final String PENDING = "pending";
    public static final String DISCARD = "discard";
    public static final String FORMAT = "order %d %s";

    public Weapon(int oid, int priority, int time, int type) {
        this.oid = oid;
        this.priority = priority;
        this.time = time;
        this.type = type;
        this.state = PENDING;
        this.need = time;
    }

    public String getCurrentState() {
        return String.format(FORMAT, oid, state);
    }
}
