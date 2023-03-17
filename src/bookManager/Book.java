package bookManager;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Book extends JPanel {
	private JTextField tfISBN;
	private JTextField tfTitle;
	private JTextField tfAuthor;
	private JTextField tfPublisher;
	private JTextField tfPubDate;
	private JTextField tfPrice;
	private JLabel lblImage;
	private JTextArea taDescription;
	private JSpinner spinnerAmount;
	private String sPicture;
	public Book() {
		setLayout(null);
		
		lblImage = new JLabel("");
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					String sImage = "<html><img src='" + sPicture + "'></html>";
					JOptionPane.showMessageDialog(null, sImage,"전체 그림",JOptionPane.CLOSED_OPTION);
				}
			}
		});
		lblImage.setBackground(new Color(255, 255, 0));
		lblImage.setOpaque(true);
		lblImage.setToolTipText("더블클릭하면 원본화면이 보여요");
		lblImage.setBounds(12, 10, 150, 200);
		add(lblImage);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setBounds(174, 22, 57, 15);
		add(lblISBN);
		
		JLabel lblTitle = new JLabel("책제목:");
		lblTitle.setBounds(174, 52, 57, 15);
		add(lblTitle);
		
		JLabel lblAuthor = new JLabel("저자:");
		lblAuthor.setBounds(174, 82, 57, 15);
		add(lblAuthor);
		
		JLabel lblPublisher = new JLabel("출판사:");
		lblPublisher.setBounds(174, 112, 57, 15);
		add(lblPublisher);
		
		JLabel lblPubDate = new JLabel("출판일:");
		lblPubDate.setBounds(174, 142, 57, 15);
		add(lblPubDate);
		
		JLabel lblPrice = new JLabel("가격:");
		lblPrice.setBounds(174, 172, 57, 15);
		add(lblPrice);
		
		JLabel lblAmount = new JLabel("수량:");
		lblAmount.setBounds(174, 202, 57, 15);
		add(lblAmount);
		
		JLabel lblDescription = new JLabel("책 소개:");
		lblDescription.setBounds(12, 220, 57, 15);
		add(lblDescription);
		
		tfISBN = new JTextField();
		tfISBN.setColumns(10);
		tfISBN.setBounds(242, 19, 116, 21);
		add(tfISBN);
		
		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		tfTitle.setBounds(242, 49, 290, 21);
		add(tfTitle);
		
		tfAuthor = new JTextField();
		tfAuthor.setColumns(10);
		tfAuthor.setBounds(242, 79, 166, 21);
		add(tfAuthor);
		
		tfPublisher = new JTextField();
		tfPublisher.setColumns(10);
		tfPublisher.setBounds(242, 109, 116, 21);
		add(tfPublisher);
		
		tfPubDate = new JTextField();
		tfPubDate.setColumns(10);
		tfPubDate.setBounds(242, 139, 116, 21);
		add(tfPubDate);
		
		tfPrice = new JTextField();
		tfPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		tfPrice.setColumns(10);
		tfPrice.setBounds(242, 169, 116, 21);
		add(tfPrice);
		
		spinnerAmount = new JSpinner();
		spinnerAmount.setBounds(242, 199, 77, 22);
		add(spinnerAmount);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 240, 520, 325);
		add(scrollPane);
		
		taDescription = new JTextArea();
		taDescription.setLineWrap(true);
		scrollPane.setViewportView(taDescription);
		
	}
	public Book(String sISBN, String sTitle, String sAuthor, 
			String sPublisher, String sPubDate, String sImage, 
			String sPrice, String sDescription, String sAmount) {
		this();
		tfISBN.setText(sISBN);
		tfTitle.setText(sTitle);
		tfAuthor.setText(sAuthor);
		tfPublisher.setText(sPublisher);
		tfPubDate.setText(sPubDate);
		sImage = "<html><img src='" + sImage + "' width=150 height=200></html>";
		lblImage.setText(sImage);		
		sPrice = sPrice.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
		tfPrice.setText(sPrice + "원");		
		spinnerAmount.setValue(Integer.parseInt(sAmount));
		taDescription.setText(sDescription);
	}
	public Book(Vector<String> vec) {
		this();
		tfISBN.setText(vec.get(0));
		tfTitle.setText(vec.get(1));
		tfAuthor.setText(vec.get(2));
		tfPublisher.setText(vec.get(3));
		tfPubDate.setText(vec.get(4));
		String sImage = "<html><img src='" + vec.get(5) + "' width=150 height=200></html>";
		sPicture = vec.get(5);
		
		lblImage.setText(sImage);		
		String sPrice = vec.get(6).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
		tfPrice.setText(sPrice + "원");
		taDescription.setText(vec.get(7));
		spinnerAmount.setValue(Integer.parseInt(vec.get(8)));		
	}

}
