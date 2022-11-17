package guitest;

import javax.swing.border.EmptyBorder;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;

public class Profile extends JFrame {

	private JPanel contentPane;

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

	/**
	 * Create the frame.
	 */
	public Profile() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 496);
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
		
		
		JPanel left = new JPanel();
		contentPane.add(left, BorderLayout.WEST);
		
		JPanel bottom = new JPanel();
		contentPane.add(bottom, BorderLayout.SOUTH);
		
		JScrollPane center = new JScrollPane();
		contentPane.add(center, BorderLayout.CENTER);
		
		JPanel post_container = new JPanel();
		center.setViewportView(post_container);
		post_container.setLayout(new BoxLayout(post_container, BoxLayout.Y_AXIS));
		
		JPanel example_post_panel1 = new JPanel();
		example_post_panel1.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), null));
		post_container.add(example_post_panel1);
		example_post_panel1.setLayout(new GridLayout(1, 3));
		
		JPanel user_profile = new JPanel();
		example_post_panel1.add(user_profile);
		
		ImageIcon profile = new ImageIcon("./image/profile.png");
		JButton profileImage = new JButton();
		profileImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		profileImage.setPreferredSize(new Dimension(150,150));
		profileImage.setIcon(setImageSize(profile, 150, 150));
		user_profile.add(profileImage);
		
		JPanel post_etc = new JPanel();
		example_post_panel1.add(post_etc);
		post_etc.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		String Str = new String("12345 1234123412341 234123451234123412341 2341234512341234 12341234123451234 12341234123 412345123412341 234123512341234 12341234512351234123412341234 2134123412351234123512 34123512341235123 412351234123512341 23512341251234");
		String info = new String("<html><body>" + Str + "</body></html>");
		
		JLabel userInfo = new JLabel();
		userInfo.setText(info);
		userInfo.setVerticalAlignment(SwingConstants.TOP);
		userInfo.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
		userInfo.setPreferredSize(new Dimension(150, 150));
		post_etc.add(userInfo);
		JScrollPane scroll = new JScrollPane(userInfo);
		post_etc.add(scroll);
		
		JPanel panel_2 = new JPanel();
		example_post_panel1.add(panel_2);
		
		JButton follow = new JButton("10");
		follow.setBackground(UIManager.getColor("CheckBox.light"));
		follow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ImageIcon likeImg = new ImageIcon("./image/like.png");
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		follow.setIcon(setImageSize(likeImg, 100, 100));
		panel_2.add(follow);
		
		JButton add_comment = new JButton("5");
		add_comment.setBackground(UIManager.getColor("CheckBox.light"));
		add_comment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		ImageIcon commentImg = new ImageIcon("./image/cmt.png");
		add_comment.setIcon(setImageSize(commentImg, 100, 100));
		panel_2.add(add_comment);

		panel post1 = new panel();
		JPanel example_post_panel2 = new JPanel();
		post_container.add(example_post_panel2);

//		example_post_panel2.add(post1);
		
		JPanel example_post_panel3 = new JPanel();
		post_container.add(example_post_panel3);
		example_post_panel3.setLayout(new BoxLayout(example_post_panel3, BoxLayout.X_AXIS));
	}
	
	private static ImageIcon setImageSize(ImageIcon imgIcon, int i, int j) {
		Image ximg = imgIcon.getImage();
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(yimg);
	}

}
