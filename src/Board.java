import java.util.*;//tı

public class Board {
    public int size;
    public boolean[] operator;
    private ArrayList<ArrayList<Integer>> board;
    public ArrayList<ArrayList<Integer>> blok;
    public int[][] grid;
    private Trominoes blocks;
    public ArrayList<Vector> position;
    public Board(int _size,boolean[] _operator) {
        this.size = _size;
        this.operator = _operator;
        this.grid = new int[_size][_size];
        this.board = new ArrayList<ArrayList<Integer>>();
        this.blok = new ArrayList<ArrayList<Integer>>();
        this.position = new ArrayList<Vector>();
        this.letsBegin();
    }
    public ArrayList<ArrayList<Integer>> getGameBoard(){
        return this.board;
    }
    private void letsBegin() {
        this._initialize();
        this._create_array();
        this._copy();
        this.blocks = new Trominoes(this.size,this.operator,this.board);
        this.blok = this.blocks.get();
        this.position = this.blocks.getPositions();
        int count = 0;
        Unique u = new Unique(this.size, blocks.possible);
        u.solve(0,blocks.operatorListPositions);
        count = u.count;
        System.out.println(count);
        if(count != 1) {
            this.board.clear();
            letsBegin();
        }
    }
    public ArrayList<ArrayList<Integer>> getBlocks(){
        return this.blok;
    }
    public int getSize() {
        return this.size;
    }
    private void _initialize() {
        //seeds to generate more boards
        int [][] lst4 = {{2,3,4,1},{3,4,1,2},{1,2,3,4},{4,1,2,3}};  
        int [][] lst5 = {{2,3,1,5,4},{1,5,4,2,3},{3,1,5,4,2},{5,4,2,3,1},{4,2,3,1,5}};
        int [][] lst6 = {{4,3,1,2,5,6},{3,1,2,5,6,4},{1,2,5,6,4,3},{2,5,6,4,3,1},{5,6,4,3,1,2},{6,4,3,1,2,5}};
        int [][] lst7 = {{2,4,6,7,5,1,3},{4,6,7,5,1,3,2},{6,7,5,1,3,2,4},{7,5,1,3,2,4,6},{5,1,3,2,4,6,7},{1,3,2,4,6,7,5},{3,2,4,6,7,5,1}};
        int [][] lst8 = {{4,2,6,5,3,1,8,7},{2,6,5,3,1,8,7,4},{6,5,3,1,8,7,4,2},{5,3,1,8,7,4,2,6},{3,1,8,7,4,2,6,5},{1,8,7,4,2,6,5,3},{8,7,4,2,6,5,3,1},{7,4,2,6,5,3,1,8}};
        for(int i = 0; i < this.size; i++){
            this.board.add(new ArrayList<Integer>());
        }
        for(int j = 0; j < this.size; j++) {
            for(int k = 0; k < this.size; k++) {
                switch (this.size) {
                    case 4:
                        this.board.get(j).add(lst4[j][k]);
                        break;
                    case 5:
                        this.board.get(j).add(lst5[j][k]);
                        break;
                    case 6:
                        this.board.get(j).add(lst6[j][k]);
                        break;
                    case 7:
                        this.board.get(j).add(lst7[j][k]);
                        break;
                    case 8:
                        this.board.get(j).add(lst8[j][k]);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private void _create_array() {
        this._shuffle_rows();
        this._shuffle_columns();
        this._shuffle_rows();
        this._shuffle_columns();
    }
    private void _shuffle_rows() {
        Collections.shuffle(this.board);
    }
    private void _shuffle_columns() {
        this._roll_board(1);
        Collections.shuffle(this.board);
    }
    private void _roll_board(int times) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
        for(int j = 0; j < this.size; j++){
            ArrayList<Integer> col = new ArrayList<Integer>();
            for(ArrayList<Integer> row : this.board){
                col.add(row.get(j));                
            }
            temp.add(col);
        }
        this.board.clear();
        this.board = temp;
    }
    private void _copy() {
        for (int i = 0; i < this.board.size(); i++) {
            for (int j = 0; j < this.board.get(i).size(); j++) {
                this.grid[i][j] = this.board.get(i).get(j);
            }
        }
    }
    private boolean isUniq() {
        return false;
    }
    private void clear(){
        
    }
    private boolean checkZero() {
        for(int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if(board.get(i).get(j) == 0) {
                    return true;
                }
            }
        }
        for(int k = 0; k < grid.length; k++) {
            for(int l = 0; l < grid.length; l++) {
                if(this.grid[k][l] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}