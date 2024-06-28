public class Snake{
    private int tailPosition;
    private int headPosition;

    public Snake(int t, int h){
        this.tailPosition = t;
        this.headPosition = h;
    }

    public void setTailPosition(int t){
        this.tailPosition = t;
    }
    public void setHeadPosition(int h){
        this.headPosition = h;
    }

    public int getTailPosition(){
        return this.tailPosition;
    }
    public int getHeadPosition(){
        return this.headPosition;
    }


}