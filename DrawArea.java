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
  public static boolean flagSetOn = false;
  private Point playerSpawn;
  private Point flagSpawn;
  private int clickedWallY;
  public String nameValue;
  public int parValue = -1;

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
            clickedWallY = walls.get(i).getY();
          } else {
            walls.get(i).highlighted = false;
          }
        }
        if(!clickedWall && playerSetOn){
          playerSpawn = e.getPoint();
          playerSetOn = false;
        }
        if(clickedWall && flagSetOn){
          flagSpawn = new Point(e.getX(), clickedWallY-32);
          flagSetOn = false;
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
    if(flagSpawn!=null){
      g2.setColor(Color.RED);
      g2.fillRect((int)flagSpawn.x,(int)flagSpawn.y,8,32);
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
    if(image == null) {
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
    if(nameValue == null || parValue == -1 || playerSpawn == null || flagSpawn == null){
      return;
    }
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to save");

    int userSelection = fileChooser.showSaveDialog(SwingPaint.instance.frame);

    if(userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        try{
          final BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave));
          writer.write(nameValue+"\n");
          writer.write(parValue+"\n");
          writer.write((int)playerSpawn.x*5+","+(int)playerSpawn.y*5+"\n");
          writer.write((int)flagSpawn.x*5+","+(int)flagSpawn.y*5+"\n");
          for(RectangleWall r : walls){
            writer.write(r.printLvlInfo() + "\n");
          }
          writer.close();
        } catch(IOException e){
          e.printStackTrace();
        }
    }
  }

  public void openLevel(){
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to load");

    int userSelection = fileChooser.showOpenDialog(SwingPaint.instance.frame);
    if(userSelection == JFileChooser.APPROVE_OPTION){
      File openedFile = fileChooser.getSelectedFile();
      try {
        final BufferedReader reader = new BufferedReader(new FileReader(openedFile));
        walls.clear();
        nameValue = reader.readLine();
        parValue = Integer.parseInt(reader.readLine());
        String[] tplayerSpawn = reader.readLine().split(",");
        playerSpawn = new Point(Integer.parseInt(tplayerSpawn[0])/5, Integer.parseInt(tplayerSpawn[1])/5);
        String[] tflagSpawn = reader.readLine().split(",");
        flagSpawn = new Point(Integer.parseInt(tflagSpawn[0])/5, Integer.parseInt(tflagSpawn[1])/5);
        String line;
        while((line = reader.readLine())!=null){
          String[] lineele = line.split(",");
          walls.add(new RectangleWall(Integer.parseInt(lineele[0])/5, Integer.parseInt(lineele[1])/5, Integer.parseInt(lineele[2])/5, Integer.parseInt(lineele[3])/5));
        }
        reader.close();
      } catch(IOException e){
        e.printStackTrace();
      } finally {
        System.out.println(playerSpawn.toString() + " " + flagSpawn.toString());
        render();
      }
    }
  }

}
