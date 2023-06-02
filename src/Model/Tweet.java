package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Tweet implements Serializable {
    private StringBuilder body;
    private int likes;
    private int retweets;
    private int comments;
    private LocalDate tweetTime;
    private LocalDate currentTime;

    public Tweet(StringBuilder body, int likes, int retweets, int comments) {
        this.body = body;
        this.likes = likes;
        this.retweets = retweets;
        this.comments = comments;
        this.tweetTime = LocalDate.now();
        this.currentTime = LocalDate.now();
    }
}
