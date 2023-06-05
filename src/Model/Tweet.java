package Model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.time.ZoneId;

public class Tweet implements Serializable {
    private User author;
    private StringBuilder body;
    private int likes;
    private int retweets;
    private int comments;
    private boolean faveStar;
    private LocalDateTime tweetTime;
    String currentTime;

    public Tweet(StringBuilder body,int likes, int retweets, int comments, User author) {
        this.author = author;
        this.body = body;
        this.likes = likes;
        this.retweets = retweets;
        this.comments = comments;
        this.tweetTime = LocalDateTime.now();
        this.faveStar = false;
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

    public boolean isFaveStar() {
        return faveStar;
    }

    public void setFaveStar(boolean faveStar) {
        this.faveStar = faveStar;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        long diff = convertDate(tweetTime, LocalDateTime.now());
        if(0 < diff && diff < 60) {
            this.currentTime = "Just Now";
        }
        else if(0 < (diff/60) && (diff/60) < 60) {
            this.currentTime = (diff/60) + "m";
        }
        else if(0 < diff/3600 && diff/3600 <= 24) {
            this.currentTime = (diff/3600) + "h";
        }
        else {
            this.currentTime = (LocalDate.now().getDayOfMonth()) + " " + (LocalDate.now().getMonth());
        }
        return "*********************************" + "\n" +
                body +
                "\nlikes : "+likes +
                "   retweets : " + retweets +
                "   comments : " + comments +
                "\n" + currentTime;
    }

    public static long convertDate(LocalDateTime startTime, LocalDateTime finishTime){
        ZoneId zoneId = ZoneId.systemDefault();
        Date start = Date.from(startTime.atZone(zoneId).toInstant());
        Date finish = Date.from(finishTime.atZone(zoneId).toInstant());
        return (finish.getTime() - start.getTime())/1000;
    }
}
