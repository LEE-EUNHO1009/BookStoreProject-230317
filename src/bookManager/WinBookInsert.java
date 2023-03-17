package bookManager;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class WinBookInsert extends JDialog {
	private JTextField tfISBN;
	private JTextField tfTitle;
	private JTextField tfAuthor;
	private JTextField tfPublisher;
	private JTextField tfPubDate;
	private JTextField tfPrice;

	private String sUrl;
	private JTextArea taDescription;
	private JSpinner spinnerAmount;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinBookInsert dialog = new WinBookInsert();
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
	public WinBookInsert() {
		setResizable(false);
		setModal(true);
		setTitle("도서 입력창");
		setBounds(100, 100, 560, 614);
		getContentPane().setLayout(null);
		
		String imgUrl = "https://shopping-phinf.pstatic.net/main_3246352/32463527641.20230207163644.jpg";
		sUrl = imgUrl;
		imgUrl = "<html><img src='" + imgUrl + "' width=150 height=200></html>";
		JLabel lblImage = new JLabel(imgUrl);
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					//메시지 상자를 보여주고 입력받자
					//JOptionPane.showInputDialog("그림 경로를 입력하시오");
					WinInputDialog winInputDialog = new WinInputDialog();
					winInputDialog.setModal(true);
					winInputDialog.setVisible(true);
					String imgUrl = winInputDialog.getImageUrl();
					sUrl = imgUrl;
					imgUrl = "<html><img src='" + imgUrl + "' width=150 height=200></html>";
					lblImage.setText(imgUrl);
				}
			}
		});
		
		lblImage.setToolTipText("더블클릭하여 그림 경로를 입력하시오");
		lblImage.setBounds(12, 10, 150, 200);
		getContentPane().add(lblImage);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setBounds(174, 22, 57, 15);
		getContentPane().add(lblISBN);
		
		JLabel lblTitle = new JLabel("책제목:");
		lblTitle.setBounds(174, 52, 57, 15);
		getContentPane().add(lblTitle);
		
		JLabel lblAuthor = new JLabel("저자:");
		lblAuthor.setBounds(174, 82, 57, 15);
		getContentPane().add(lblAuthor);
		
		JLabel lblPublisher = new JLabel("출판사:");
		lblPublisher.setBounds(174, 112, 57, 15);
		getContentPane().add(lblPublisher);
		
		JLabel lblPubDate = new JLabel("출판일:");
		lblPubDate.setBounds(174, 142, 57, 15);
		getContentPane().add(lblPubDate);
		
		JLabel lblPrice = new JLabel("가격:");
		lblPrice.setBounds(174, 172, 57, 15);
		getContentPane().add(lblPrice);
		
		JLabel lblAmount = new JLabel("수량:");
		lblAmount.setBounds(174, 202, 57, 15);
		getContentPane().add(lblAmount);
		
		JLabel lblDescription = new JLabel("책 소개:");
		lblDescription.setBounds(12, 220, 57, 15);
		getContentPane().add(lblDescription);
		
		tfISBN = new JTextField();		
		tfISBN.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String temp = tfISBN.getText();
				if(isDigit(temp)) {
					if(temp.length()==13 && !isDuplicated(temp))
						tfTitle.requestFocus();
				}else {
					tfISBN.setText(temp.substring(0, temp.length()-1));
					System.out.println("숫자아님");
				}
			}
		});
		tfISBN.setBounds(242, 19, 116, 21);
		getContentPane().add(tfISBN);
		tfISBN.setColumns(10);
		
		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		tfTitle.setBounds(242, 49, 290, 21);
		getContentPane().add(tfTitle);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(242, 79, 166, 21);
		getContentPane().add(tfAuthor);
		
		tfPublisher = new JTextField();
		tfPublisher.setColumns(10);
		tfPublisher.setBounds(242, 109, 116, 21);
		getContentPane().add(tfPublisher);
		
		tfPubDate = new JTextField();
		tfPubDate.setColumns(10);
		tfPubDate.setBounds(242, 139, 116, 21);
		getContentPane().add(tfPubDate);
		
		tfPrice = new JTextField();
		tfPrice.setColumns(10);
		tfPrice.setBounds(242, 169, 116, 21);
		getContentPane().add(tfPrice);
		
		spinnerAmount = new JSpinner();
		spinnerAmount.setBounds(242, 199, 77, 22);
		getContentPane().add(spinnerAmount);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 240, 520, 325);
		getContentPane().add(scrollPane);
		
		taDescription = new JTextArea();
		taDescription.setLineWrap(true);
		scrollPane.setViewportView(taDescription);
		
		JButton btnAdd = new JButton("추가");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBook();
				dispose();
			}
		});
		btnAdd.setBounds(370, 172, 162, 49);
		getContentPane().add(btnAdd);
		
		JButton btnDup = new JButton("중복확인");
		btnDup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isDuplicated(tfISBN.getText())) {
					JOptionPane.showMessageDialog(null, "이미 ISBN이 존재합니다");
				}else {
					tfTitle.requestFocus();
				}
			}
		});
		btnDup.setBounds(370, 18, 97, 23);
		getContentPane().add(btnDup);
		
		JButton btnCalendar = new JButton("달력...");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfPubDate.setText(winCalendar.getDate());
			}
		});
		btnCalendar.setBounds(370, 138, 97, 23);
		getContentPane().add(btnCalendar);
		
		JComboBox cbPublisher = new JComboBox();
		cbPublisher.setBounds(370, 108, 116, 23);
		getContentPane().add(cbPublisher);

	}

	protected void addBook() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "insert into bookTBL values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tfISBN.getText());
			pstmt.setString(2, tfTitle.getText());
			pstmt.setString(3, tfAuthor.getText());
			pstmt.setString(4, tfPublisher.getText());
			pstmt.setString(5, tfPubDate.getText());
			pstmt.setString(6, sUrl);
			pstmt.setInt(7, Integer.parseInt(tfPrice.getText()));
			pstmt.setString(8, taDescription.getText());
			pstmt.setInt(9, Integer.parseInt(spinnerAmount.getValue().toString()));			
			pstmt.executeUpdate();
			
			pstmt.close();			
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 		
	}

	protected boolean isDuplicated(String sISBN) {
		//DB 연결 => 존재하는지 select문으로 판단하기
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
			//=============================================		
			String sql = "select count(*) from bookTBL where isbn='" + sISBN + "'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				if(rs.getInt(1) == 1) // 중복
					return true;
				else
					return false;
			}
			rs.close();
			stmt.close();
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			System.out.println("DB 연결 오류");
		} 
		return false;
	}

	protected boolean isDigit(String temp) {
		if(temp.matches("\\d+"))
			return true;
		else 
			return false;
	}
}
