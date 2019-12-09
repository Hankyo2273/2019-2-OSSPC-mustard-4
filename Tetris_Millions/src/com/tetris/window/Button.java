package com.tetris.window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Button {
   
   JLabel keyLabel;
   JTextField keysetNumTxt;
   JFrame frame, keyFrame;
   JPanel centerPanel;
   JButton eventBtn;
   JButton[] btn = new JButton[8];
   Container contentPane;
   String keyString = null;
   private static int z_key = 90, left_key = 37, right_key=39, down_key=40, up_key=38, space_key=32, shift_key=16, keyCode, pk=0;
   // ï¿½ï¿½ï¿½ï¿½


   public Button() {}
   
//   public int getZ_key(){
//       return z_key;
//   }
 
   public int getLeft_key() {
      return left_key;
   }
   public int getRight_key() {
   return right_key;
}
   public int getDown_key() {
   return down_key;
   }
   public int getUp_key() {
      return up_key;
   }
   public int getZ_key() {
	   return z_key;
   }
   public int getSpace_key() {
      return space_key;
   }
   public int getShift_key() {
      return shift_key;
   }
   
   public void setZ_key(int z_key){
       this.z_key = z_key;
   }
   public void setLeft_key(int left_key) {
      this.left_key = left_key;
   }
   public void setRight_key(int right_key) {
      this.right_key = right_key;
   }
   public void setDown_key(int down_key) {
      this.down_key = down_key;
   }
   public void setUp_key(int up_key) {
      this.up_key = up_key;
   }
//   public void setZ_key(int z_key) {
//	   this.z_key= z_key;
//   }
   public void setSpace_key(int space_key) {
      this.space_key = space_key;
   }
   public void setShift_key(int shift_key) {
      this.shift_key = shift_key;
   }
   public void FrameShow() {
    
    // Frame
    frame = new JFrame("KEY SETTING");
     frame.setPreferredSize(new Dimension(350, 250));
     frame.setLocation(350, 250);
     frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ DISPOSE_ON_CLOSEï¿½ï¿½ ï¿½Øºï¿½ ï¿½ï¿½.
     frame.pack();
     frame.setVisible(true);
     

     centerPanel = new JPanel();
     contentPane = frame.getContentPane();

     // ï¿½Ð³Î¿ï¿½ ï¿½ï¿½Æ° 6ï¿½ï¿½ ï¿½ß°ï¿½
     centerPanel.setLayout(new GridLayout(4, 2));
     for (int i = 0; i < 8; i++) {
      btn[i] = new JButton();

      centerPanel.add(btn[i]);
      BtnArrayListener bingoBtnClickListener = new BtnArrayListener();
      btn[i].addActionListener(bingoBtnClickListener);
     }
     
     // ï¿½ï¿½Æ° text  
     btn[0].setText(" Left ");
     btn[1].setText(" Right ");
     btn[2].setText(" Down ");
     btn[3].setText(" Rotation ");
     btn[4].setText(" Drop ");
     btn[5].setText(" Hold ");
     btn[6].setText(" Reverse ");
     btn[7].setText(" - Mustard - ");

     btn[0].setName("b0");
     btn[1].setName("b1");
     btn[2].setName("b2");
     btn[3].setName("b3");
     btn[4].setName("b4");
     btn[5].setName("b5");
     btn[6].setName("b6");
     btn[7].setName("b7");
     
    
     
     
     // Frame Layout
     frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
    }
 

 class TextClickListener implements MouseListener {
     public void mouseEntered(MouseEvent e) {
     }
   
     public void mouseExited(MouseEvent e) {
     }
   
     public void mousePressed(MouseEvent e) {
     }
   
     public void mouseReleased(MouseEvent e) {
      JTextField text = (JTextField) e.getSource();
      text.setText("");
     }
   
     public void mouseClicked(MouseEvent e) {
     }

 }

 // 6ï¿½ï¿½ ï¿½ï¿½ ï¿½Ï³ï¿½ï¿½ï¿½ Å¬ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½

 class BtnArrayListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event) {
       eventBtn = (JButton) event.getSource();   
      
       keyFrame = new JFrame("KEY SETTING");
       JPanel panel1 = new JPanel();
       JPanel panel2 = new JPanel();
   
       Container contentPane1 = keyFrame.getContentPane();
       contentPane1.setLayout(new BorderLayout());
    
   
       // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
       keyFrame.setPreferredSize(new Dimension(400, 150));
       keyFrame.setLocation(700, 250);
       keyFrame.pack();
      
       // ï¿½Ø½ï¿½Æ®ï¿½Êµï¿½ ï¿½ï¿½ï¿½ï¿½
       keysetNumTxt = new JTextField("", 5);
       TextClickListener textClickListener = new TextClickListener();
       keysetNumTxt.addMouseListener(textClickListener);
   
       KeyPressed keyPressed = new KeyPressed();
       keysetNumTxt.addKeyListener(keyPressed); // ï¿½Ø½ï¿½Æ®ï¿½Êµå¿¡ Å°ï¿½ï¿½ ï¿½Ô·ÂµÇ¾ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ È£ï¿½ï¿½
      
       if ("b0".equals( eventBtn.getName() ) ) {
          keyFrame.setVisible(true);
          // ï¿½ï¿½ï¿½Ìºï¿½ ï¿½ï¿½ï¿½ï¿½
          keyLabel = new JLabel("Left Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
          pk=0;
          
       } else if("b1".equals( eventBtn.getName() ) ) {
          keyFrame.setVisible(true);
          // ï¿½ï¿½ï¿½Ìºï¿½ ï¿½ï¿½ï¿½ï¿½
          keyLabel = new JLabel("Right Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
          pk=1;
         
       } else if ("b2".equals( eventBtn.getName() ) ) {
          keyFrame.setVisible(true);
          // ï¿½ï¿½ï¿½Ìºï¿½ ï¿½ï¿½ï¿½ï¿½
          keyLabel = new JLabel("Down Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
          pk=2;
         
       } else if ("b3".equals( eventBtn.getName() ) ) {
          keyFrame.setVisible(true);
          // ï¿½ï¿½ï¿½Ìºï¿½ ï¿½ï¿½ï¿½ï¿½
          keyLabel = new JLabel("Rotation Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
          pk=3;
         
       } else if ("b4".equals( eventBtn.getName() ) ) {
          keyFrame.setVisible(true);
          // ï¿½ï¿½ï¿½Ìºï¿½ ï¿½ï¿½ï¿½ï¿½
          keyLabel = new JLabel("Drop Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
          pk=4;
         
       } else if ("b5".equals( eventBtn.getName() ) ) {
          keyFrame.setVisible(true);
          // ï¿½ï¿½ï¿½Ìºï¿½ ï¿½ï¿½ï¿½ï¿½
          keyLabel = new JLabel("Hold Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
          pk=5;
          
       } else if ("b6".equals( eventBtn.getName() ) ) {
          keyFrame.setVisible(true);
          // ï¿½ï¿½ï¿½Ìºï¿½ ï¿½ï¿½ï¿½ï¿½
          keyLabel = new JLabel("Reverse Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
          pk=6;
       } 
      
      // ï¿½Ð³ï¿½ ï¿½ß°ï¿½
      
      
      panel1.add(keyLabel);
      panel2.add(keysetNumTxt);
   
      // ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½Ì¾Æ¿ï¿½
      keyFrame.getContentPane().add(BorderLayout.NORTH, panel1);
      keyFrame.getContentPane().add(BorderLayout.CENTER, panel2);
     }
 }

 class BtnClickListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
       eventBtn = (JButton) event.getSource();
    }
 }
 
 // ï¿½ï¿½Æ°ï¿½ï¿½ï¿½ï¿½ ï¿½Ò·ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ó¿ï¿½ Å°ï¿½Ô·Â½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½

 class KeyPressed implements KeyListener{
    public void keyPressed(KeyEvent e) {
       BtnArrayListener btnArray = new  BtnArrayListener();

         
//       keyString = ""; // ï¿½Ô·ï¿½ ï¿½ï¿½ ï¿½Ø½ï¿½Æ® ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½Ê±ï¿½È­
       System.out.println("=======");
       keyString = e.getKeyText(e.getKeyCode());
       keyCode = e.getKeyCode();
       System.out.println(e.getKeyCode());
       
       if (e.getKeyCode() != 10) {
          if (pk == 0) {
             if(keyCode == right_key) {
                warning_Right();
             } else if(keyCode == up_key) {
                warning_Up();
             } else if(keyCode == down_key) {
                warning_Down();
             } else if(keyCode == space_key) {
                warning_Drop();
             } else if(keyCode == shift_key) {
                warning_Hold();
             } else if(keyCode == z_key) {
            	 warning_Reverse();
             }  else {
                setLeft_key(keyCode);
                btn[0].setText("Left : "+ keyString);
                keyFrame.setVisible(false); // ï¿½Ô·ï¿½Ã¢ ï¿½ï¿½ï¿½ï¿½
                }
             
          } else if (pk == 1){
             if (keyCode == left_key) {
                warning_Left();                
             } else if(keyCode == up_key) {
                warning_Up();
             } else if(keyCode == down_key) {
                warning_Down();
             } else if(keyCode == space_key) {
                warning_Drop();
             } else if(keyCode == shift_key) {
                warning_Hold();
             } else if(keyCode == z_key) {
            	 warning_Reverse();
             }
             else {
                setRight_key(keyCode);
                btn[1].setText("Right : "+ keyString);
                keyFrame.setVisible(false); // ï¿½Ô·ï¿½Ã¢ ï¿½ï¿½ï¿½ï¿½   
                }
             
          } else if (pk == 2){
             if (keyCode == left_key) {
                warning_Left();                
             } else if(keyCode == right_key) {
                warning_Right();
             } else if(keyCode == up_key) {
                warning_Up();
             } else if(keyCode == space_key) {
                warning_Drop();
             } else if(keyCode == shift_key) {
                warning_Hold();
             } else if(keyCode == z_key) {
            	 warning_Reverse();
             }
             else {
                setDown_key(keyCode);
                btn[2].setText("Down : "+ keyString);
                keyFrame.setVisible(false); // ï¿½Ô·ï¿½Ã¢ ï¿½ï¿½ï¿½ï¿½   
                }
             
          } else if (pk == 3){
             if (keyCode == left_key) {
                warning_Left();                
             } else if(keyCode == right_key) {
                warning_Right();
             } else if(keyCode == down_key) {
                warning_Down();
             } else if(keyCode == space_key) {
                warning_Drop();
             } else if(keyCode == shift_key) {
                warning_Hold();
             } else if(keyCode == z_key) {
            	 warning_Reverse();
             }
             else {
                setUp_key(keyCode);
                btn[3].setText("Rotation : "+ keyString);
                keyFrame.setVisible(false); // ï¿½Ô·ï¿½Ã¢ ï¿½ï¿½ï¿½ï¿½   
                }
             
          } else if (pk == 4){
             if (keyCode == left_key) {
                warning_Left();                
             } else if(keyCode == right_key) {
                warning_Right();
             } else if(keyCode == up_key) {
                warning_Up();
             } else if(keyCode == down_key) {
                warning_Down();
             } else if(keyCode == shift_key) {
                warning_Hold();
             } else if(keyCode == z_key) {
            	 warning_Reverse();
             }
             else {
                setSpace_key(keyCode);
                btn[4].setText("Drop : "+ keyString);
                keyFrame.setVisible(false); // ï¿½Ô·ï¿½Ã¢ ï¿½ï¿½ï¿½ï¿½   
                }
             
          } else if (pk == 5){
             if (keyCode == left_key) {
                warning_Left();                
             } else if(keyCode == right_key) {
                warning_Right();
             } else if(keyCode == up_key) {
                warning_Up();
             } else if(keyCode == down_key) {
                warning_Down();
             } else if(keyCode == space_key) {
                warning_Drop();
             } else if(keyCode == z_key) {
            	 warning_Reverse();
             }
             else {
                setShift_key(keyCode);
                btn[5].setText("Hold : "+ keyString);
                keyFrame.setVisible(false); // ï¿½Ô·ï¿½Ã¢ ï¿½ï¿½ï¿½ï¿½   
                }
          }
      
          else if (pk == 6){
              if (keyCode == left_key) {
                 warning_Left();                
              } else if(keyCode == right_key) {
                 warning_Right();
              } else if(keyCode == up_key) {
                 warning_Up();
              } else if(keyCode == down_key) {
                 warning_Down();
              } else if(keyCode == shift_key) {
                 warning_Hold();
              } else if(keyCode == space_key) {
             	 warning_Drop();
              }
              else {
                 setZ_key(keyCode);
                 btn[6].setText("Reverse : "+ keyString);
                 keyFrame.setVisible(false); // ï¿½Ô·ï¿½Ã¢ ï¿½ï¿½ï¿½ï¿½   
                 }
           }
          else { keyFrame.setVisible(false);
          }
       }
    }
    
   public void warning_Left() {
      JOptionPane.showMessageDialog(null, "left_key ¿Í Áßº¹µÇ¾ú½À´Ï´Ù. ´Ù¸¥ Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "Error", JOptionPane.ERROR_MESSAGE);
   }
   public void warning_Right() {
      JOptionPane.showMessageDialog(null, "right_key ¿Í Áßº¹µÇ¾ú½À´Ï´Ù. ´Ù¸¥ Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "Error", JOptionPane.ERROR_MESSAGE);
   }
   public void warning_Up() {
      JOptionPane.showMessageDialog(null, "up_key ¿Í Áßº¹µÇ¾ú½À´Ï´Ù. ´Ù¸¥ Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "Error", JOptionPane.ERROR_MESSAGE);
   }
   public void warning_Down() {
      JOptionPane.showMessageDialog(null, "down_key ¿Í Áßº¹µÇ¾ú½À´Ï´Ù. ´Ù¸¥ Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "Error", JOptionPane.ERROR_MESSAGE);
   }
   public void warning_Drop() {
      JOptionPane.showMessageDialog(null, "drop_key ¿Í Áßº¹µÇ¾ú½À´Ï´Ù. ´Ù¸¥ Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "Error", JOptionPane.ERROR_MESSAGE);
   }
   public void warning_Hold() {
      JOptionPane.showMessageDialog(null, "hold_key ¿Í Áßº¹µÇ¾ú½À´Ï´Ù. ´Ù¸¥ Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "Error", JOptionPane.ERROR_MESSAGE);
   }
   public void warning_Reverse() {
	  JOptionPane.showMessageDialog(null, "reverse_key ¿Í Áßº¹µÇ¾ú½À´Ï´Ù. ´Ù¸¥ Å°¸¦ ÀÔ·ÂÇÏ¼¼¿ä.", "Error", JOptionPane.ERROR_MESSAGE);
   }
   

   @Override
   public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
   }
   
   @Override
   public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
   }
  }
 }