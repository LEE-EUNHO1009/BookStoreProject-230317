import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class WinHelp extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinHelp dialog = new WinHelp();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinHelp() {
		setResizable(false);
		setTitle("도움말");
		setBounds(100, 100, 310, 177);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(WinHelp.class.getResource("/images/logo.png")));
		lblNewLabel.setBounds(12, 10, 120, 120);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("만든사람: 홍길동");
		lblNewLabel_1.setBounds(149, 10, 155, 15);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("연락처: 032-456-7892");
		lblNewLabel_1_1.setBounds(149, 42, 155, 15);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("email: hello@ici.or.kr");
		lblNewLabel_1_1_1.setBounds(149, 74, 167, 15);
		getContentPane().add(lblNewLabel_1_1_1);
		
		JButton btnNewButton = new JButton("확인");
		btnNewButton.setBounds(149, 106, 97, 23);
		getContentPane().add(btnNewButton);

	}
}
