package Model;

public class Bio {
    private StringBuilder website;
    private String location;
    private StringBuilder details;

    public Bio(StringBuilder website, String location, StringBuilder details) {
        this.website = website;
        this.location = location;
        this.details = details;
    }

    public StringBuilder getWebsite() {
        return website;
    }

    public void setWebsite(StringBuilder website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StringBuilder getDetails() {
        return details;
    }

    public void setDetails(StringBuilder details) {
        this.details = details;
    }
}
