package Model;

public class Retweet extends Tweet{
    public Retweet(StringBuilder body, int likes, int retweets, int comments, User author) {
        super(body, likes, retweets, comments, author);
    }
}
