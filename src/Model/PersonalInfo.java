package Model;

public class PersonalInfo {
    private StringBuilder website;
    private String location;
    private StringBuilder bio;

    public PersonalInfo(StringBuilder website, String location, StringBuilder bio) {
        this.website = website;
        this.location = location;
        this.bio = bio;
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

    public StringBuilder getBio() {
        return bio;
    }

    public void setBio(StringBuilder bio) {
        this.bio = bio;
    }
}
