package pl.mdrop.app;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pl.mdrop.database.ConnectDB;

public class Program {

	Connection connectToDb = null;
	
	private JFrame frame;
	private JTextField txtLogin;
	private JPasswordField pwdPassword;
	private int id = 0;

	/**
	 * Create the application.
	 */
	public Program() {
		initialize();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen settings
		
		/*
		 *  Create JFrame with panels
		 */
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.controlHighlight);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		frame.setBounds(100, 100, 800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		/*
		 * Frame location -> get screen settings user 
		 */
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (frame.getWidth() / 2), 
		                              middle.y - (frame.getHeight() / 2));
		frame.setLocation(newLocation);
		
		/*
		 *  Add Login and Password -> JPanel, JTextField and JLabels
		 */
		JPanel loginPanel = new JPanel();
		frame.getContentPane().add(loginPanel, "name_83890607175099");
		loginPanel.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLogin.setBounds(250, 283, 70, 30);
		loginPanel.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(250, 324, 80, 30);
		loginPanel.add(lblPassword);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(330, 285, 160, 30);
		loginPanel.add(txtLogin);
		txtLogin.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(330, 325, 160, 30);
		loginPanel.add(pwdPassword);
		
		/*
		 *  Add Buttons - Sign In & Close with actions 
		 */
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connectToDb = ConnectDB.helpDeskDB();
				String loginCache = txtLogin.getText();
				String passwordCache = new String(pwdPassword.getPassword());				
				checkLogin(loginCache, passwordCache);
			}
		});
		btnSignIn.setBounds(330, 380, 120, 30);
		btnSignIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginPanel.add(btnSignIn);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int closeAnswer = JOptionPane.showConfirmDialog(
						null, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION);
				if(closeAnswer == JOptionPane.YES_OPTION)
				{
					System.exit(JFrame.EXIT_ON_CLOSE);
				}	
			}
		});
		btnClose.setBounds(600, 500, 120, 30);
		btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginPanel.add(btnClose);
		
		// Add background to application
		JLabel lblLogobackground = new JLabel("");
		lblLogobackground.setIcon(new ImageIcon(Program.class.getResource("/img/background.png")));
		lblLogobackground.setBounds(0, 0, 794, 550);
		loginPanel.add(lblLogobackground);
		
		/*
		 * Menu Bar
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 794, 21);
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mnEdit.add(mntmCut);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mnEdit.add(mntmCopy);
		
		JMenuItem mntmPaste = new JMenuItem("Paste");
		mnEdit.add(mntmPaste);
		
		JMenu mnOptions = new JMenu("Database");
		menuBar.add(mnOptions);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnOptions.add(mntmSettings);
		
		JMenu mnInfo = new JMenu("Information");
		menuBar.add(mnInfo);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnInfo.add(mntmAbout);

	}
		
	/*
	 * Validate login & password are correct.
	 */
	private void checkLogin(String login, String password){
		
		// Read from database if login & password are correct
		try {
			String query = "select * from users where login = ? and password = ?";
			PreparedStatement pst = connectToDb.prepareStatement(query);
			pst.setString(1, login);
			pst.setString(2, password);
			ResultSet result = pst.executeQuery();
			int count = 0;
			
			// if query find a row with correct datas -> count == 1
			while(result.next()){
				count++;
			}
			
			if(count == 1){
				// temp values
				int isAdmin = 0;
				String isLogin = "";
				
				// Get from database information about login is Admin?
				String queryIsAdmin = "select id, login, isAdmin from users where login = ?";
				PreparedStatement pst1 = connectToDb.prepareStatement(queryIsAdmin);
				pst1.setString(1, login);
				ResultSet rs = pst1.executeQuery();
				
				while(rs.next()){
					isAdmin = rs.getInt("isAdmin");
					isLogin = rs.getString("login");
					id = rs.getInt("id");
					setId(id);
					System.out.println(id);
					System.out.println(isAdmin);
					System.out.println(isLogin);
					System.out.println("GetID(): " + getId());
				}
				// Logged as admin (if isAdmin == 1)
				if(isAdmin == 1){
					informationDialog("admin");
					frame.dispose();
					new UserGUI(getId(), login);
				}
				// Logged as user (if isAdmin != 1)
				else{
					informationDialog("user");
					frame.dispose();
					new UserGUI(getId(), login);
				}				
			}
			// Fields are empty -> throw dialog message
			else if(login.isEmpty() || password.isEmpty()){
				informationDialog("empty");
			}
			// Wrong Password
			else{
				informationDialog("error");
			}
			result.close();		// end result
			pst.close();        // end pst
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/*
	 * All notifications from check input text login & password.
	 */
	private void informationDialog(String info){
		if(info.equals("admin")){
			JOptionPane.showMessageDialog(null,
				    "Success! Logged as admin.");
		}
		else if(info.equals("user")) {
			JOptionPane.showMessageDialog(null,
				    "Success! Logged as user.");
		}
		else if(info.equals("empty")){
			JOptionPane.showMessageDialog(null,
				    "Fields cannot be empty!",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
		}
		else if(info.equals("error")){
			JOptionPane.showMessageDialog(null,
				    "Wrong Password!",
				    "Error!",
				    JOptionPane.ERROR_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null,
				    "ERROR PROGRAM");
		}
	}
}