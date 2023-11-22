package task1;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    int avarage;
    String name;
    String group;

    public Student(int avarage, String name, String group){
        this.avarage = avarage;
        this.name = name;
        this.group = group;
    }

    public String toString(){
        return ("Name: "+name+"\nGroup: "+group+"\nAvarage mark: "+avarage);
    }
}
