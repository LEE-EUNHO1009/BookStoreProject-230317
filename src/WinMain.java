import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.VariableHeightLayoutCache;

import bookManager.WinBookAllShow;
import bookManager.WinBookDelete;
import bookManager.WinBookInsert;
import bookManager.WinBookUpdate;
import bookManager.WinComboSearch;
import memberManager.WinMemberAdd;
import memberManager.WinMemberAllShow;
import memberManager.WinMemberModify;
import memberManager.WinMemberRemove;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;

public class WinMain extends JDialog {
	private static JTable table;
	private static JLabel lblISBN;
	private JTextField tfSearch;
	private final int typeAdd = 1;
	private final int typeRemove = 2;
	private final int typeModify = 3;
	private final int typeSelect = 4;
	private JProgressBar progressBar;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMain dialog = new WinMain();
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
	public WinMain() {
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				showTable();
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		setTitle("도서 대여점");
		setBounds(100, 100, 833, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnuFile = new JMenu("파일(F)");
		mnuFile.setMnemonic('F');
		menuBar.add(mnuFile);
		
		JMenuItem mnuExit = new JMenuItem("종료(X)");
		mnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_DOWN_MASK));
		mnuExit.setMnemonic('X');
		mnuFile.add(mnuExit);
		
		JMenu mnuBook = new JMenu("도서(B)");
		mnuBook.setMnemonic('B');
		menuBar.add(mnuBook);
		
