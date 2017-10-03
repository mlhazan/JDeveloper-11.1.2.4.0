package view.bean;

import javax.faces.event.ActionEvent;

public class MyBean {
    private int x;
    private int y;
    private int z;
        
    public MyBean() {
    }

    public void calculateNumbers(ActionEvent actionEvent) {
        this.setZ(this.getX()+this.getY());
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getZ() {
        return z;
    }
}
