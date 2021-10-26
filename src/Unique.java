import java.util.ArrayList;
public class Unique {
    public int[][] grid;
    public int size;
    ArrayList<ArrayList<Vector>> blocks;
    public int count;
    Unique (int size,ArrayList<ArrayList<Vector>> blocks) {
        this.size = size;
        grid = new int[this.size][this.size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = 0;
            }
        }
        this.blocks = blocks;
    }
    void print(int[][] a) {
        for(int i = 0; i < a.length; i++) {
                for(int j = 0; j < a.length; j++) {
                    System.out.printf("%2d ", a[i][j]);
                }
            System.out.println();
        }
    }
    
    int findBlock(int row, int column) {
        int index = 0;
        for (ArrayList<Vector> arrayList : blocks) {
            for (Vector v : arrayList) {
                if(v.X() == row && v.Y() == column) {
                    return index;
                }
            }
            index++;
        }
        return -1;
    }
    boolean possible(ArrayList<Vector> positions,int row, int column,int item) {
        if(grid[row][column] != 0) return false;
        for (int i = 0; i < grid.length; i++) {
            if(grid[row][i] == item) {
                return false;
            }
        }
        for (int i = 0; i < grid.length; i++) {
            if(grid[i][column] == item) {
                return false;
            }
        }
        int index = findBlock(row, column);
        if (index == -1 ) return true; 
        int _sise = blocks.get(index).size();
        Vector vector = positions.get(index);
        if(vector.c == '*') {
            if(item > vector.result || vector.result % item != 0) {
                return false;
            }
            if(_sise == 2) {
                if(contains(vector.result / item) == false) return false;
            }
            
        }
        else if(vector.c == '+') {
            if(item > vector.result) return false;
            //if(nums.contains(vector.result-item) == false) return false;
            if(_sise == 2) {
                if(contains(vector.result - item) == false) return false; 
            }
        }
        /*else if(vector.c == 'c') {
            Vector v1 = this.blocks.get(index).get(0);
            Vector v2 = this.blocks.get(index).get(1);
            if(vector.result > item) {

            }
            else {

            }
        }*/
        return true;
    }

    void solve(int increment,ArrayList<Vector> positions) {
        
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if(count > 1) return;
                if(grid[i][j] == 0) {
                    for (int j2 = 1; j2 <= this.size; j2++) {
                        if(possible(positions,i,j,j2)) {
                            grid[i][j] = j2;
                            solve(++increment,positions);
                            grid[i][j] = 0;
                        }
                    }
                    return;
                }
            }
        }
        count++;
        print(grid);
    }
    boolean isfull() {
        for (int[] arrayList : grid) {
            for (int integer : arrayList) {
                if(integer == 0) return false;
            }
        }
        return true;
    }
    boolean check(ArrayList<Vector> positions) {
        char operator;
        int result = 0;
        int index = 0;        
        for (ArrayList<Vector> arrayList : blocks) {
            //System.out.println("End");
            operator = positions.get(index).c;
            //System.out.println(operator);
            result = operator == '*' ? 1 : 0 ;
            //System.out.println(result);
            if(arrayList.size() == 0) continue;
            //System.out.println("Size : " + arrayList.size());
            for (Vector vector : arrayList) {
                if(operator == '*') {
                    result *= grid[vector.X()][vector.Y()];
                }
                else if(operator == '+') {
                    result += grid[vector.X()][vector.Y()];
                }
                else if(operator == '-') {
                    result -= grid[vector.X()][vector.Y()];
                    result = Math.abs(result);
                }
            }
            //System.out.println(result + " == " + positions.get(index).result);
            if(result != positions.get(index).result) {
                return false;
            }
            index++;
        }
        return true;
    }
    void printv(Vector v) {
        System.out.println("[ "+ v.X() + ", " + v.Y() + ", "+v.c + ", "+ v.result+ " ]");
    }
    boolean contains(int item) {
        boolean ret = false;
        for (int i = 1; i <= this.size; i++) {
            if(item == i) ret = true;
        }
        return ret;
    }
}
