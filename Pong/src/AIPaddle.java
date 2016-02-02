import java.awt.Color;


public class AIPaddle extends Paddle
{
    private int speed;
    
    public AIPaddle(int x, int spd)
    {
        super(x);
        speed = spd;
    }
    
    public void followBall(Ball ball)
    {
        double ballY = ball.getY();
        double myY = this.getY()+(this.getHeight()/2);
        
        //this.setY((int)(ballY-this.getHeight()/2));
        
        if (ballY <= myY)
            this.setY(getY()-speed);
        else if (ballY >= myY)
            this.setY(getY()+speed);
    }
    
    public void checkCollision(Ball ball)
    {
        if (ball.getX() <= this.getX()+this.getWidth() &&
                this.getY()<ball.getTop() &&  ball.getY()<this.getTop())
            handleCollision(ball,false);
//        else if (ball.getRight()>= getX() && ball.getX()<=this.getX()+this.getWidth()
//                && ball.getY()<=this.getY()+this.getHeight())
//            handleCollision(ball,true);
//        else if (ball.getRight()>= getX() && ball.getX()<=this.getX()+this.getWidth()
//                && ball.getTop()>=this.getY())
//            handleCollision(ball,true);
    }
    
    public void handleCollision(Ball ball, boolean topOrBottom)
    {
        if (topOrBottom)
            return; 
        
        double relY = (ball.getY()-getY())/(getHeight()/2) - 1;
        relY = relY*-1;
        double angle = Math.atan2(ball.getVelocityY(), ball.getVelocityX());
        angle += relY*Math.PI/4;
        //ball.setVelocityX(ball.VELOCITY * Math.cos(angle));
        ball.setVelocityY((int)(ball.getVelocity() * Math.sin(angle)));
        ball.changeVelX();
    }
}
