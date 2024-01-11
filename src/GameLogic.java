import java.sql.SQLOutput;
import java.util.ArrayList;


public class GameLogic implements PlayableLogic {
    private ConcretePlayer atck;
    private ConcretePlayer def;
    private boolean atck_turn = true;

    private Piece[][] Board = new Piece[11][11];

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
                    atck_turn = false;
                }
                if (this.getPieceAtPosition(b).getType().equals("♙")) {
                    kill(b, atck);
                }
                    //   kill2(b,atck);
                    return ans;

            }
        } else {
            if (from.getOwner() != this.def) {
                return false;
            } else {
                boolean ans = logic_move(a, b);
                if (ans) {
                    atck_turn = true;
                }
                if (this.getPieceAtPosition(b)!=null) {
                    if (this.getPieceAtPosition(b).getType().equals("♙")) {
                        kill(b, def);
                    }
                        return ans;

                }
            }
        }
     return false; //if we have a problame is here .
    }

    public void kill(Position b, ConcretePlayer p) {
        Position n1 = new Position(b.getX(),b.getY()-1);
        if ((checkbounds(n1))&&this.getPieceAtPosition(n1)!=null){
            if(this.getPieceAtPosition(n1).getOwner()!= p){
                Position n2 = new Position(b.getX(),b.getY()-2);
                if ((checkbounds(n2))&&this.getPieceAtPosition(n2)!=null){
                    if(this.getPieceAtPosition(n2).getOwner()== p){
                       Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(n1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX()][b.getY() - 1] = null;
                        }
                    }
                    }
                if (n1.getY()==0) {
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(n1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX()][b.getY() - 1] = null;
                    }
                }
                }
        }


        Position s1 = new Position(b.getX(),b.getY()+1);
        if ((checkbounds(s1))&&this.getPieceAtPosition(s1)!=null){
            if(this.getPieceAtPosition(s1).getOwner()!= p){
                Position s2 = new Position(b.getX(),b.getY()+2);
                if ((checkbounds(s2))&&this.getPieceAtPosition(s2)!=null){
                    if(this.getPieceAtPosition(s2).getOwner()== p){
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(s1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX()][b.getY() + 1] = null;
                        }
                    }
                }
                if (s1.getY()==10){
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(s1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX()][b.getY() + 1] = null;
                    }
                }
            }
        }

        Position e1 = new Position(b.getX()+1,b.getY());
        if ((checkbounds(e1))&&this.getPieceAtPosition(e1)!=null){
            if(this.getPieceAtPosition(e1).getOwner()!= p){
                Position e2 = new Position(b.getX()+2,b.getY());
                if ((checkbounds(e2))&&this.getPieceAtPosition(e2)!=null){
                    if(this.getPieceAtPosition(e2).getOwner()== p){
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(e1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX() + 1][b.getY()] = null;
                        }
                    }
                }
                if (e1.getX()==10){
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(e1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX() + 1][b.getY()] = null;
                    }
                }
            }
        }
        Position w1 = new Position(b.getX()-1,b.getY());
        if ((checkbounds(w1))&&this.getPieceAtPosition(w1)!=null){
            if(this.getPieceAtPosition(w1).getOwner()!= p){
                Position w2 = new Position(b.getX()-2,b.getY());
                if ((checkbounds(w2))&&this.getPieceAtPosition(w2)!=null){
                    if(this.getPieceAtPosition(w2).getOwner()== p){
                        Pawn eat = (Pawn) this.getPieceAtPosition(b);
                        if (this.getPieceAtPosition(w1).getType().equals("♙")) {
                            eat.inc_kill();
                            this.Board[b.getX() - 1][b.getY()] = null;
                        }
                    }
                }
                if (w1.getX()==0){
                    Pawn eat = (Pawn) this.getPieceAtPosition(b);
                    if (this.getPieceAtPosition(w1).getType().equals("♙")) {
                        eat.inc_kill();
                        this.Board[b.getX() - 1][b.getY()] = null;
                    }
                }
            }
        }

    }

    public void SetBoard(Position a,Piece l){
        Board[a.getX()][a.getY()] = l;
    }
    public boolean checkbounds (Position b) {
        if (b.getX()>10||b.getX()<0){
            return false;
        }
        if (b.getY()>10||b.getY()<0){
            return false;
        }
        return true;
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
        if(this.Board[0][0]!=null||this.Board[0][10]!=null||this.Board[10][0]!=null||this.Board[10][10]!=null){
            def.inc_wins();
            return true;
        }
       Position attck_N=new Position(live_king.getX(), live_king.getY()+1);
        Position attck_S=new Position(live_king.getX(), live_king.getY()-1);
        Position attck_W=new Position(live_king.getX()-1, live_king.getY());
        Position attck_E=new Position(live_king.getX()+1, live_king.getY());



       // if (checkbounds(attck_N)



        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return atck_turn;
    }

    @Override
    public void reset() {
        Board = new Piece[11][11];
        // player one setting
        create_players();


        // setting pieces locations and id
        for (int i = 3; i < 8; i++) {
            this.Board[i][0] = new Pawn((this.atck), "A"+(i - 2));
            this.Board[i][10] = new Pawn((this.atck), "A"+(i + 17));
        }
        Board[5][1] = new Pawn((this.atck),"A6");
        this.Board[0][3] = new Pawn((this.atck),"A7");
        this.Board[10][3] = new Pawn((this.atck),"A8");
        this.Board[0][4] = new Pawn((this.atck),"A9");
        this.Board[10][4] = new Pawn((this.atck),"A10");
        this.Board[0][5] = new Pawn((this.atck),"A11");
        this.Board[1][5] = new Pawn((this.atck),"A12");
        this.Board[9][5] = new Pawn((this.atck),"A13");
        this.Board[10][5] = new Pawn((this.atck),"A14");
        this.Board[0][6] = new Pawn((this.atck),"A15");
        this.Board[10][6] = new Pawn((this.atck),"A16");
        this.Board[0][7] = new Pawn((this.atck),"A17");
        this.Board[10][7] = new Pawn((this.atck),"A18");
        this.Board[5][9] = new Pawn((this.atck),"A19");
        this.Board[5][3] = new Pawn((this.def),"D1");
        this.Board[4][4] = new Pawn((this.def),"D2");
        this.Board[5][4] = new Pawn((this.def),"D3");
        this.Board[6][4] = new Pawn((this.def),"D4");
        this.Board[3][5] = new Pawn((this.def),"D5");
        this.Board[4][5] = new Pawn((this.def),"D6");
        this.Board[5][5] = new King((this.def),"K7");
        this.Board[6][5] = new Pawn((this.def),"D8");
        this.Board[7][5] = new Pawn((this.def),"D9");
        this.Board[4][6] = new Pawn((this.def),"D10");
        this.Board[5][6] = new Pawn((this.def),"D11");
        this.Board[6][6] = new Pawn((this.def),"D12");
        this.Board[5][7] = new Pawn((this.def),"D13");

    }


    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return Board.length;
    }

    //////////////////////////////////////////////PRIVATE METHODS//////////////////////////////////////////////
    private void create_players() {
        this.atck = new ConcretePlayer(false);
        this.def = new ConcretePlayer(true);

    }

    private boolean logic_move(Position a, Position b) {
        Piece from = getPieceAtPosition(a);
        Piece to = getPieceAtPosition(b);
        String s = from.getType();
        if( s.equals("♙") ){
            if ((b.getX()==0&&b.getY()==0)||(b.getX()==0&&b.getY()==10)||(b.getX()==10&&b.getY()==0)||(b.getX()==10&&b.getY()==10))
                return false;
        }
        if (a.getX() == b.getX()) {
            if (a.getY() - b.getY() < 0) {
                for (int i = a.getY()+1; i <= b.getY(); i++) {
                    if (getPieceAtPosition(new Position(a.getX(), i)) != null) {
                        //that was the orinal
                        //if (getPieceAtPosition(new Position(i, a.getX())) != null)
                        return false;
                    }
                }
                if( !s.equals("♙")) {
                live_king.setX(b.getX());
                live_king.setY(b.getY());
                }
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }
            if (a.getY() - b.getY() >0) {
                for (int i = a.getY()-1; i >= b.getY(); i--) {
                    if (getPieceAtPosition(new Position(a.getX(), i)) != null) {
                        //that was the orinal
                        //if (getPieceAtPosition(new Position(i, a.getX())) != null)
                        return false;
                    }
                }
                if( !s.equals("♙")) {

                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }

        }
        if (a.getY() == b.getY()) {
            if (a.getX() - b.getX() < 0) {
                for (int i = a.getX()+1; i <= b.getX(); i++) {
                    if (getPieceAtPosition(new Position(i, a.getY())) != null) {
                        return false;
                    }


                }
                if( !s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }
            if (a.getX() - b.getX() > 0) {   //a.x > b.x
                for (int i = a.getX()-1; i >= b.getX(); i--) {
                    if (getPieceAtPosition(new Position(i, a.getY())) != null) {
                        return false;
                    }
                }
                if( !s.equals("♙")) {
                    live_king.setX(b.getX());
                    live_king.setY(b.getY());
                }
                SetBoard(a, null);
                SetBoard(b, from);
                return true;

            }

        }

        return false;
    }


}