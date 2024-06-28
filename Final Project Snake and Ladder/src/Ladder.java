public class Ladder{
    private int topPosition;
    private int bottomPosition;

    public Ladder(int t, int b){
        this.topPosition = t;
        this.bottomPosition = b;
    }

    public void setTopPosition(int t){
        this.topPosition = t;
    }
    public void setBottomPosition(int b){
        this.bottomPosition = b;
    }

    public int getTopPosition(){
        return this.topPosition;
    }
    public int getBottomPosition(){
        return this.bottomPosition;
    }


}