package cppexam.weibo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    String uid;
    List<User> fans;
    List<User> followee;
    List<Weibo> weibo;

    public User(String uid) {
        this.uid = uid;
        fans = new ArrayList<>();
        followee = new ArrayList<>();
        weibo = new ArrayList<>();
    }

    public void postWeibo(String weiboId, String type) {
        weibo.add(new Weibo(weiboId, type));
    }

    public void follow(User followee) {
        if (!this.followee.contains(followee)) {
            this.followee.add(followee);
        }
    }

    public void unfollow(User followee) {
        this.followee.remove(followee);
    }

    public void beFollowed(User follower) {
        if (!fans.contains(follower)) {
            fans.add(follower);
        }
    }

    public void beUnfollowed(User follower) {
        fans.remove(follower);
    }

    public int getFollowee() {
        return followee.size();
    }

    public int getFans() {
        return fans.size();
    }

    public int getWeibo() {
        return weibo.size();
    }

    public void getRecentWeibo(int num, String type) {
        List<Weibo> allWeiboCanSee;
        switch (type) {
            case Weibo.ONLY_SELF_SEE:
                allWeiboCanSee = getAllSelfWeibo();
                break;
            case Weibo.FRIENDS_SEE:
                allWeiboCanSee = getAllFriendsWeibo();
                break;
            case Weibo.FANS_SEE:
                allWeiboCanSee = getAllFolloweeWeibo();
                break;
            default:
                return;
        }
        StringBuilder sb = new StringBuilder();
        allWeiboCanSee.stream()
                    .sorted((w1, w2) -> w2.timestamp - w1.timestamp)
                    .limit(num)
                    .map(w -> w.weiboId)
                    .forEach(id -> sb.append(" ").append(id));
        System.out.println(sb.substring(1));
    }

    /**
     * 查看自己的微博
     */
    private List<Weibo> getAllSelfWeibo() {
        return this.weibo;
    }

    /**
     * 查看朋友圈的微博
     */
    private List<Weibo> getAllFriendsWeibo() {
        List<Weibo> allWeibo = new ArrayList<>();
        allWeibo.addAll(followee.stream()
                                .filter(fans::contains)
                                .flatMap(u -> u.weibo.stream())
                                .filter(w -> !w.type.equals(Weibo.ONLY_SELF_SEE))
                                .collect(Collectors.toList()));
        allWeibo.addAll(this.weibo);
        return allWeibo;
    }

    /**
     * 查看关注的人的微博
     */
    private List<Weibo> getAllFolloweeWeibo() {
        List<Weibo> allWeibo = new ArrayList<>();
        allWeibo.addAll(followee.stream()
                                .filter(fans::contains)
                                .flatMap(u -> u.weibo.stream())
                                .filter(w -> !w.type.equals(Weibo.ONLY_SELF_SEE))
                                .collect(Collectors.toList())); // 对于friend，只要不是仅自己可见都能看见
        allWeibo.addAll(followee.stream()
                                .filter(u -> !fans.contains(u))
                                .flatMap(u -> u.weibo.stream())
                                .filter(w -> w.type.equals(Weibo.FANS_SEE))
                                .collect(Collectors.toList())); // 单纯的关注者，只能看到粉丝可见
        allWeibo.addAll(this.weibo);
        return allWeibo;
    }
}
