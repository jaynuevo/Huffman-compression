import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Main extends JPanel{
	JButton browse;
	 private JFileChooser c = new JFileChooser();
	 private String path;
	 private JTextField source;
	 private JLabel sourceL;
	 private JButton next;
	JLabel img;
	
	public Main(JFrame f){
		//super("Huffman Multichannel Image Compression");
		setLayout(null);
		
		
		browse = new JButton("Browse...");
		browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rVal =c.showOpenDialog(null);
                if(rVal == JFileChooser.APPROVE_OPTION) {
                    path=c.getSelectedFile().getAbsolutePath();
                    source.setText(path);
                    
                    BufferedImage i = null;
                    try {
						i = ImageIO.read(new File(path));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                  //  BufferedImage dimg = i.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(400, -1, Image.SCALE_SMOOTH));
                 //   ImageIcon icon = new ImageIcon(path);
                    img.setIcon(imageIcon);
                    //img.setBounds(50,50,50,50);
                    
                   // Image scaledImage = i.getScaledInstance(img.getWidth(),img.getHeight(),Image.SCALE_SMOOTH);
                    //img.setIcon(scaledImage);
                    img.setVisible(true);
                }
            }
        });
		
		img = new JLabel();
		img.setBounds(250,50,400,400);
		
		//This creates a nice frame.
		Border compound;
		Border redline = BorderFactory.createLineBorder(Color.red);
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		compound = BorderFactory.createCompoundBorder(
		                          raisedbevel, loweredbevel);
		img.setBorder(compound);
		add(img);
		
		
		sourceL = new JLabel("Source: ");
		sourceL.setBounds(250,500,100,30);
		add(sourceL);
		
		source = new JTextField(null);
		source.setBounds(300,502,350,20);
		source.setEditable(false);
		add(source);
		
		browse.setBounds(560,520,90,20);
		browse.setVisible(true);
       add(browse);
       
       next = new JButton("Next");
       next.setBounds(560,450,90, 15);
       add(next);
        
       next.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            //  cp.setVisible(false);
            // r
        	 // cp.removeAll();
        	  setVisible(false);
        	  
        	  Options o = new Options(path);
        	  f.add(o);
        	  //setVisible(true);
             // cp.setVisible(true);
           }
       });
       
       
       // WritableRaster inraster;
       // inraster.getsample
	}
	
	
}
