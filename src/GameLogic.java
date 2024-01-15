import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class GameLogic implements PlayableLogic {
    private ArrayList<ConcretePiece> arry_sort = new ArrayList<ConcretePiece>();
    private ArrayList<Position> undo = new ArrayList<Position>();
    private ConcretePlayer atck;
    private ConcretePlayer def;
    private Position live_king = new Position(5, 5);
    private Piece[][] Board = new Piece[11][11];
    private boolean undo_lest_move = true;
    private boolean atck_turn = true;
    private boolean killKing = false;
    private boolean atck_win;


    public GameLogic() {
        create_players();
        reset();
    }

    @Override
    public boolean move(Position a, Position b) {
        Piece from = getPieceAtPosition(a);
        if (atck_turn) {
            if (from.getOwner() != this.atck) {
                return false;
            } else {
                boolean ans = logic_move(a, b);
                if (ans) {
                    if (undo_lest_move) {
                        undo.add(a);
                        undo.add(b);
                    }
                    atck_turn = false;
                }
                if (this.getPieceAtPosition(b) != null) {
                    if (this.getPieceAtPosition(b).getType().equals("♙")) {
                        kill(b, atck);
                        killKing = kill_King(b, live_king, def);
                    }
                    //   kill2(b,atck);
                    return ans;
                }
            }
        } else {
            if (from.getOwner() != this.def) {
                return false;
            } else {
                boolean ans = logic_move(a, b);
                if (ans) {
                    atck_turn = true;
                    if (undo_lest_move) {
                        undo.add(a);
                        undo.add(b);
                    }
                }
                if (this.getPieceAtPosition(b) != null) {
                    if (this.getPieceAtPosition(b).getType().equals("♙")) {
                        kill(b, def);
                    }
                    return ans;

                }
            }
        }
        return false; //if we have a problame is here .
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        return Board[position.getX()][position.getY()];
    }

    @Override
    public Player getFirstPlayer() {
        return atck;
    }

    @Override
    public Player getSecondPlayer() {
        return def;
    }

    @Override
    public boolean isGameFinished() {
        if (this.Board[0][0] != null || this.Board[0][10] != null || this.Board[10][0] != null || this.Board[10][10] != null) {
            def.inc_wins();
            atck_win=false;
             printQ1(false);
            print_q3();
            return true;
        }
        if (killKing) {
            atck.inc_wins();
            atck_win=true;
            print_q3();
            printQ1(true);
            return true;
        }
        return false;
    }

    public void printQ1(boolean whowin) {

        for (int i=0;i<arry_sort.size();i++)
        {
            if (arry_sort.get(i).getNum_step()>0) {
                arry_sort.get(i).Close_S_potion();
            }
        }
        Collections.sort(arry_sort,q1);
       if(whowin==true)
       {
           for (int i=0;i<arry_sort.size();i++)
           {
               if (arry_sort.get(i).getOwner()==this.atck)
               {
                   if (arry_sort.get(i).getNum_step()>0)
                       System.out.println(arry_sort.get(i).getS_postion());
               }
               if (arry_sort.get(i).getOwner()==this.def)
               {
                   if (arry_sort.get(i).getNum_step()>0)
                       System.out.println(arry_sort.get(i).getS_postion());
               }
           }
       }
       if (whowin==false)
       {
           for (int i=0;i<arry_sort.size();i++)
           {
               if (arry_sort.get(i).getOwner()==this.def)
               {
                   if (arry_sort.get(i).getNum_step()>0)
                       System.out.println(arry_sort.get(i).getS_postion());
               }
               if (arry_sort.get(i).getOwner()==this.atck)
               {
                   if (arry_sort.get(i).getNum_step()>0)
                       System.out.println(arry_sort.get(i).getS_postion());
               }
           }
       }
        System.out.println("this is diff");
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return atck_turn;
    }

    @Override
    public void reset() {
        atck_turn = true;
        Board = new Piece[11][11];
        for (int i = 3; i < 8; i++) {
            this.Board[i][0] = new Pawn((this.atck), "A" + (i - 2));
            arry_sort.add((ConcretePiece) this.Board[i][0]);
            this.Board[i][10] = new Pawn((this.atck), "A" + (i + 17));
            arry_sort.add((ConcretePiece) this.Board[i][10]);
        }
        Board[5][1] = new Pawn((this.atck), "A6");
        arry_sort.add((ConcretePiece) this.Board[5][1]);
        this.Board[0][3] = new Pawn((this.atck), "A7");
        arry_sort.add((ConcretePiece) this.Board[0][3]);
        this.Board[10][3] = new Pawn((this.atck), "A8");
        arry_sort.add((ConcretePiece) this.Board[10][3]);
        this.Board[0][4] = new Pawn((this.atck), "A9");
        arry_sort.add((ConcretePiece) this.Board[0][4]);
        this.Board[10][4] = new Pawn((this.atck), "A10");
        arry_sort.add((ConcretePiece) this.Board[10][4]);
        this.Board[0][5] = new Pawn((this.atck), "A11");
        arry_sort.add((ConcretePiece) this.Board[0][5]);
        this.Board[1][5] = new Pawn((this.atck), "A12");
        arry_sort.add((ConcretePiece) this.Board[1][5]);
        this.Board[9][5] = new Pawn((this.atck), "A13");
        arry_sort.add((ConcretePiece) this.Board[9][5]);
        this.Board[10][5] = new Pawn((this.atck), "A14");
        arry_sort.add((ConcretePiece) this.Board[10][5]);
        this.Board[0][6] = new Pawn((this.atck), "A15");
        arry_sort.add((ConcretePiece) this.Board[0][6]);
        this.Board[10][6] = new Pawn((this.atck), "A16");
        arry_sort.add((ConcretePiece) this.Board[10][6]);
        this.Board[0][7] = new Pawn((this.atck), "A17");
        arry_sort.add((ConcretePiece) this.Board[0][7]);
        this.Board[10][7] = new Pawn((this.atck), "A18");
        arry_sort.add((ConcretePiece) this.Board[10][7]);
        this.Board[5][9] = new Pawn((this.atck), "A19");
        arry_sort.add((ConcretePiece) this.Board[5][9]);
        this.Board[5][3] = new Pawn((this.def), "D1");
        arry_sort.add((ConcretePiece) this.Board[5][3]);
        this.Board[4][4] = new Pawn((this.def), "D2");
        arry_sort.add((ConcretePiece) this.Board[4][4]);

        this.Board[5][4] = new Pawn((this.def), "D3");
        arry_sort.add((ConcretePiece) this.Board[5][4]);
        this.Board[6][4] = new Pawn((this.def), "D4");
        arry_sort.add((ConcretePiece) this.Board[6][4]);
        this.Board[3][5] = new Pawn((this.def), "D5");
        arry_sort.add((ConcretePiece) this.Board[3][5]);
        this.Board[4][5] = new Pawn((this.def), "D6");
        arry_sort.add((ConcretePiece) this.Board[4][5]);
        this.Board[5][5] = new King((this.def), "K7");
        arry_sort.add((ConcretePiece) this.Board[5][5]);
        this.Board[6][5] = new Pawn((this.def), "D8");
        arry_sort.add((ConcretePiece) this.Board[6][5]);
        this.Board[7][5] = new Pawn((this.def), "D9");
        arry_sort.add((ConcretePiece) this.Board[7][5]);
        this.Board[4][6] = new Pawn((this.def), "D10");
        arry_sort.add((ConcretePiece) this.Board[4][6]);
        this.Board[5][6] = new Pawn((this.def), "D11");
        arry_sort.add((ConcretePiece) this.Board[5][6]);
        this.Board[6][6] = new Pawn((this.def), "D12");
        arry_sort.add((ConcretePiece) this.Board[6][6]);
        this.Board[5][7] = new Pawn((this.def), "D13");
        arry_sort.add((ConcretePiece) this.Board[5][7]);

    }

    @Override
    public void undoLastMove() {
        if (undo.size() >= 2) {
            undo_lest_move = false;
            undo.removeLast();
            undo.removeLast();
            reset();
            for (int i = 0; i < undo.size(); i = i + 2) {
                move(undo.get(i), undo.get(i + 1));
            }
            undo_lest_move = true;
        }
    }

    @Override
    public int getBoardSize() {
        return Board.length;
    }

    //////////////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////
    public void SetBoard(Position a, Piece l) {
        Board[a.getX()][a.getY()] = l;
    }

    public boolean checkbounds(Position b) {
        if (b.getX() > 10 || b.getX() < 0) {
            return false;
        }
        if (b.getY() > 10 || b.getY() < 0) {
            return false;
        }
        return true;
    }


    private void create_players() {
        this.atck = new ConcretePlayer(false);
        this.def = new ConcretePlayer(true);

    }

    private boolean logic_move(Position a, Position b) {
        Piece from = getPieceAtPosition(a);
        Piece to = getPieceAtPosition(b);
        String s = from.getType();
        if (s.equals("♙")) {
            if ((b.getX() == 0 && b.getY() == 0) || (b.getX() == 0 && b.getY() == 10) || (b.getX() == 10 && b.getY() == 0) || (b.getX() == 10 && b.getY() == 10))
                return false;
        }
        if (a.getX() == b.getX()) {
            if (a.getY() - b.getY() < 0) {
                for (int i = a.getY() + 1; i <= b.getY(); i++) {
                    if (getPieceAtPosition(new Position(a.getX(), i)) != null) {
                        //that was the orinal
                        //if (getPieceAtPosition(new Position(i, a.getX())) != null)
                        return false;
                    }
                }
                if (!s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((b.getY()-a.getY()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }
            if (a.getY() - b.getY() > 0) {
                for (int i = a.getY() - 1; i >= b.getY(); i--) {
                    if (getPieceAtPosition(new Position(a.getX(), i)) != null) {
                        //that was the orinal
                        //if (getPieceAtPosition(new Position(i, a.getX())) != null)
                        return false;
                    }
                }
                if (!s.equals("♙")) {

                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((a.getY()-b.getY()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }

        }
        if (a.getY() == b.getY()) {
            if (a.getX() - b.getX() < 0) {
                for (int i = a.getX() + 1; i <= b.getX(); i++) {
                    if (getPieceAtPosition(new Position(i, a.getY())) != null) {
                        return false;
                    }


                }
                if (!s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((b.getX()-a.getX()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }
            if (a.getX() - b.getX() > 0) {   //a.x > b.x
                for (int i = a.getX() - 1; i >= b.getX(); i--) {
                    if (getPieceAtPosition(new Position(i, a.getY())) != null) {
                        return false;
                    }
                }
                if (!s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                ((ConcretePiece) this.getPieceAtPosition(a)).inc_step();
                ((ConcretePiece) this.getPieceAtPosition(a)).Add_S_potion(b);
                ((ConcretePiece) this.getPieceAtPosition(a)).num_square((a.getX()-b.getX()));
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }

        }

        return false;
    }

    public void kill(Position b, ConcretePlayer p) {
        Position n1 = new Position(b.getX(), b.getY() - 1);
        if ((checkbounds(n1)) && this.getPieceAtPosition(n1) != null) {
            if (this.getPieceAtPosition(n1).getOwner() != p) {
                Position n2 = new Position(b.getX(), b.getY() - 2);
                if ((checkbounds(n2)) && this.getPieceAtPosition(n2) != null && this.getPieceAtPosition(n2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(n2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(n1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX()][b.getY() - 1] = null;
                        }
                    }
                }
                if (n1.getY() == 0) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(n1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX()][b.getY() - 1] = null;
                    }
                }
            }
        }


        Position s1 = new Position(b.getX(), b.getY() + 1);
        if ((checkbounds(s1)) && this.getPieceAtPosition(s1) != null) {
            if (this.getPieceAtPosition(s1).getOwner() != p) {
                Position s2 = new Position(b.getX(), b.getY() + 2);
                if ((checkbounds(s2)) && this.getPieceAtPosition(s2) != null && this.getPieceAtPosition(s2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(s2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(s1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX()][b.getY() + 1] = null;
                        }
                    }
                }
                if (s1.getY() == 10) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(s1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX()][b.getY() + 1] = null;
                    }
                }
            }
        }

        Position e1 = new Position(b.getX() + 1, b.getY());
        if ((checkbounds(e1)) && this.getPieceAtPosition(e1) != null) {
            if (this.getPieceAtPosition(e1).getOwner() != p) {
                Position e2 = new Position(b.getX() + 2, b.getY());
                if ((checkbounds(e2)) && this.getPieceAtPosition(e2) != null && this.getPieceAtPosition(e2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(e2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(e1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX() + 1][b.getY()] = null;
                        }
                    }
                }
                if (e1.getX() == 10) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(e1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX() + 1][b.getY()] = null;
                    }
                }
            }
        }
        Position w1 = new Position(b.getX() - 1, b.getY());
        if ((checkbounds(w1)) && this.getPieceAtPosition(w1) != null) {
            if (this.getPieceAtPosition(w1).getOwner() != p) {
                Position w2 = new Position(b.getX() - 2, b.getY());
                if ((checkbounds(w2)) && this.getPieceAtPosition(w2) != null && this.getPieceAtPosition(w2).getType().equals("♙")) {
                    if (this.getPieceAtPosition(w2).getOwner() == p) {
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(w1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX() - 1][b.getY()] = null;
                        }
                    }
                }
                if (w1.getX() == 0) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(w1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX() - 1][b.getY()] = null;
                    }
                }
            }
        }

    }

    private boolean kill_King(Position b, Position liveKing, ConcretePlayer def) {
        boolean n = false;
        boolean s = false;
        boolean e = false;
        boolean w = false;
        Position w1 = new Position(live_king.getX() - 1, live_king.getY());
        Position e1 = new Position(live_king.getX() + 1, live_king.getY());
        Position s1 = new Position(live_king.getX(), live_king.getY() + 1);
        Position n1 = new Position(live_king.getX(), live_king.getY() - 1);
        if (!checkbounds(w1)) {
            w = true;
        } else {
            if (this.getPieceAtPosition(w1) != null) {
                if (this.getPieceAtPosition(w1).getOwner() != def) {
                    w = true;
                }
            }
        }
        if (!checkbounds(e1)) {
            e = true;
        } else {
            if (this.getPieceAtPosition(e1) != null) {
                if (this.getPieceAtPosition(e1).getOwner() != def) {
                    e = true;
                }
            }
        }
        if (!checkbounds(s1)) {
            s = true;
        } else {
            if (this.getPieceAtPosition(s1) != null) {
                if (this.getPieceAtPosition(s1).getOwner() != def) {
                    s = true;
                }
            }
        }
        if (!checkbounds(n1)) {
            n = true;
        } else {
            if (this.getPieceAtPosition(n1) != null) {

                if (this.getPieceAtPosition(n1).getOwner() != def) {
                    n = true;
                }
            }
        }

        if (n && s && e && w) {

            Pawn pieceAtPosition = (Pawn) this.getPieceAtPosition(b);
            pieceAtPosition.inc_kill();
            return true;
        }
        return false;

    }
    private void print_q3() {
        Collections.sort(arry_sort, q3);
        for (int i=arry_sort.size()-1;i>=0;i--){
            if (arry_sort.get(i).get_num_square()!=0){
                System.out.println(arry_sort.get(i).getName()+": "+arry_sort.get(i).get_num_square()+" square");
            }
        }
        System.out.println("**********************************************");
    }
    Comparator<ConcretePiece> q1 = new Comparator<ConcretePiece>() {
        @Override
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            // Handle null cases
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }

            // Compare by the number of steps
            int result = Integer.compare(o1.getNum_step(), o2.getNum_step());

            // If the number of steps is the same, compare by other criteria if needed
            if (result == 0) {
            }
            return result;
        }

    };
    Comparator<ConcretePiece> q2 = new Comparator<ConcretePiece>() {
        @Override
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            // Handle null cases
            int result=0;
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return 1;
            }
            if (o2 == null) {
                return -1;
            }
            if (o2 instanceof Pawn&&o1 instanceof Pawn) {
                result = Integer.compare(((Pawn) o2).getKill(), ((Pawn) o2).getKill());
            }
            if (result == 0) {
            }
            return result;
        }
    };
    Comparator<ConcretePiece> q3 = new Comparator<ConcretePiece>() {
        @Override
        public int compare(ConcretePiece o1, ConcretePiece o2) {
            int result = 0;
            result = Integer.compare(o1.get_num_square(), o2.get_num_square());

        if (result==0){
            int o1_s = Integer.parseInt(o1.getName().substring(1,o1.getName().length()));
            int o2_s = Integer.parseInt(o2.getName().substring(1,o2.getName().length()));
            result = Integer.compare(o1_s,o2_s);
            }
            if (result==0){
                if (o1.getOwner()==atck&&atck_win){
                  return -1;
                }
                return 1;
            }
            return result;
        }
    };

}