		JMenuItem mnuBookAdd = new JMenuItem("도서 추가(A)...");
		mnuBookAdd.setMnemonic('A');
		mnuBookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookInsert winBookInsert = new WinBookInsert();
				winBookInsert.setModal(true);
				winBookInsert.setVisible(true);
			}
		});
		mnuBookAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		mnuBook.add(mnuBookAdd);
		
		JMenuItem mnuBookRemove = new JMenuItem("도서 삭제...");
		mnuBookRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(0); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					WinBookDelete winBookDelete = 
							new WinBookDelete(table.getValueAt(table.getSelectedRow(), 0).toString());
					winBookDelete.setModal(true);
					winBookDelete.setVisible(true);
				}				
			}
		});
		mnuBook.add(mnuBookRemove);
		
		JMenuItem mnuBookModify = new JMenuItem("도서 변경...");
		mnuBookModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(1); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					WinBookUpdate winBookUpdate = 
							new WinBookUpdate(table.getValueAt(table.getSelectedRow(), 0).toString());
					winBookUpdate.setModal(true);
					winBookUpdate.setVisible(true);
				}
			}
		});
		mnuBook.add(mnuBookModify);
		
		JMenuItem mnuBookSelect = new JMenuItem("도서 조회...");
		mnuBook.add(mnuBookSelect);
		
		mnuBook.addSeparator();
		
		JMenuItem mnuBookDetailShow = new JMenuItem("도서 상세보기...");
		mnuBookDetailShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookAllShow winBookAllShow = new WinBookAllShow();
				//winBookAllShow.setModal(true);
				winBookAllShow.setVisible(true);
			}
		});
		mnuBook.add(mnuBookDetailShow);
		
		JMenu mnuMember = new JMenu("회원(M)");
		mnuMember.setMnemonic('M');
		menuBar.add(mnuMember);
		
		JMenuItem mnuMemberAdd = new JMenuItem("회원 추가...");
		mnuMemberAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberAdd winMemberAdd = new WinMemberAdd(typeAdd);
				winMemberAdd.setModal(true);
				winMemberAdd.setVisible(true);
			}
		});
		mnuMember.add(mnuMemberAdd);
		
		JMenuItem mnuMemberRemove = new JMenuItem("회원 삭제...");
		mnuMemberRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberRemove winMemberRemove = new WinMemberRemove(typeRemove);
				winMemberRemove.setModal(true);
				winMemberRemove.setVisible(true);
			}
		});
		mnuMember.add(mnuMemberRemove);
		
		JMenuItem mnuMemberModify = new JMenuItem("회원 변경...");
		mnuMemberModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberModify winMemberModify = new WinMemberModify(typeModify);
				winMemberModify.setModal(true);
				winMemberModify.setVisible(true);				
			}
		});
		mnuMember.add(mnuMemberModify);
		
		JMenuItem mnuMemberSelect = new JMenuItem("회원 조회...");
		mnuMember.add(mnuMemberSelect);
		
		mnuMember.addSeparator();
		
		JMenuItem mnuMemberDetailShow = new JMenuItem("회원 전체 보기...");
		mnuMember.add(mnuMemberDetailShow);
		
		JMenu mnuBorrow = new JMenu("대출(B)");
		mnuBorrow.setMnemonic('B');
		menuBar.add(mnuBorrow);
		
		JMenuItem mnuBorrow2 = new JMenuItem("대출");
		mnuBorrow.add(mnuBorrow2);
		
		JMenu mnuHelp = new JMenu("도움말(H)");
		mnuHelp.setMnemonic('H');
		menuBar.add(mnuHelp);
		
		JMenuItem mnuHelp2 = new JMenuItem("Help...");
		mnuHelp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinHelp winHelp = new WinHelp();
				winHelp.setModal(true);
				winHelp.setVisible(true);
			}
		});
		mnuHelp2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnuHelp2.setMnemonic('p');
		mnuHelp.add(mnuHelp2);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnBookAdd = new JButton("");
		btnBookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookInsert winBookInsert = new WinBookInsert();
				winBookInsert.setModal(true);
				winBookInsert.setVisible(true);
			}
		});
		btnBookAdd.setIcon(new ImageIcon(WinMain.class.getResource("/images/add.png")));
		toolBar.add(btnBookAdd);
		
		JButton btnBookRemove = new JButton("");
		btnBookRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(0); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					int rowIndex[] = table.getSelectedRows(); // 선택된 레코드들을 저장하는 배열				
					
					if(rowIndex.length > 1) {
						for(int i=0;i<rowIndex.length;i++) {
							WinBookDelete winBookDelete = 
									new WinBookDelete(table.getValueAt(rowIndex[i], 0).toString());
							winBookDelete.setModal(true);
							winBookDelete.setVisible(true);
						}
					}else {				
						WinBookDelete winBookDelete = 
								new WinBookDelete(table.getValueAt(table.getSelectedRow(), 0).toString());
						winBookDelete.setModal(true);
						winBookDelete.setVisible(true);
					}
				}
			}
		});
		btnBookRemove.setIcon(new ImageIcon(WinMain.class.getResource("/images/remove.png")));
		toolBar.add(btnBookRemove);
		
		JButton btnBookModify = new JButton("");
		btnBookModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() == -1) {
					WinComboSearch winComboSearch = new WinComboSearch(1); // 0:삭제 1:변경
					winComboSearch.setModal(true);
					winComboSearch.setVisible(true);
				}else {
					WinBookUpdate winBookUpdate = 
							new WinBookUpdate(table.getValueAt(table.getSelectedRow(), 0).toString());
					winBookUpdate.setModal(true);
					winBookUpdate.setVisible(true);
				}
			}
		});
		btnBookModify.setIcon(new ImageIcon(WinMain.class.getResource("/images/update.png")));
		toolBar.add(btnBookModify);
		
		JButton btnBookSelect = new JButton("");
		btnBookSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookAllShow winBookAllShow = new WinBookAllShow();
				//winBookAllShow.setModal(true);
				winBookAllShow.setVisible(true);
			}
		});
		btnBookSelect.setIcon(new ImageIcon(WinMain.class.getResource("/images/search.png")));
		toolBar.add(btnBookSelect);
		
		toolBar.addSeparator();
		
		JComboBox cbSearch = new JComboBox();
		cbSearch.setModel(new DefaultComboBoxModel(new String[] {"제목", "저자", "출판사", "회원이름", "전화번호"}));
		toolBar.add(cbSearch);
		
		tfSearch = new JTextField();
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					showSearchResult(cbSearch.getSelectedItem().toString(), tfSearch.getText());
				}
			}
		});
		tfSearch.setText("단어입력");
		tfSearch.setFont(new Font("굴림", Font.BOLD, 20));
		toolBar.add(tfSearch);
		tfSearch.setColumns(10);
		
		toolBar.addSeparator();
		
		JButton btnMemberAdd = new JButton("");
		btnMemberAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberAdd winMemberAdd = new WinMemberAdd(typeAdd);
				winMemberAdd.setModal(true);
				winMemberAdd.setVisible(true);
			}
		});
		btnMemberAdd.setIcon(new ImageIcon(WinMain.class.getResource("/images/memberAdd.png")));
		toolBar.add(btnMemberAdd);
		
		JButton btnMemberRemove = new JButton("");
		btnMemberRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberRemove winMemberRemove = new WinMemberRemove(typeRemove);
				winMemberRemove.setModal(true);
				winMemberRemove.setVisible(true);
			}
		});
		btnMemberRemove.setIcon(new ImageIcon(WinMain.class.getResource("/images/memberRemove.png")));
		toolBar.add(btnMemberRemove);
		
		JButton btnMemberModify = new JButton("");
		btnMemberModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberModify winMemberModify = new WinMemberModify(typeModify);
				winMemberModify.setModal(true);
				winMemberModify.setVisible(true);
			}
		});
		btnMemberModify.setIcon(new ImageIcon(WinMain.class.getResource("/images/memberUpdate.png")));
		toolBar.add(btnMemberModify);
		
		JButton btnMemberSelect = new JButton("");
		btnMemberSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMemberAllShow winMemberAllShow = new WinMemberAllShow();
				winMemberAllShow.setModal(true);
				winMemberAllShow.setVisible(true);
			}
		});
		btnMemberSelect.setIcon(new ImageIcon(WinMain.class.getResource("/images/memberSearch.png")));
		toolBar.add(btnMemberSelect);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String columnNames[] = {"ISBN","제목","저자","출판사","출판일","가격","수량"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames,0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}			
		};
		
		table = new JTable(dtm);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem mnBookBorrow = new JMenuItem("대여");
		mnBookBorrow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
		mnBookBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinInputID winInputID = new WinInputID();
				winInputID.setModal(true);
				winInputID.setVisible(true);
				
				String sID = winInputID.getID();
				String sISBN = table.getValueAt(table.getSelectedRow(), 0).toString();
				
				insertList(sID, sISBN);				
			}
		});
		popupMenu.add(mnBookBorrow);
		
		JMenuItem mnBookReturn = new JMenuItem("반납");
		mnBookReturn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
		mnBookReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinBookUpdate winBookUpdate = 
						new WinBookUpdate(table.getValueAt(table.getSelectedRow(), 0).toString());
				winBookUpdate.setModal(true);
				winBookUpdate.setVisible(true);
			}
		});
		popupMenu.add(mnBookReturn);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		
		lblISBN = new JLabel("현재 선택한 ISBN:");
		panel.add(lblISBN);
		
		
		//showTable();
	}

	protected void insertList(String sID, String sISBN) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "insert into listTBL values(null,'";
			sql = sql + sID + "','" + sISBN + "', curDate())";
			Statement stmt = con.createStatement();
			System.out.println(sql);
			
			stmt.executeUpdate(sql);
			
			//==============================================
			con.close();
		} catch (ClassNotFoundException e1) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}

	protected void showSearchResult(String sItem, String txtSearch) {
		// TODO Auto-generated method stub
		String sql="";
		int choice=0;
		if(sItem.equals("제목")) {
			sql = "select * from bookTBL where title like '%" + txtSearch + "%'";
			choice=0;
		}else if(sItem.equals("저자")) {
			sql = "select * from bookTBL where author like '%" + txtSearch + "%'";
			choice=0;
		}else if(sItem.equals("출판사")) {
			sql = "select * from bookTBL where publisher like '%" + txtSearch + "%'";
			choice=0;
		}else if(sItem.equals("회원이름")) {
			sql = "select * from memberTBL where name like '%" + txtSearch + "%'";
			choice=1;
		}else if(sItem.equals("전화번호")) {
			sql = "select * from memberTBL where mobile like '%" + txtSearch + "%'";
			choice=1;
		}		
		if(sItem.equals("제목") || sItem.equals("저자") || sItem.equals("출판사")) {
			String columnNames1[] = {"ISBN","제목","저자","출판사","출판일","가격","수량"};		
			DefaultTableModel dtm = new DefaultTableModel(columnNames1, 0);
			table.setModel(dtm);
		}else {
			String columnNames2[] = {"ID","회원명","전화번호","주소"};
			DefaultTableModel dtm = new DefaultTableModel(columnNames2,0);
			table.setModel(dtm);			
		}
		showResultRecords(sql, choice);
		
	}

	private void showResultRecords(String sql, int choice) {
		// TODO Auto-generated method stub
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				
			}

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Auto-generated method stub
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = 
							DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
					
					//=============================================		
					String sql2 = sql;					
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sql2);
					int total = 0;
					while(rs.next())
						total++;
					
					rs = stmt.executeQuery(sql);
					int count = 0;
					
					DefaultTableModel dtm = (DefaultTableModel)table.getModel();
					dtm.setRowCount(0);
					
					while(rs.next()) {
						count++;
						Vector <String> vec = new Vector<>();
						if(choice==0) {
							for(int i=1;i<=9;i++) {
								if(i!=6 && i!=8)
									vec.add(rs.getString(i));
							}
						}else {
							for(int i=1;i<=5;i++) {
								if(i!=2)
									vec.add(rs.getString(i));
							}
						}
						dtm.addRow(vec);
						Thread.sleep(10);
						progressBar.setValue(count*100/total);
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
				
				return null;
			} 
		};
		worker.execute();		
	}

	public WinMain(DefaultTableModel dtm) {
		// TODO Auto-generated constructor stub
		this();
		table.setModel(dtm);
	}

	private void showTable() {	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = 
					DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");
			
			//=============================================		
			String sql = "select * from bookTBL";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			String columnNames1[] = {"ISBN","제목","저자","출판사","출판일","가격","수량"};		
			DefaultTableModel dtm = new DefaultTableModel(columnNames1, 0);
			table.setModel(dtm);
			
			while(rs.next()) {
				Vector <String> vec = new Vector<>();
				for(int i=1;i<=9;i++) {
					if(i!=6 && i!=8)
						vec.add(rs.getString(i));
				}
				dtm.addRow(vec);
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
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JTable source = (JTable)e.getSource();
					int row = source.rowAtPoint(e.getPoint());
					int col = source.columnAtPoint(e.getPoint());
					if(!source.isRowSelected(row))
						source.changeSelection(row, col, false, false);
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					WinBookUpdate winBookUpdate = 
							new WinBookUpdate(table.getValueAt(table.getSelectedRow(), 0).toString());
					winBookUpdate.setModal(true);
					winBookUpdate.setVisible(true);
				}else {
					lblISBN.setText("현재 선택한 제목: " + table.getValueAt(table.getSelectedRow(), 1).toString() );
				}
			}
		});
	}
}
