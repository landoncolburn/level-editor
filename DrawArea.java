import java.awt.*;
import java.util.LinkedList;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.JComponent;

public class DrawArea extends JComponent {

  private Image image;
  private Graphics2D g2;
  private int rX, rY, rH, rW, oldX, oldY, finalX, finalY;
  public LinkedList<RectangleWall> walls = new LinkedList<RectangleWall>();
  private Point clickedPoint;
  private int moveX = -1;
  private int moveY = -1;
  public static boolean playerSetOn = false;
  private Point playerSpawn;

  public DrawArea(){
    setDoubleBuffered(false);
    addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        System.out.println("pressed");
        if(e.getKeyCode() == KeyEvent.VK_W){
          System.out.println("pressed");
          for(int i = 0; i<walls.size(); i++){
            if(walls.get(i).highlighted){
              walls.get(i).setX(walls.get(i).getX()+5);
              render();
            }
          }
        }
      }
    });

    addMouseListener(new MouseAdapter() {

      public void mouseClicked(MouseEvent e){
        clickedPoint = e.getPoint();
        boolean clickedWall = false;
        for(int i = 0; i<walls.size(); i++){
          if(walls.get(i).getBounds().contains(clickedPoint)){
            walls.get(i).highlighted = !walls.get(i).highlighted;
            clickedWall = true;
          } else {
            walls.get(i).highlighted = false;
          }
        }
        if(!clickedWall && playerSetOn){
          playerSpawn = e.getPoint();
          playerSetOn = false;
        }
        render();
      }

      public void mousePressed(MouseEvent e) {
        oldX = e.getX();
        oldY = e.getY();
      }

      public void mouseReleased(MouseEvent e){
        clickedPoint = null;
        finalX = e.getX();
        finalY = e.getY();
        if (g2 != null) {
          if(finalX>oldX){
            rX = oldX;
            rW = finalX-oldX;
          } else {
            rX = finalX;
            rW = oldX-finalX;
          }
          if(finalY>oldY){
            rY = oldY;
            rH = finalY-oldY;
          } else {
            rY = finalY;
            rH = oldY-finalY;
          }
          if(rW > 10 && rH > 10){
            walls.add(new RectangleWall(rX, rY, rW, rH));
          }
          render();
        }
      }

    });
  }

  public void render(){
    clear();
    if(playerSpawn!=null){
      g2.fillOval((int)playerSpawn.x-5,(int)playerSpawn.y-5,10,10);
    }
    walls.forEach((w) -> {
      if(w.highlighted){
        g2.setColor(Color.BLUE);
      } else {
        g2.setColor(Color.BLACK);
      }
      g2.fillRect(w.getX(), w.getY(), w.getW(), w.getH());
    });
  }

  protected void paintComponent(Graphics g) {
    if (image == null) {
      image = createImage(getSize().width, getSize().height);
      g2 = (Graphics2D) image.getGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      clear();
    }

    g.drawImage(image, 0, 0, null);
  }

  public void clear() {
    g2.setPaint(Color.white);
    g2.fillRect(0, 0, getSize().width, getSize().height);
    g2.setPaint(Color.black);
    repaint();
  }

  public LinkedList<RectangleWall> exportList(){
    return walls;
  }

  public void exportLevel() throws IOException {
    try{
      final BufferedWriter writer = new BufferedWriter(new FileWriter("level2.lvl"));
      writer.write("SPAWN,"+(int)playerSpawn.x*5+","+(int)playerSpawn.y*5+"\n");
      for(RectangleWall r : walls){
        writer.write(r.printLvlInfo() + "\n");
      }
      writer.close();
    } catch(IOException e){
      e.printStackTrace();
    }
  }

}
