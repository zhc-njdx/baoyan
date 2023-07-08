package cppexam.weibo;

public class Weibo {
    String weiboId;

    String type;

    int timestamp;

    public static final String ONLY_SELF_SEE = "0";
    public static final String FRIENDS_SEE = "1";
    public static final String FANS_SEE = "2";

    public Weibo(String weiboId, String type) {
        this.weiboId = weiboId;
        this.type = type;
        this.timestamp = (++Platform.tick);
    }

    @Override
    public String toString() {
        return weiboId + " " + type + " " + timestamp;
    }
}
