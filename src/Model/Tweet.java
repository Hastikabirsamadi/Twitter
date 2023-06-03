package Model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
        long diff = convertDate(tweetTime, LocalDate.now());
        if(0 <= diff && diff < 60) {
            this.currentTime = "Just Now";
        }
        else if(0 < diff/60 && diff/60 < 60) {
            this.currentTime = (diff/60) + "m";
        }
        else if(0 < diff/3600 && diff/3600 <= 24) {
            this.currentTime = (diff/3600) + "h";
        }
        else {
            this.currentTime = String.valueOf(LocalDate.now().getDayOfYear()) + (LocalDate.now().getMonth());
        }
        return "*********************************" + "\n" +
                body +
                "\nlikes : "+likes +
                "   retweets : " + retweets +
                "   comments=" + comments +
                "\n" + currentTime;
    }

    public static long convertDate(LocalDate startTime, LocalDate finishTime){
        Date start = java.sql.Date.valueOf(startTime);
        Date finish = java.sql.Date.valueOf(finishTime);
        return (finish.getTime() - start.getTime())/1000;
    }
}
