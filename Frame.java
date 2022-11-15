import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Frame {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Welcome to Twitter"); // 라벨 글
		JButton btnl = new JButton("Click me!!"); // 버튼기능 및 이름
		JButton btn2 = new JButton("Exit");
		JTextArea txtArea = new JTextArea();
		//JTextField txtField = new JTextField(200);
		panel.setLayout(new BorderLayout());
		JPanel btnPanel = new JPanel();
		
		btnPanel.add(btnl);
		btnPanel.add(btn2);
		panel.add(label, BorderLayout.NORTH);
		label.setHorizontalAlignment(JLabel.CENTER);// 라벨 가운데 정렬
		panel.add(txtArea, BorderLayout.CENTER); // 200자 제한 글쓰기
		panel.add(btnPanel, BorderLayout.WEST);
		
		btnl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				label.setText(txtArea.getText());
				
			}
			
		});
		
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
				
			}
			
		});
		
		frame.add(panel);
		
		frame.setResizable(false); // 화면 늘이고 줄이기 가능
		frame.setVisible(true);
		frame.setPreferredSize(new Dimension(840,840/12*9)); // 사이즈
		frame.setSize(8400, 840/12*9); // 사이즈2
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 나가면 작동 안함
	}

}
