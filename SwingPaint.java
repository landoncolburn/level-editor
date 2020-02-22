import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import javax.swing.UIManager.*;

public class SwingPaint {

  public int magnitude = 0;
  public RectangleWall highlightedWall;
  public JButton renderBtn, listBtn, clearBtn, openBtn, exportBtn, spawnBtn, flagBtn, setPar, setName;
  public DrawArea drawArea;
  public JFrame frame;
  public JTextField name;
  public static SwingPaint instance;

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
      } else if(e.getSource() == flagBtn){
        DrawArea.flagSetOn = true;
      } else if(e.getSource() == setPar){
        drawArea.parValue = Integer.parseInt(name.getText());
      } else if(e.getSource() == setName){
        drawArea.nameValue = name.getText();
      } else if(e.getSource() == openBtn){
        drawArea.openLevel();
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

    try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
    instance = this;
    frame = new JFrame("Eagle Level Editor");
    Container content = frame.getContentPane();
    content.setLayout(new BorderLayout());
    drawArea = new DrawArea();

    content.add(drawArea, BorderLayout.CENTER);

    JPanel controls = new JPanel(new GridLayout(20, 0));



    renderBtn = new JButton("Refresh");
    renderBtn.addActionListener(actionListener);
    listBtn = new JButton("List details");
    listBtn.addActionListener(actionListener);
    spawnBtn = new JButton("Set spawn");
    spawnBtn.addActionListener(actionListener);
    flagBtn = new JButton("Set flag");
    flagBtn.addActionListener(actionListener);
    clearBtn = new JButton("Remove all");
    clearBtn.addActionListener(actionListener);
    openBtn = new JButton("Open level");
    openBtn.addActionListener(actionListener);
    exportBtn = new JButton("Export level");
    exportBtn.addActionListener(actionListener);
    setPar = new JButton("Set par");
    setPar.addActionListener(actionListener);
    setName = new JButton("Set name");
    setName.addActionListener(actionListener);
    name = new JTextField(10);

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
    controls.add(spawnBtn);
    controls.add(flagBtn);
    controls.add(clearBtn);
    controls.add(openBtn);
    controls.add(exportBtn);
    controls.add(setPar);
    controls.add(setName);
    controls.add(name);

    content.add(controls, BorderLayout.EAST);

    frame.setSize(1200, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

}
