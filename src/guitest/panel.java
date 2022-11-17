package guitest;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class panel {

	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					panel window = new panel();
					window.panel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public panel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel imgPart = new JPanel();
		panel.add(imgPart);
		
		ImageIcon userImg = new ImageIcon("./image/post.1");
		JButton user = new JButton();
		user.setIcon(setImageSize(userImg, 150, 150));
		
		imgPart.add(user);
		imgPart.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel postTxt = new JPanel();
		panel.add(postTxt);
		
		JLabel postInfo = new JLabel("post info");
		postTxt.add(postInfo);
		
		JPanel buttonPart = new JPanel();
		panel.add(buttonPart);
		
		JButton like = new JButton();
		like.setVerticalAlignment(SwingConstants.TOP);
		ImageIcon likeImg = new ImageIcon("./image/like.png");
		buttonPart.setLayout(new GridLayout(0, 1, 0, 0));
		like.setIcon(setImageSize(likeImg, 150, 150));
		buttonPart.add(like);
		
		JButton cmt = new JButton("New button");
		buttonPart.add(cmt);
	}
	private static ImageIcon setImageSize(ImageIcon imgIcon, int i, int j) {
		Image ximg = imgIcon.getImage();
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(yimg);
	}
}
