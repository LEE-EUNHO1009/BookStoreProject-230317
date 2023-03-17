package bookManager;

import java.awt.EventQueue;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;

public class WinBookAllShow extends JDialog implements PropertyChangeListener {
	
	private ProgressMonitor progressMonitor;
	private Task task;
	
	class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() throws Exception {
            int progress = 0;
            setProgress(0);
            try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = 
						DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root","1234");			
				//=============================================		
				String sql = "select count(*) from bookTBL"; 
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				int total=0;
				if(rs.next()) 
					total = rs.getInt(1);
				
				sql = "select * from bookTBL";
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				int count=0;
				while(rs.next() && progress < 100 && !isCancelled()) {
					count++;
					Vector<String> vec = new Vector<>();
					for(int i=1;i<=9;i++)
						vec.add(rs.getString(i));
					tabbedPane.add(rs.getString("Title"), new Book(vec));
					
					tabbedPane.setSelectedIndex(count-1);
					
					Thread.sleep(100);
					
					progress = 100*count/total;
                    setProgress(Math.min(progress, 100));
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
    }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinBookAllShow dialog = new WinBookAllShow();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JTabbedPane tabbedPane;

	/**
	 * Create the dialog.
	 */
	public WinBookAllShow() {
		setTitle("모든 도서 보기(ProgressMonitor)");
		setBounds(100, 100, 598, 652);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);		
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		
		progressMonitor = new ProgressMonitor(WinBookAllShow.this,
                "Running a Long Task",
                "", 0, 100);
		progressMonitor.setProgress(0);
		task = new Task();
		task.addPropertyChangeListener((PropertyChangeListener)this);
		task.execute();		
		//showAllbooks();
	}	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message =
                String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
            
            if(progressMonitor.isCanceled())
            	task.cancel(true);
            else if(task.isDone())
            	JOptionPane.showMessageDialog(null, "Task Completed!!!");
        }
	}

}
