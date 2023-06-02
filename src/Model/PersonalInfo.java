package Model;

import java.io.Serializable;

public class PersonalInfo implements Serializable {
    private String website;
    private String location;
    private StringBuilder bio;

    public PersonalInfo(String website, String location, StringBuilder bio) {
        this.website = website;
        this.location = location;
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
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
