public class Direction {
    public boolean delivered = false; //.. set to true when it has been delivered
    public static enum Turn {Left, Right, LeftxLeft, LeftxRight, RightxRight, 
        RightxLeft, LeftxLeftxLeft, LeftxLeftxRight, LeftxRightxLeft, LeftxRightxRight,
        RightxLeftxLeft, RightxLeftxRight, RightxRightxLeft, RightxRightxRight, End, LeftxRightxLeftxRight, LeftxRightxRightxLeft, RightxRightxStraight}
    public Turn easyTurn;
    public Turn hardTurn; 
    public float x; //.. when to trigger
    public float y; 

    public int MAXDISTANCE = 20;

    public Direction (Turn turn, Turn hardTurn,  float x, float y) {
         this.easyTurn = turn; this.x =x; this.y =y; this.hardTurn = hardTurn;
    }

    /**return true if x and y is in range*/
    public boolean trigger(float posX, float posY) {
        if (delivered) return false;

        if ((Math.abs(posX - x) < MAXDISTANCE) && ((Math.abs(posY - y) < MAXDISTANCE))) {
            delivered = true;
            return true;
        }
        return false;
    }

        
        
        
}