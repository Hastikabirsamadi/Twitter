package Model;

public class Quote extends Tweet{
    private String secondBody;

    public Quote(StringBuilder body, int likes, int retweets, int comments, User author, String secondBody) {
        super(body, likes, retweets, comments, author);
        this.secondBody = secondBody;
    }
}
