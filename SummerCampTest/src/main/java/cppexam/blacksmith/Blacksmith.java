package cppexam.blacksmith;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Blacksmith {
    int uid; // 铁匠的 id，保证 id 不相同，且为正整数
    int type; // 铁匠本身的类型，每个铁匠只能处理与自身type 一致的兵器订单
    Weapon doing; // 正在做的订单
    List<Weapon> history; // 该铁匠已完成的订单列表

    public static final String IDLE_FORMAT = "worker %d resting";
    public static final String DOING_FORMAT = "worker %d doing order %d";

    public Blacksmith(int uid, int type) {
        this.uid = uid;
        this.type = type;
        doing = null;
        history = new ArrayList<>();
    }

    public String getOrderHistory() {
        StringBuilder sb = new StringBuilder();
        for (Weapon weapon : history) {
            sb.append(weapon.oid).append(" ");
        }
        return sb.toString();
    }

    public String getCurrentState() {
        if (doing == null) {
            return String.format(IDLE_FORMAT, uid);
        }
        return String.format(DOING_FORMAT, uid, doing.oid);
    }

    public boolean isIdle() {
        return doing == null;
    }

    public void selectOrder(List<Weapon> orders) {
        doing = orders.stream()
                .filter(o -> o.type == this.type && o.state.equals(Weapon.PENDING)) // type匹配且处于PENDING
                .max(Comparator.comparingInt(o -> o.priority)) // 找到优先级最大的订单
                .orElse(null); // 返回
        if (doing != null) doing.state = Weapon.DOING;
    }

    public void doTheOrder() {
        if (doing == null) return;
        doing.need -= 1;
    }

    public void updateState() {
        if (doing == null) return;
        if (doing.need == 0) {
            doing.state = Weapon.DONE;
            history.add(doing);
            doing = null;
        }
    }
}
