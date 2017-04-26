import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

public class HuffTest {
	public static void main(String[] args){
		JFrame f = new JFrame();
		
		Main m =  new Main(f);
		//Options o = new Options();
  	  
		//f.setLayout(null);
		
		f.add(m);
		//f.add(o);  
		
		
		f.setTitle("Huffman Multichannel Image Compression");
		f.setFont(new Font("Arial",Font.BOLD,14));
	        f.setBackground(Color.BLACK);

			
			f.setSize(900,600);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setResizable(false);
			f.setVisible(true);
	}
}
