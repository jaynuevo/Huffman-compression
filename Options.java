import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Options extends JPanel{
	JButton train, compress, render, browseC, browseT;
	JLabel img, rImg, rL;
	public ArrayList<Integer> pixels = new ArrayList();
	int pixelFreq[];
	int pixelArr[];
	private PriorityQueue<Node> huffQueue = new PriorityQueue();
	JTextField treeF, compF;
	String binPath;
	JLabel tree, compressed;
	int heightI=0,widthI=0, wd=0;
	String pth;
	public ArrayList<String> pathL = new ArrayList();
	public ArrayList<Integer> valueL = new ArrayList();
	
	private Node root = new Node();
	
	public Options(String path){
		
		setLayout(null);
		
		img = new JLabel();
		img.setBounds(10,100,400,400);
		
		loadImage(path);
		pth = path;
		//This creates a nice frame.
		Border compound;
		Border redline = BorderFactory.createLineBorder(Color.red);
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		compound = BorderFactory.createCompoundBorder(
		                          raisedbevel, loweredbevel);
		img.setBorder(compound);
		add(img);
	//////////////////////////////////////	
		
		rImg = new JLabel();
		rImg.setBounds(480,100,400,400);
		
		compF = new JTextField(null);
		compF.setBounds(560,505, 230,20);
		add(compF);
		
		compressed = new JLabel("Compressed:");
		compressed.setBounds(480, 505, 80,20);
		add(compressed);
		
		browseC = new JButton("Browse");
		browseC.setBounds(790,505,90,20);
		add(browseC);
		
		treeF = new JTextField(null);
		treeF.setBounds(560, 530, 230,20);
		add(treeF);
		
		tree = new JLabel("Tree:  (.huff)");
		tree.setBounds(480,530,80,20);
		add(tree);
		
		browseT = new JButton("Browse");
		browseT.setBounds(790,530,90,20);
		add(browseT);
		
		
		rImg.setBorder(compound);
		add(rImg);
		
		train = new JButton("Train");
		train.setBounds(10,500,133,20);
		add(train);

		compress = new JButton("Compress");
		compress.setBounds(143,500,133,20);
		add(compress);
		
		render = new JButton("Render");
		render.setBounds(276,500,134,20);
		add(render);
		
		rL = new JLabel("Rendered Image");
		rL.setFont(new Font("Arial", Font.PLAIN, 20));
		rL.setBounds(610,50, 200,50);
		add(rL);
		
		
		
		train.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	           String choice;
	           
	           	choice = JOptionPane.showInputDialog("a.) New\nb.) Existing\n");
	           	
	           	if(choice.equals("a")){
	           		getPixels(path);
	           		save();
	           		
	           	}
	           	
	           	else if(choice.equals("b")){

	           		getPixelsNoCount(path);
	           		
	           		try {
						addFileToList();
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	           		
	           		countColors();
	           		
	           		try {
						saveToHuff();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	           
	           	}
	           	
	           }
	       });
		
		
		
		browseC.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	  
	        	   JFileChooser c = new JFileChooser();
	        	   int rVal =c.showOpenDialog(null);
	                if(rVal == JFileChooser.APPROVE_OPTION) {
	                    String d=c.getSelectedFile().getAbsolutePath();
	                    compF.setText(d);
	                    
	                }
	        	   
	           }
	       });
		
		
		browseT.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	  
	        	   JFileChooser c = new JFileChooser();
	        	   int rVal =c.showOpenDialog(null);
	                if(rVal == JFileChooser.APPROVE_OPTION) {
	                    String d=c.getSelectedFile().getAbsolutePath();
	                    treeF.setText(d);
	                    
	                }
	        	   
	           }
	       });
		
		
		render.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	   
	        	   huffQueue = new PriorityQueue();
	        	   
	        	   pathL = new ArrayList();
	        	   valueL = new ArrayList();
	        		

	        	   heightI=0;
	        	   widthI=0;
	        	   wd=0;
	        	   
	        	   root = new Node();
	        	   
	        	   BufferedReader rd = null;
	        	   FileReader fr = null;
				try {
					
					fr = new FileReader(treeF.getText());
					rd = new BufferedReader(fr);
					 
					 
					 while(true){
						 String s = null;
						 s=rd.readLine();
						 
						 if(s == null)
							 break;
						 
						 int v = Integer.parseInt(s);
						 
						 s=rd.readLine();
						 int w = Integer.parseInt(s);
						 huffQueue.offer(new Node(v, w, null, null, null));
						 
						 
			         
					 }
					 
					 createHuffTree();
					 genCode(root, "");
					 
					  
		        	   
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 try {
					rd.close();
					 fr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	              
///////////////////////////////////////////////////////////////////////////////////////////////////   
	           
				 
				 String decodedStr = "";
				 
				 try {
					fr = new FileReader(compF.getText());
					rd = new BufferedReader(fr);
					
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				 int r,g,b,x=0,y=0;
				///count width and height to create image
				 while(true){
					 String s = null;
					 try {
						s=rd.readLine();
						 if(s.equals("-")){
							 widthI = wd;
							 wd =0;
							
							 heightI++;
							 continue;
						 }
						 if(s.equals("end")){
						//	 heightI++;
							 break;
						 }
						
							
						s=rd.readLine();
						
							
						s=rd.readLine();
						
						
						wd++;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
					
				 }

				 
				 try {
					fr.close();
					 rd.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				

				 BufferedImage img = new BufferedImage(heightI, widthI, BufferedImage.TYPE_INT_RGB);

				 try {
						fr = new FileReader(compF.getText());
						rd = new BufferedReader(fr);
						
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 
				 r=0;
				 g=0;
				 b=0;
				 x=0;
				 y=0;
				 
				 while(true){
					 String s = null;
					 try {
						s=rd.readLine();
						 if(s.equals("-")){
							 widthI = wd;
							 wd = 0;
							 x++;
							 y=0;
							 heightI++;
							 continue;
						 }
						 if(s.equals("end")){
						//	 heightI++;
							 break;
						 }
						r = decode(s);
							
						s=rd.readLine();
						g = decode(s);
							
						s=rd.readLine();
						b = decode(s);
						
						Color c = new Color(r,g,b);
						int rgb  = c.getRGB();
						img.setRGB(x,y, rgb);
						
						y++;
						wd++;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 
					
				 }
				 
				 ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(400, -1, Image.SCALE_SMOOTH));
				// ImageIcon icon = new ImageIcon( img );
			    // add( new JLabel(icon) );
			    rImg.setIcon(icon);
					rImg.setVisible(true);
	           
	           }
	       });
		
		
		compress.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	   initialize();
	        	   genCode(root, "");
	        	   try {
					storeBin(path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	   
	           }
		});
		
		
	}
	
	public void getPixelsNoCount(String path){
		BufferedImage i = null;
		 
        try {
				i = ImageIO.read(new File(path));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        int width = i.getWidth();
		    int height = i.getHeight();
		    int size = (width * height) * 3;
		    pixels = new ArrayList<Integer>(size);
		    
        WritableRaster inraster = i.getRaster();
        for (int a = 0; a < width; a++){
            for (int j = 0; j < height; j++) {
                pixels.add(inraster.getSample(a, j, 0));
                pixels.add(inraster.getSample(a, j, 1));
                pixels.add(inraster.getSample(a, j, 2));
            }
        }

	}
	
	
	public void addFileToList() throws NumberFormatException, IOException{
		JFileChooser c=new JFileChooser();
		int rVal =c.showOpenDialog(null);
        if(rVal == JFileChooser.APPROVE_OPTION) {
            pth=c.getSelectedFile().getAbsolutePath();
           addFile(pth);
        }	       
	}
	
	
	public void addFile(String path) throws NumberFormatException, IOException{
		FileReader fr  = new FileReader(path);
        BufferedReader rd = new BufferedReader(fr);
        String str;
        int loop;
        
        while(true){
        	str = rd.readLine();
        	
        	if(str == null)
        		break;
        
        	int p = Integer.parseInt(str);
        	
        	
        	loop = Integer.parseInt(rd.readLine());
        	
        	for (int i=0; i <loop; i++){
        		pixels.add(p);
        	}
        }
         rd.close();
         fr.close();

	}
	
	public void saveToHuff() throws IOException{
		BufferedWriter bw = null;
		FileWriter fw = null;
    	
    	
       
       
        
        
        JFileChooser chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retrieval = chooser.showSaveDialog(null);

		String pathA=chooser.getSelectedFile().getAbsolutePath();

		String filename=chooser.getSelectedFile().getName();
		
		if (retrieval == JFileChooser.APPROVE_OPTION) {
	        	 fw = new FileWriter(pathA +".huff");
	             bw = new BufferedWriter(fw);
        
        
        
	             for (int i = 0; i < pixelArr.length; i++) {
        	
	        	bw.write(Integer.toString(pixelArr[i]));
	        	bw.newLine();
	        	bw.write(Integer.toString(pixelFreq[i]));
	        	bw.newLine();
	       
	    	}
		}
        bw.close();
        fw.close();
	}
	
	public int decode(String bits) {
    // create empty string to hold decoded message
		int decoded =0;

    // iterate through bits
    	for (int i = 0; i < bits.length(); i++) {
            	decoded = getVal(bits);

    	}
    	return decoded;
	}
	
	
	private int getVal(String bits) {
    	// create string to hold potential character
        int value = 0;
        	// traverse code table to seek match
            for (int i = 0; i < pathL.size(); i++) {
                // add to string if match is found
            	if (pathL.get(i).equals(bits))
                    value = valueL.get(i);
            }
        return value;
    }


	
	public void loadImage(String path){
		BufferedImage i = null;
        try {
			i = ImageIO.read(new File(path));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(NullPointerException e){
			
		}

        ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(400, -1, Image.SCALE_SMOOTH));
        img.setIcon(imageIcon);

        img.setVisible(true);
	}
	
	
	public void getPixels(String path){
		 BufferedImage i = null;
		 
         try {
				i = ImageIO.read(new File(path));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
         int width = i.getWidth();
		    int height = i.getHeight();
		    int size = (width * height) * 3;
		    pixels = new ArrayList<Integer>(size);
         
         WritableRaster inraster = i.getRaster();
         for (int a = 0; a < width; a++){
             for (int j = 0; j < height; j++) {
                 pixels.add(inraster.getSample(a, j, 0));
                 pixels.add(inraster.getSample(a, j, 1));
                 pixels.add(inraster.getSample(a, j, 2));
             }
         }
         
         countColors();
	}
	
	
	private void countColors() {
		
		
	    List<Integer> duplicateRemoved;
	    duplicateRemoved = new ArrayList<Integer>(pixels);

	    Set<Integer> hs = new HashSet<Integer>();
	    hs.addAll(duplicateRemoved);
	    duplicateRemoved.clear();
	    duplicateRemoved.addAll(hs);

	    pixelFreq = new int[duplicateRemoved.size()];
	    pixelArr = new int[duplicateRemoved.size()];

	    for (int i = 0; i < pixelArr.length; i++) {
	        int p = duplicateRemoved.get(i);
	        int count = Collections.frequency(pixels, p);
	        pixelArr[i] = p;
	        pixelFreq[i] = count;
	    }
	    
	}
	
	public void storeBin(String path) throws IOException{
		BufferedImage i = null;
		 
        try {
				i = ImageIO.read(new File(path));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        int width = i.getWidth();
		int height = i.getHeight();
		int size = (width * height) * 3;
		    
		
		    
		 
         
         JFileChooser chooser=new JFileChooser();
 		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 		int retrieval = chooser.showSaveDialog(null);

 		String p=chooser.getSelectedFile().getAbsolutePath();
 		binPath = p;
 		String filename=chooser.getSelectedFile().getName();

 		if (retrieval == JFileChooser.APPROVE_OPTION) {
 			BufferedWriter bw = null;
 			FileWriter fw = null;
 	    	
 	    	
 	        fw = new FileWriter(p +".chorba");
 	        bw = new BufferedWriter(fw);

 	        	 WritableRaster inraster = i.getRaster();
 	            for (int a = 0; a < width; a++){
 	                for (int j = 0; j < height; j++) {
 	                    
 	                	for(Integer n: valueL){
 	                    	
 	                    	if(n.equals(inraster.getSample(a, j, 0))){
 	                    		bw.write(pathL.get(valueL.indexOf(n)));
 	                    		bw.newLine();
 	                    		break;
 	                    	}
 	                    }	
 	                    	
 	                    
 	                   for(Integer n: valueL){
	                    	
	                    	if(n.equals(inraster.getSample(a, j, 1))){
	                    		bw.write(pathL.get(valueL.indexOf(n)));
	                    		bw.newLine();
	                    		break;
	                    	}
	                    }
 	                      
 	                  for(Integer n: valueL){
	                    	
	                    	if(n.equals(inraster.getSample(a, j, 2))){
	                    		bw.write(pathL.get(valueL.indexOf(n)));
	                    		bw.newLine();
	                    		break;
	                    	}
	                    }
 	                       
    
 	                }
 	                bw.write("-");
 	                bw.newLine();
 	            }    
 	        	
 	        bw.write("end");
 	        bw.close();
 	        fw.close();
 		} 
		    
	}
	
	public void save(){
		JFileChooser chooser=new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retrieval = chooser.showSaveDialog(null);

		String path=chooser.getSelectedFile().getAbsolutePath();
		String filename=chooser.getSelectedFile().getName();
		
		if (retrieval == JFileChooser.APPROVE_OPTION) {
	        try {
	        	writeToHuff(path);
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
		
	}
	
	
	public void writeToHuff(String path) throws IOException{
		BufferedWriter bw = null;
		FileWriter fw = null;
    	
    	
        fw = new FileWriter(path +".huff");
        bw = new BufferedWriter(fw);
        for (int i = 0; i < pixelArr.length; i++) {
	        bw.write(Integer.toString(pixelArr[i]));
	        bw.newLine();
	        bw.write(Integer.toString(pixelFreq[i]));
	        bw.newLine();
	    }
        bw.close();
        fw.close();
	}
	
	
	public void readFromHuff(String path) throws NumberFormatException, IOException{
		 FileReader fr  = new FileReader(path +".huff");
         BufferedReader rd = new BufferedReader(fr);
         
         int p = Integer.parseInt(rd.readLine());
         
         rd.close();
         fr.close();
	}
	
	
	private void initialize(){
		
		int size = pixelFreq.length;
		
		 for (int i = 0; i < size; i++) {
             huffQueue.offer(new Node(pixelArr[i], pixelFreq[i], null, null, null));
         }
	
		 createHuffTree();
		 //genCode(this.root, "");	
		
	}
	
	 private void createHuffTree() {
         while (huffQueue.size() > 1) {
             // DEQUEUE TWO MINIMUM ELEMENTS
             Node tempL = huffQueue.poll();
             Node tempR = huffQueue.poll();
             
             // CREATING ROOT FOR THE 2 POPPED ELEMENTS
             Node parent = new Node(0, tempL.weight+tempR.weight, tempL, tempR, null);
             tempL.parent = parent;
             tempR.parent = parent;
             
             // ENQUEUE AGAIN
             huffQueue.offer(parent);
             //this.size++;
         }
         
         this.root = huffQueue.peek();
     }
	 
	 private void genCode(Node current, String cod) {
         // IF NODE IS NULL RETURN
         if (current == null){ 
         	return;
         
         }
         // STORE PATH AND VALUE
         if (current.leftTree == null && current.rightTree == null) {
             int v;
           
             v = current.value; 
             valueL.add(v);
             pathL.add(cod);
            
         }

         //ADD 0 BEFORE GOING TO LEFT
         cod += "0";
         
         //TRAVERSE PREORDER
         genCode(current.leftTree, cod);
       
         
         //ADJUSTS PATH AND ADDS 1 BEFORE GOING TO RIGHT
         cod = cod.substring(0, cod.length()-1);
         cod += "1";
         genCode(current.rightTree, cod);
         	            
     }
	
	
	

}
