package cppexam.weibo;

import java.util.ArrayList;
import java.util.List;

public class Platform {

    List<User> users;

    public static int tick = 0;

    private static Platform platform;

    private Platform(){
        users = new ArrayList<>();
    }

    public static Platform getPlatform() {
        if (platform == null) {
            platform = new Platform();
        }
        return platform;
    }

    private User getUser(String uid) {
        return users.stream().filter(u -> u.uid.equals(uid)).findFirst().orElseGet(() -> {
            User user = new User(uid);
            users.add(user);
            return user;
        });
    }

    public void doPost(String uid, String weiboId, String type) {
        getUser(uid).postWeibo(weiboId, type);
    }
    public void doFollow(String followerId, String followeeId) {
        User follower = getUser(followerId);
        User followee = getUser(followeeId);
        follower.follow(followee);
        followee.beFollowed(follower);
    }

    public void doUnfollow(String followerId, String followeeId) {
        User follower = getUser(followerId);
        User followee = getUser(followeeId);
        follower.unfollow(followee);
        followee.beUnfollowed(follower);
    }

    public void getUserFollowee(String uid) {
        System.out.println(getUser(uid).getFollowee());
    }

    public void getUserFans(String uid) {
        System.out.println(getUser(uid).getFans());
    }

    public void getUserWeibo(String uid) {
        System.out.println(getUser(uid).getWeibo());
    }

    /**
     * 0: 只看自己的微博
     * 1: friend发的除仅自己可见外的微博
     * 2: followee发的粉丝可见的微博
     * @param uid 用户id
     * @param num 查看微博数量
     * @param type 查看类型
     */
    public void getUserRecentWeibo(String uid, int num, String type) {
        // uid用户浏览微博的时候看到的微博
        getUser(uid).getRecentWeibo(num, type);
    }
}
