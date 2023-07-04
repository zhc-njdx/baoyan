package cppexam.blacksmith;

public class Command {
    String cmd;
    BlacksmithShop shop;

    public static final String ADD = "add";
    public static final String QUERY_USER = "queryUser";
    public static final String QUERY_ORDER = "queryOrder";
    public static final String QUERY_ORDERS = "queryOrders";

    public Command(String cmd, BlacksmithShop shop) {
        this.cmd = cmd;
        this.shop = shop;
    }

    public void execute() {
        String[] split = cmd.split(" ");
        switch (split[0]) {
            case ADD:
                shop.addWeaponOrder(split[1], split[2], split[3], split[4]);
                break;
            case QUERY_USER:
                System.out.println(shop.queryUserState(Integer.parseInt(split[1])));
                break;
            case QUERY_ORDER:
                System.out.println(shop.queryOrderState(Integer.parseInt(split[1])));
                break;
            case QUERY_ORDERS:
                System.out.println(shop.queryUserHistory(Integer.parseInt(split[1])));
                break;
            default:
                break;
        }
    }
}
