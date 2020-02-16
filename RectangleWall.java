import java.awt.Rectangle;
public class RectangleWall{

  public int x,y,w,h;
  public boolean highlighted;

  public RectangleWall(int x, int y, int w, int h){
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public int getW(){
    return w;
  }

  public int getH(){
    return h;
  }

  public void setX(int xi){
    this.x = xi;
  }

  public void setY(int xi){
    this.y = xi;
  }

  public void setW(int xi){
    this.w = xi;
  }

  public void setH(int xi){
    this.h = xi;
  }

  public String details(){
    return ("X: " + x + " Y: " + y + " W: " + w + " H: " + h);
  }

  public String printLvlInfo(){
    return (x*5 + "," + y*5 + "," + w*5 + "," + h*5);
  }

  public Rectangle getBounds(){
    return new Rectangle(x, y, w, h);
  }

}
