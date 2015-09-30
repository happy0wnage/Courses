package ua.nure.petrov.Course.db.entity.db;

/**
 * Created by Владислав on 28.07.2015.
 */
public class Theme extends Entity {

    private String name;

    private String description;

    public Theme(int id) {
        super(id);
    }

    public Theme() {
        super(1);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
