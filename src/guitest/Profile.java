package guitest;

import javax.swing.border.EmptyBorder;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

class panel1 extends JPanel{
	
	private JButton imgBtn;
	private JButton txtBtn;
	private JButton likeBtn;
	private JButton cmtBtn;
	private JPanel imgPnl;
	private JPanel txtPnl;
	private JPanel btnPnl;
	private ImageIcon postImg;
	private ImageIcon likeImg;
	private ImageIcon cmtImg;
	private Dimension btnSize;
	private JPanel likePnl;
	private JPanel cmtPnl;
	
	public panel1() {
		setLayout(new GridLayout(1, 3));
		
		btnSize = new Dimension(140,140);
		
		postImg = new ImageIcon("./image/post1.png");
		imgBtn = new JButton();
		imgBtn.setPreferredSize(btnSize);
		imgBtn.setIcon(setImageSize(postImg,140,140));
		imgPnl = new JPanel();
		imgPnl.add(imgBtn);
		add(imgPnl);
		
		txtBtn = new JButton("123123123");
		txtBtn.setPreferredSize(btnSize);
		txtPnl = new JPanel();
		txtPnl.add(txtBtn);
		add(txtPnl);
		
		likeImg = new ImageIcon("./image/like.png");
		likeBtn = new JButton();
		likeBtn.setPreferredSize(new Dimension(140,65));
		likeBtn.setIcon(setImageSize(likeImg,50,50));
		likePnl = new JPanel();
		likePnl.add(likeBtn);
		
		cmtImg = new ImageIcon("./image/cmt.png");
		cmtBtn = new JButton();
		cmtBtn.setPreferredSize(new Dimension(140,65));
		cmtBtn.setIcon(setImageSize(cmtImg,50,50));
		cmtPnl = new JPanel();
		cmtPnl.add(cmtBtn);
		
		btnPnl = new JPanel();
		btnPnl.setLayout(new BoxLayout(btnPnl, BoxLayout.Y_AXIS));
		btnPnl.add(likePnl);
		btnPnl.add(cmtPnl);
		add(btnPnl);
	}
	private static ImageIcon setImageSize(ImageIcon imgIcon, int i, int j) {
		Image ximg = imgIcon.getImage();
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(yimg);
	}
}

public class Profile extends JFrame {

	private JPanel contentPane;
	public panel1 panel01 = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profile frame = new Profile();

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Profile() {
		super("userName profile");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel top = new JPanel();
		contentPane.add(top, BorderLayout.NORTH);
		
		JLabel twitterLabel = new JLabel("");
		ImageIcon imgIcon = new ImageIcon("C:\\twitter.png");
		twitterLabel.setIcon(setImageSize(imgIcon, 200, 50));
		top.add(twitterLabel);
		
		JPanel right = new JPanel();
		contentPane.add(right, BorderLayout.EAST);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		JButton logIn = new JButton("Log-In");
		right.add(logIn);
		
		JButton joinIn = new JButton("Join-In");
		right.add(joinIn);
		
		ImageIcon profile = new ImageIcon("./image/profile.png");
		
		JButton follower[] = new JButton[10];
		for(int i=0;i<10;i++)
		{
			follower[i] = new JButton("follower"+ i);
			follower[i].setIcon(setImageSize(profile,50,50));
			right.add(follower[i]);
		}
		
		
		
		JPanel left = new JPanel();
		contentPane.add(left, BorderLayout.WEST);
		
		JPanel bottom = new JPanel();
		contentPane.add(bottom, BorderLayout.SOUTH);
		
		JScrollPane center = new JScrollPane();
		contentPane.add(center, BorderLayout.CENTER);
		center.setPreferredSize(new Dimension(800,800));
		
		JPanel post_container = new JPanel();
		center.setViewportView(post_container);
		post_container.setLayout(new BoxLayout(post_container, BoxLayout.Y_AXIS));
		
		JPanel userPnl = new JPanel();
		userPnl.setLayout(new GridLayout(1,3));
		
		Dimension btnSize = new Dimension(140,140);
		
		ImageIcon userImg = new ImageIcon("./image/profile.png");
		JButton imgBtn = new JButton();
		imgBtn.setPreferredSize(btnSize);
		imgBtn.setIcon(setImageSize(userImg,140,140));
		JPanel imgPnl = new JPanel();
		imgPnl.add(imgBtn);
		userPnl.add(imgPnl);
		
		JButton infoBtn = new JButton("123123");
		infoBtn.setPreferredSize(btnSize);
		JPanel infoPnl = new JPanel();
		infoPnl.add(infoBtn);
		userPnl.add(infoPnl);
		
		ImageIcon flwImg = new ImageIcon("./image/follow.jpg");
		JButton flwBtn = new JButton();
		flwBtn.setPreferredSize(btnSize);
		flwBtn.setIcon(setImageSize(flwImg,100,100));
		JPanel flwPnl = new JPanel();
		flwPnl.add(flwBtn);
		userPnl.add(flwPnl);
		
		JPanel example_post_panel = new JPanel();
		example_post_panel.add(userPnl);
		post_container.add(example_post_panel);
		
		
		JPanel example_post_panel1 = new JPanel();
		panel1 post_panel1 = null;
		post_panel1 = new panel1();
		example_post_panel1.add(post_panel1);
		post_container.add(example_post_panel1);
		
		JPanel example_post_panel3 = new JPanel();
		example_post_panel3.setPreferredSize(new Dimension(450,180));
		post_container.add(example_post_panel3);
		example_post_panel3.setLayout(new BoxLayout(example_post_panel3, BoxLayout.X_AXIS));
		
		JPanel example_post_panel4 = new JPanel();
		example_post_panel4.setPreferredSize(new Dimension(450,180));
		post_container.add(example_post_panel4);
		example_post_panel4.setLayout(new BoxLayout(example_post_panel4, BoxLayout.X_AXIS));
	
	}


	private static ImageIcon setImageSize(ImageIcon imgIcon, int i, int j) {
		Image ximg = imgIcon.getImage();
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(yimg);
	}
}
