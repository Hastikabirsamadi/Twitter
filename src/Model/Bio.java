package Model;

public class Bio {
    private StringBuilder website;
    private String location;
    private String details;

    public Bio(StringBuilder website, String location, String details) {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
