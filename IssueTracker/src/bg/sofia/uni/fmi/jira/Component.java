package bg.sofia.uni.fmi.jira;

public class Component {

    private String name;
    private String shortName;
    private User creator;


    public Component(String name, String shortName, User creator)
    {
        this.setName(name);
        this.setShortName(shortName);
        this.setCreator(creator);
    }

    public String getShortName() {
        return shortName;
    }

    private void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    private void setCreator(User creator) {
        this.creator = creator;
    }
}
