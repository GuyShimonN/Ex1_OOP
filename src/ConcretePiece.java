public class ConcretePiece implements Piece{
    private final ConcretePlayer owner;
    private final String name;
    private int num_step;
    public ConcretePiece(ConcretePlayer p,String name){
        this.owner = p;
        this.name= name;
        this.num_step=0;
    }
    @Override
    public Player getOwner() {
        return owner;
    }

    public String getName(){
        return name;
    }
    @Override
    public String getType() {
//        String t = getName();
//        String s = t.charAt(0) + "";
//        if (s.equals("k")) {
//            return "King";
//        }
//        return "Pawn";
  return "";
    }
    public void inc_step(){
        num_step++;
    }
}
