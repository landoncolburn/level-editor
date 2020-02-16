import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;

public class SwingPaint {

  int magnitude = 0;
  RectangleWall highlightedWall;
  JButton renderBtn, listBtn, clearBtn, exportBtn, spawnBtn;
  DrawArea drawArea;

  ActionListener actionListener = new ActionListener(){
    public void actionPerformed(ActionEvent e) {
      if(e.getSource() == renderBtn) {
        drawArea.render();
      } else if(e.getSource() == listBtn) {
        drawArea.exportList().forEach((w)->{
          System.out.println(w.details());
          System.out.println(magnitude);
        });
      } else if(e.getSource() == clearBtn){
        drawArea.walls.clear();
        drawArea.render();
      } else if(e.getSource() == exportBtn){
        try{
          drawArea.exportLevel();
        } catch(IOException l){
          l.printStackTrace();
        }
      } else if(e.getSource() == spawnBtn){
        DrawArea.playerSetOn = true;
      }
    }
  };

  Action shift = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       magnitude = Math.abs(magnitude-9);
     }
  };

  Action rotate = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           int temp = drawArea.walls.get(i).h;
           drawArea.walls.get(i).h=drawArea.walls.get(i).w;
           drawArea.walls.get(i).w=temp;
           drawArea.render();
         }
       }
     }
  };

  Action fix = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           if(drawArea.walls.get(i).w>drawArea.walls.get(i).h){
             drawArea.walls.get(i).h = 10;
           } else {
             drawArea.walls.get(i).w = 10;
           }
           drawArea.render();
         }
       }
     }
  };

  Action sW = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).w-=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  Action gW = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).w+=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  Action sH = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).h-=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  Action gH = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).h+=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  Action up = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).y-=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  Action down = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).y+=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  Action left = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).x-=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  Action right = new AbstractAction(){
     public void actionPerformed(ActionEvent e){
       for(int i = 0; i<drawArea.walls.size(); i++){
         if(drawArea.walls.get(i).highlighted){
           drawArea.walls.get(i).x+=magnitude+1;
           drawArea.render();
         }
       }
     }
  };

  public static void main(String[] args) {
    new SwingPaint().show();
  }

  public void show() {
    JFrame frame = new JFrame("Swing Paint");
    Container content = frame.getContentPane();
    content.setLayout(new BorderLayout());
    drawArea = new DrawArea();

    content.add(drawArea, BorderLayout.CENTER);

    JPanel controls = new JPanel();

    renderBtn = new JButton("Refresh");
    renderBtn.addActionListener(actionListener);
    listBtn = new JButton("List Details");
    listBtn.addActionListener(actionListener);
    spawnBtn = new JButton("Set spawn");
    spawnBtn.addActionListener(actionListener);
    clearBtn = new JButton("Remove all");
    clearBtn.addActionListener(actionListener);
    exportBtn = new JButton("Export level");
    exportBtn.addActionListener(actionListener);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "sH");
    controls.getActionMap().put("sH", sH);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "gH");
    controls.getActionMap().put("gH", gH);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "sW");
    controls.getActionMap().put("sW", sW);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "gW");
    controls.getActionMap().put("gW", gW);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "fix");
    controls.getActionMap().put("fix", fix);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "shift");
    controls.getActionMap().put("shift", shift);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "rotate");
    controls.getActionMap().put("rotate", rotate);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
    controls.getActionMap().put("moveUp", up);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
    controls.getActionMap().put("moveDown", down);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
    controls.getActionMap().put("moveLeft", left);

    controls.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
    controls.getActionMap().put("moveRight", right);

    controls.add(renderBtn);
    controls.add(listBtn);
    controls.add(spawnBtn);
    controls.add(clearBtn);
    controls.add(exportBtn);

    content.add(controls, BorderLayout.NORTH);

    frame.setSize(600, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}
