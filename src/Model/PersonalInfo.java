package Model;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public String toString() {
        if(this.website.equals("exit")) {
            this.website = "-";
        }
        if(this.location.equals("exit")) {
            this.location = "-";
        }
        if(Objects.equals(this.bio, new StringBuilder("exit\n"))) {
            this.bio = new StringBuilder("-");
        }
        return "PersonalInfo\n" + "-------------------------" + "bio : \n" + bio +
                "location : " + location + "\n" +
                "website : " + website + "\n";
    }
}
