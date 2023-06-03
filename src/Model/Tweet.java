package Model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Tweet implements Serializable {
    private StringBuilder body;
    private int likes;
    private int retweets;
    private int comments;
    private LocalDate tweetTime;
    String currentTime;

    public Tweet(StringBuilder body,int likes, int retweets, int comments) {
        this.body = body;
        this.likes = likes;
        this.retweets = retweets;
        this.comments = comments;
        this.tweetTime = LocalDate.now();
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

    @Override
    public String toString() {
        long diff = ChronoUnit.SECONDS.between(this.tweetTime, LocalDate.now());
        if(0 <= diff && diff < 60) {
            this.currentTime = "Just Now";
        }
        if(0 < diff &&  Duration.between(tweetTime , LocalDate.now()).toMinutes() < 60) {
            this.currentTime = String.valueOf(this.tweetTime.until(LocalDate.now(), ChronoUnit.MINUTES));
        }
        else if(0 < this.tweetTime.until(LocalDate.now(), ChronoUnit.HOURS) && this.tweetTime.until(LocalDate.now(), ChronoUnit.HOURS) <= 24) {
            this.currentTime = String.valueOf(this.tweetTime.until(LocalDate.now(), ChronoUnit.HOURS));
        }
        else {
            this.currentTime = String.valueOf(LocalDate.now());
        }
        return "*********************************" + "\n" +
                body +
                "\nlikes : "+likes +
                "   retweets : " + retweets +
                "   comments=" + comments +
                "\n" + currentTime;
    }
}
