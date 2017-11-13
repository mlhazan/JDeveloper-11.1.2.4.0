package project1;

public class Emp {
    public Emp() {
        super();
    }
    
    private String name;
    private int id;
    private int exp;
    private int salary;

    public Emp(String name, int id, int exp, int salary) {
        super();
        this.name = name;
        this.id = id;
        this.exp = exp;
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return exp;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
        
}
