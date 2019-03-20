package com.devadvance.smsbackupreader;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.codec.binary.Base64;

public class ImageViewer extends JFrame{
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private Double scl;
	private Attachment attachment;
	private int frW;
	private int frH;
	private int winW;
	private int winH;
	private ImagePanel ip;
	private JScrollPane scrollPane;
	
	public ImageViewer(Attachment a) throws IOException {
		attachment = a;
		
		byte[] btDataFile = Base64.decodeBase64(a.getBase64Data());
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(btDataFile));
		
		img = image;
		
		setTitle(a.getFileName());
	    setScale();
	    
	    frW = (int) ((int) img.getWidth() * scl);
	    frH = (int) ((int) img.getHeight() * scl);
	    
	    winW = frW;
	    winH = frH;
	    
	    MouseAdapter ma = new MouseAdapter(){
	    	private Point origin;
	    	
	    	@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					JPopupMenu menu = new JPopupMenu();
					JMenuItem mi = new JMenuItem("Save Image");
					ActionListener al = new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							saveImage();
						}
					};
					mi.addActionListener(al);
					menu.add(mi);
			        menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
	
	    	public void mousePressed(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON1 || arg0.getButton() == MouseEvent.BUTTON2){
					origin = arg0.getPoint();
				}
			}
	    	
	    	@Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, ip);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        ip.scrollRectToVisible(view);
                    }
                }
            }
	    	
	    	@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() == 1){
					scl /= 1.1;
				}else{
					scl *= 1.1;
				}
				
				frW = (int) ((int) img.getWidth() * scl);
			    frH = (int) ((int) img.getHeight() * scl);
			    
			    if(frW < winW){
			    	frW = winW;
			    	setScale();
			    }
			    
			    if(frH < winH){
			    	frH = winH;
			    	setScale();
			    }
			    
			    //ip.setSize(frW, frH);
			    ip.setPreferredSize(new Dimension(frW, frH));
			    ip.repaint();
			}
	    };
	    
	    ip = new ImagePanel();
	    
	    ip.addMouseWheelListener(ma);
	    ip.addMouseListener(ma);
	    ip.addMouseMotionListener(ma);
	    
	    scrollPane = new JScrollPane(ip, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
	    getContentPane().add(scrollPane);
	    
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
	  }
	
	public void setVisible(boolean arg){
		super.setVisible(arg);
	}
	
	public void resizeFrame(){
		setSize(winW, winH);
		ip.setPreferredSize(new Dimension(winW, winH));
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
