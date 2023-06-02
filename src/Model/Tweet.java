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

    public Tweet(StringBuilder body,int likes, int retweets, int comments) {
        this.body = body;
        this.likes = likes;
        this.retweets = retweets;
        this.comments = comments;
        this.tweetTime = LocalDate.now();
        this.currentTime = LocalDate.now();
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
