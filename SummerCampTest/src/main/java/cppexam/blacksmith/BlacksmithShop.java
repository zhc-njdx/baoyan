package cppexam.blacksmith;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BlacksmithShop {
    int buffer; // 可以暂存在铁匠铺的订单数量
    List<Blacksmith> blacksmiths; // 铁匠
    List<Weapon> orders; // 所有的订单

    public BlacksmithShop(int buffer, List<Blacksmith> blacksmiths) {
        this.buffer = buffer;
        this.blacksmiths = blacksmiths;
        this.orders = new ArrayList<>();
    }

    public void addWeaponOrder(String... args) {
        int oid = Integer.parseInt(args[0]);
        int priority = Integer.parseInt(args[1]);
        int time = Integer.parseInt(args[2]);
        int type = Integer.parseInt(args[3]);
        Weapon w = new Weapon(oid, priority, time, type);
        long pending = orders.stream().filter(o -> o.state.equals(Weapon.PENDING)).count();
        orders.add(w);
        if (pending >= buffer) {
            Weapon discard = orders.stream().filter(o -> o.state.equals(Weapon.PENDING))
                    .min(Comparator.comparingInt(o -> o.priority)).orElse(null);
            if (discard == null) { // 不会出现这种情况
                w.state = Weapon.DISCARD;
            } else {
                discard.state = Weapon.DISCARD;
            }
        }
    }

    public String queryUserState(int uid) {
        for (Blacksmith blacksmith : blacksmiths) {
            if (blacksmith.uid == uid) {
                return blacksmith.getCurrentState();
            }
        }
        return "";
    }

    public String queryOrderState(int oid) {
        for (Weapon weapon : orders) {
            if (weapon.oid == oid) {
                return weapon.getCurrentState();
            }
        }
        return "";
    }

    public String queryUserHistory(int uid) {
        for (Blacksmith blacksmith : blacksmiths) {
            if (blacksmith.uid == uid) {
                return blacksmith.getOrderHistory();
            }
        }
        return "";
    }

    public void schedule() {
        for (Blacksmith blacksmith : blacksmiths) {
            blacksmith.updateState();
            if (blacksmith.isIdle()) { // 为每个空闲的铁匠分配订单
                blacksmith.selectOrder(orders);
            }
            blacksmith.doTheOrder();
        }
    }


    public String logContent() {
        StringBuilder sb = new StringBuilder();
        for (Weapon weapon : orders) {
            sb.append(String.format(Weapon.FORMAT, weapon.oid, weapon.state)).append("\n");
        }
        return sb.toString();
    }
}
