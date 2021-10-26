public class Vector {
    private int x;
    private int y; 
    public Character c;
    public Integer result;
    public Vector() {
        this.x = -1;
        this.y = -1;
    }
    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector(int x,int y) {
            this.x = x;
            this.y = y;
            this.c = ' ';
            this.result = -999;
    }
    public Vector(int x,int y,Character c,int result) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.result = result;
    }
    public int X() {
        return this.x;
    }
    public int Y() {
        return this.y;
    }
    public boolean isEqual(Vector v) {
        return this.x == v.X() && this.y == v.Y();
    }
}