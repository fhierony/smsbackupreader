package com.devadvance.smsbackupreader;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.codec.binary.Base64;

public class ImageViewer extends JFrame{
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private Double scl;
	private Attachment attachment;
	private int frW;
	private int frH;
	
	public ImageViewer(Attachment a) throws IOException {
		attachment = a;
		
		byte[] btDataFile = Base64.decodeBase64(a.getBase64Data());
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(btDataFile));
		
		img = image;
		
		setTitle(a.getFileName());
	    setScale();
	    
	    frW = (int) ((int) img.getWidth() * scl);
	    frH = (int) ((int) img.getHeight() * scl);
	    
	    MouseListener m = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getButton() == MouseEvent.BUTTON3){
					JPopupMenu menu = new JPopupMenu();
					JMenuItem mi = new JMenuItem("Save Image");
					ActionListener al = new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							saveImage();
							
						}
					};
					mi.addActionListener(al);
					menu.add(mi);
			        menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			public void mouseEntered(MouseEvent arg0) {}

			public void mouseExited(MouseEvent arg0) {}
			
			public void mousePressed(MouseEvent arg0) {}

			public void mouseReleased(MouseEvent arg0) {}
	    };
	    
	    this.addMouseListener(m);
	    
	    
	    ImagePanel ip = new ImagePanel();	    
	    
	    add(ip);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
	  }
	
	public void setVisible(boolean arg){
		super.setVisible(arg);
	}
	
	public void resizeFrame(){
		setSize(frW, frH);
	}

	private void setScale(){
		int w = img.getWidth();
		int h = img.getHeight();
 
		scl = 1.0;
		if (w<128) {
			scl = (double) 128/w;
		}
		else {
			scl = Math.min((double) 800/w, (double) 600/h);
			if (scl>1.0) scl = 1.0;
		}
	}
	
	public void saveImage(){
		String fileType;
		File file = new File(attachment.getFileName());
		
		switch(attachment.getMimeType()){
			case "image/jpeg":
				fileType = "jpg";
				break;
			case "image/gif":
				fileType = "gif";
				break;
			case "video/3gpp":
				fileType = "3gp";
				break;
			case "video/3gp2":
				fileType = "3g2";
				break;
			default:
				fileType = "bin";
		}
		
		try {
			ImageIO.write(img, fileType, file);
		} catch (IOException e) {
			System.out.println("image write failed");
			System.out.println(e);
			System.exit(-1);
		}
	}
	
	class ImagePanel extends JPanel{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
	    	Graphics2D g2 = (Graphics2D) g;
	    	super.paintComponent(g2);
	        	if (scl==1.0) g2.drawImage(img, 0, 0, null);
	        	else   g2.drawImage(img, AffineTransform.getScaleInstance(scl, scl), null);
		}
	}
}
