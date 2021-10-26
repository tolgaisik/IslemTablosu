import java.util.*;
public class Trominoes {
    private int size;
    private ArrayList<ArrayList<Integer>> trominoBoard;
    private ArrayList<ArrayList<Vector>> trominoList;
    ArrayList<ArrayList<Vector>> possible;
    ArrayList<Vector> operatorListPositions;
    private boolean[] opList;
    private ArrayList<ArrayList<Integer>> board;
    public Trominoes(int _size,boolean[] _opList,ArrayList<ArrayList<Integer>> _board) {
        this.size = _size;
        this.opList = _opList;
        this.board = _board;
        this.trominoBoard = new ArrayList<ArrayList<Integer>>();
        this.trominoList = new ArrayList<ArrayList<Vector>>();
        this.possible = new ArrayList<ArrayList<Vector>>();
        this.operatorListPositions = new ArrayList<Vector>();
        this.initialize();
        this.startTiling();
        this.endBoard();
        this.fillOperators();
    }
    public ArrayList<ArrayList<Integer>> get() {
        return this.trominoBoard;
    }
    public ArrayList<Vector> getPositions() {
        return this.operatorListPositions;
    }
    private void initialize() {
        for(int i = 0; i < this.size; i++) {
            this.trominoBoard.add(new ArrayList<Integer>());
            for(int j = 0; j < this.size; j++) {
                this.trominoBoard.get(i).add(-1);
            }
        }
    }   
    private void startTiling() {
        this.findPossibleTromino();
        this.findPossibleStraightTromino();
        this.findPossibleDomino();
        this.createPossibleBoard();
    }
    private void findPossibleTromino() {
        for(int j = 0; j < this.size - 1; j++) {
            for(int k = 0; k < this.size - 1; k++) {
                ArrayList<Vector> lst = new ArrayList<Vector>();
                lst.add(new Vector(j,k));
                lst.add(new Vector(j+1,k));
                lst.add(new Vector(j+1,k+1));
                this.trominoList.add(lst);
                ArrayList<Vector> lst1 = new ArrayList<Vector>();
                lst1.add(new Vector(j,k));
                lst1.add(new Vector(j+1,k));
                lst1.add(new Vector(j,k+1));
                this.trominoList.add(lst1);
                ArrayList<Vector> lst2 = new ArrayList<Vector>();
                lst2.add(new Vector(j,k));
                lst2.add(new Vector(j+1,k+1));
                lst2.add(new Vector(j,k+1));
                this.trominoList.add(lst2);
                ArrayList<Vector> lst3 = new ArrayList<Vector>();
                lst3.add(new Vector(j,k+1));
                lst3.add(new Vector(j+1,k+1));
                lst3.add(new Vector(j+1,k));
                this.trominoList.add(lst3);//BottomRightCornerShape
            }
        }
        
        
        
    }
    private void findPossibleStraightTromino() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size - 2; j++) {
                ArrayList<Vector> lst = new ArrayList<Vector>();
                lst.add(new Vector(i,j));
                lst.add(new Vector(i,j+1));
                lst.add(new Vector(i,j+2));
                this.trominoList.add(lst);
                lst.clear();
            }
        }
        for(int m = 0; m < this.size - 2; m++) {
            for(int n = 0; n < this.size; n++) {
                ArrayList<Vector> lst1 = new ArrayList<Vector>();
                lst1.add(new Vector(m,n));
                lst1.add(new Vector(m+1,n));
                lst1.add(new Vector(m+2,n));
                this.trominoList.add(lst1);
            }
        }
    }
    private void findPossibleDomino() {
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size - 1; j++) {
                ArrayList<Vector> lst = new ArrayList<Vector>();
                lst.add(new Vector(i,j));
                lst.add(new Vector(i,j+1));
                this.trominoList.add(lst);
            }
        }
        for(int m = 0; m < this.size - 1; m++) {
            for(int n = 0; n < this.size; n++) {
                ArrayList<Vector> lst1 = new ArrayList<Vector>();
                lst1.add(new Vector(m,n));
                lst1.add(new Vector(m+1,n));
                this.trominoList.add(lst1);
            }
        }
    }
    private void createPossibleBoard() {
        int dSize = this.size*this.size - 2;
        int count = 0;
        boolean goOn = true;
        boolean isContain = false;
        ArrayList<Vector> tempOut = new ArrayList<Vector>();
        ArrayList<Vector> temp = new ArrayList<Vector>();
        while(goOn) {    
            Collections.shuffle(this.trominoList);
            while(tempOut.size() < dSize && count < this.trominoList.size()) {
                temp = this.trominoList.get(count);
                for(int k = 0; k < temp.size(); k++) {
                    if(this.doesContain(tempOut,temp.get(k))) {
                        isContain = true;
                        break;
                    }
                }
                if(isContain) {
                    count++;
                    isContain = false;
                    continue;
                }
                else {
                    for(int o = 0; o < temp.size(); o++) {
                        tempOut.add(temp.get(o));
                    }
                    if(temp.size() > 0) {
                        this.possible.add(temp);
                    }
                }
                isContain = true;
                if(tempOut.size() > dSize) {
                    goOn = false;
                    break;
                }
            }
            if(tempOut.size() > dSize) {
                goOn = false;

            }
            else {
                count = 0;
                tempOut.clear();
                this.possible.clear();               
            }
        }
    }
    private void endBoard() {
        int counter = 0;
        Vector temp;
        for(int i = 0; i < this.possible.size(); i++) {
            for(int j = 0; j < this.possible.get(i).size(); j++) {
                temp = this.possible.get(i).get(j);
                this.trominoBoard.get(temp.X()).set(temp.Y(),counter);  
            }
            counter++;
        }
    }
    private boolean doesContain(ArrayList<Vector> list,Vector v) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).X() == v.X() && list.get(i).Y() == v.Y()) {
                return true;
            }
        }
        return false;
    }
    private void fillOperators() {
        ArrayList<Character> lst = new ArrayList<Character>();
        if(this.opList[0]) {
            lst.add('*');
        }
        if(this.opList[1]) {
            lst.add('-');
        }
        if(this.opList[2]) {
            lst.add('+');
        }
        Random rand = new Random();
        ArrayList<Vector> temp;
        Vector leftTop = new Vector();
        char operator;
        int _result;
        int randomIndex;
        for (int i = 0; i < this.possible.size(); i++) {
            randomIndex = rand.nextInt(lst.size());
            operator = lst.get(randomIndex);
            temp = this.possible.get(i);
            if(temp.size() > 2 && operator == '-') {
                operator = '+';
            }
            _result = operator == '*' ? 1 : 0;
            for (int j = 0; j < temp.size(); j++) {
                leftTop = this.findLeftTop(temp);
                if(operator == '*') {
                    _result *= this.board.get(temp.get(j).X()).get(temp.get(j).Y());
                }
                else if(operator == '-') {
                    if(j % 2 == 0) { 
                        _result = _result - this.board.get(temp.get(j).X()).get(temp.get(j).Y());
                    }
                    else {
                        _result = _result + this.board.get(temp.get(j).X()).get(temp.get(j).Y());
                    }
                }
                else if(operator == '+') {
                    _result += this.board.get(temp.get(j).X()).get(temp.get(j).Y());
                }   
            }
            this.operatorListPositions.add(new Vector(leftTop.X(),leftTop.Y(),operator,Math.abs(_result)));
        }
    }
    private Vector findLeftTop(ArrayList<Vector> vecs) {
        int minX = 1000,minY = 1000;
        for (Vector vector : vecs) {
            minX = vector.X() < minX ? vector.X() : minX;
        }
        for (Vector vect : vecs) {
            minY = vect.Y() < minY && vect.X() == minX ? vect.Y() : minY;
        }
        return new Vector(minX,minY);
    }

}
