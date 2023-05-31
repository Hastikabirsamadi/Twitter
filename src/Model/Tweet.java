package Model;

import java.time.LocalDate;

public class Tweet {
    private int likes;
    private int retweets;
    private int comments;
    private LocalDate tweetTime;
    private LocalDate currentTime;
    private String hashtag;

    public Tweet(int likes, int retweets, int comments, String hashtag) {
        this.likes = likes;
        this.retweets = retweets;
        this.comments = comments;
        this.tweetTime = LocalDate.now();
        this.currentTime = LocalDate.now();
        this.hashtag = hashtag;
    }
}
