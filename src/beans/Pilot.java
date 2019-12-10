package beans;

import java.io.Serializable;

public class Pilot implements Serializable, Comparable {
    private String name;
    private String surname;
    private String middleName;
    private float experience;

    Pilot() {}

    Pilot(String name, String surname, String middleName, float experience){
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.experience = experience;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getInitials(){

        return String.format("%s %c.%c.", surname, name.charAt(0), middleName.charAt(0));
    }

    public float getExperience(){
        return experience;
    }

    public void incExperience(){
        this.experience += 0.5;
    }

    @Override
    public int compareTo(Object o) {
        Pilot otherPilot = (Pilot)o;
        if (experience == otherPilot.getExperience()) {
            return 0;
        }
        return (experience > otherPilot.getExperience()) ? 1 : -1;
    }
}
