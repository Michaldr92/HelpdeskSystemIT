package pl.mdrop.app;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pl.mdrop.database.ConnectDB;

public class UserGUI {

	Connection connectToDb = null;
	private JFrame frmHelpdeskIt;
	private JTextField textField;
	private JPanel panelTask, panelMyTask, 
				   panelMySoftware, panelMyDevices;
	private JLabel lblName, lblSurname, lblEmail, 
				   lblDepartment, lblPosition, lblOffice, lblUserimage;
	private int id = 0;
	private String login = "";

	/**
	 * Create the application. - default constructor
	 */
	public UserGUI() {
		initialize();
	}
	
	/*
	 * Second constructor get id and login to keep session and get information from database
	 */
	
	/**
	 * @wbp.parser.constructor
	 */
	public UserGUI(int id, String login){
		this.id = id;
		this.login = login;
		initialize();
		getUserInfo(id);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/*
		 * Create second frame with users option
		 */
		frmHelpdeskIt = new JFrame();
		frmHelpdeskIt.setTitle("Helpdesk IT");
		frmHelpdeskIt.setBounds(100, 100, 800, 600);
		frmHelpdeskIt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHelpdeskIt.getContentPane().setLayout(new CardLayout(0, 0));
		frmHelpdeskIt.setVisible(true);
		frmHelpdeskIt.setResizable(false);
		frmHelpdeskIt.setLocationRelativeTo(null);

		// Main panel to add others
		JPanel panelAll = new JPanel();
		frmHelpdeskIt.getContentPane().add(panelAll, "name_218556506364138");
		panelAll.setLayout(null);
		
		
		/*
		 * Create JMenuBar with JMenuItems
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 795, 21);
		frmHelpdeskIt.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JSeparator separator_3 = new JSeparator();
		mnFile.add(separator_3);
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mnFile.add(mntmRefresh);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmFind = new JMenuItem("Find");
		mnEdit.add(mntmFind);
		
		JSeparator separator_1 = new JSeparator();
		mnEdit.add(separator_1);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mnEdit.add(mntmCut);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mnEdit.add(mntmCopy);
		
		JMenuItem mntmPaste = new JMenuItem("Paste");
		mnEdit.add(mntmPaste);
		
		JSeparator separator = new JSeparator();
		mnEdit.add(separator);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mnEdit.add(mntmDelete);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnOptions.add(mntmSettings);
		
		JMenu mnInformation = new JMenu("Information");
		menuBar.add(mnInformation);
		
		JMenuItem mntmAboutAuthor = new JMenuItem("About Author");
		mnInformation.add(mntmAboutAuthor);
		
		JMenuItem mntmLicense = new JMenuItem("License");
		mnInformation.add(mntmLicense);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mnHelp.add(mntmHelp);
		
		/*
		 * Add buttons to Panel all
		 */
		JButton buttonNewTask = new JButton("");
		buttonNewTask.setToolTipText("Add Task");
		Image addTaskIMG = new ImageIcon(this.getClass().getResource("/img/addTask.png")).getImage(); 
		buttonNewTask.setIcon(new ImageIcon(addTaskIMG));
		buttonNewTask.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonNewTask.setForeground(new Color(0,0,0));
		buttonNewTask.setBounds(80, 70, 100, 100);
		buttonNewTask.setFont(new Font("Verdara", Font.BOLD, 16));
		buttonNewTask.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				panelAll.setVisible(false);
				panelTask.setVisible(true);
			}
		});
		panelAll.add(buttonNewTask);
		
		JButton buttonMyTask = new JButton("");
		buttonMyTask.setToolTipText("My Tasks");
		Image myTaskIMG = new ImageIcon(this.getClass().getResource("/img/myTask.png")).getImage(); 
		buttonMyTask.setIcon(new ImageIcon(myTaskIMG));
		buttonMyTask.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonMyTask.setForeground(new Color(0,0,0));
		buttonMyTask.setBounds(220, 70, 100, 100);
		buttonMyTask.setFont(new Font("Verdara", Font.BOLD, 16));
		buttonMyTask.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				panelAll.setVisible(false);
				panelMyTask.setVisible(true);								
			}
		});
		panelAll.add(buttonMyTask);
		
		JButton buttonMyDevices = new JButton("");
		buttonMyDevices.setToolTipText("My Devices");
		Image myDevicesIMG = new ImageIcon(this.getClass().getResource("/img/myDevices.png")).getImage(); 
		buttonMyDevices.setIcon(new ImageIcon(myDevicesIMG));
		buttonMyDevices.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonMyDevices.setForeground(new Color(0,0,0));
		buttonMyDevices.setBounds(80, 205, 100, 100);
		buttonMyDevices.setFont(new Font("Verdara", Font.BOLD, 16));
		buttonMyDevices.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				panelAll.setVisible(false);
				panelMyDevices.setVisible(true);				
			}
		});
		panelAll.add(buttonMyDevices);
		
		JButton buttonMySoftware = new JButton("");
		buttonMySoftware.setToolTipText("My Software");				
		Image mySoftwareIMG = new ImageIcon(this.getClass().getResource("/img/mySoftware.png")).getImage();
		buttonMySoftware.setIcon(new ImageIcon(mySoftwareIMG));	
		buttonMySoftware.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonMySoftware.setForeground(new Color(0,0,0));
		buttonMySoftware.setBounds(220, 205, 100, 100);
		buttonMySoftware.setFont(new Font("Verdara", Font.BOLD, 16));
		buttonMySoftware.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				panelAll.setVisible(false);
				panelMySoftware.setVisible(true);				
			}
		});
		panelAll.add(buttonMySoftware);
		
		JButton buttonLogout = new JButton("");
		buttonLogout.setToolTipText("Sign Out");
		Image logoutIMG = new ImageIcon(this.getClass().getResource("/img/logout.png")).getImage(); 
		buttonLogout.setIcon(new ImageIcon(logoutIMG));
		buttonLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonLogout.setForeground(new Color(0,0,0));
		buttonLogout.setBounds(80, 400, 100, 100);
		buttonLogout.setFont(new Font("Verdara", Font.BOLD, 16));
		buttonLogout.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				frmHelpdeskIt.dispose();
				JOptionPane.showMessageDialog(null, "Logout successful!");
				new Program();
			}
		});
		panelAll.add(buttonLogout);
		
		/*
		 * Separator buttons and informations
		 */
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(400, 70, 15, 430);
		panelAll.add(separator_4);
		
		/*
		 * Information to change from datas database
		 */
		lblName = new JLabel("Name");
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblName.setBounds(555, 282, 100, 14);
		panelAll.add(lblName);
		
		lblSurname = new JLabel("Surname");
		lblSurname.setForeground(Color.WHITE);
		lblSurname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSurname.setBounds(555, 307, 100, 14);
		panelAll.add(lblSurname);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(555, 332, 140, 14);
		panelAll.add(lblEmail);
		
		lblDepartment = new JLabel("Department");
		lblDepartment.setForeground(Color.WHITE);
		lblDepartment.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDepartment.setBounds(555, 357, 100, 14);
		panelAll.add(lblDepartment);
		
		lblPosition = new JLabel("Position");
		lblPosition.setForeground(Color.WHITE);
		lblPosition.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPosition.setBounds(555, 382, 100, 14);
		panelAll.add(lblPosition);
		
		lblOffice = new JLabel("Office");
		lblOffice.setForeground(Color.WHITE);
		lblOffice.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOffice.setBounds(555, 407, 100, 14);
		panelAll.add(lblOffice);
		
		lblUserimage = new JLabel("");
		lblUserimage.setIcon(new ImageIcon(UserGUI.class.getResource("/img/user.png")));
		lblUserimage.setBounds(530, 80, 128, 143);
		panelAll.add(lblUserimage);
		
		// Set application background in all panels
		JLabel lblBackgroundall = new JLabel("");
		lblBackgroundall.setIcon(new ImageIcon(UserGUI.class.getResource("/img/background1.png")));
		lblBackgroundall.setBounds(0, 0, 795, 550);
		panelAll.add(lblBackgroundall);
		
		/*
		 * Panel add new request
		 */
		panelTask = new JPanel();
		frmHelpdeskIt.getContentPane().add(panelTask, "name_218567310779840");
		panelTask.setLayout(null);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(90, 90, 80, 14);
		panelTask.add(lblTitle);
		
		JLabel lblPriority = new JLabel("Priority:");
		lblPriority.setForeground(Color.WHITE);
		lblPriority.setBounds(90, 130, 80, 14);
		panelTask.add(lblPriority);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(180, 85, 500, 23);
		panelTask.add(textField);
		textField.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setForeground(Color.WHITE);
		lblDescription.setBounds(90, 250, 80, 14);
		panelTask.add(lblDescription);
		
		JLabel lbCategory = new JLabel("Category:");
		lbCategory.setForeground(Color.WHITE);
		lbCategory.setBounds(90, 165, 80, 14);
		panelTask.add(lbCategory);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBounds(90, 200, 80, 14);
		panelTask.add(lblStatus);
		
		JLabel lblNewTask = new JLabel("Add request");
		lblNewTask.setForeground(Color.WHITE);
		lblNewTask.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewTask.setFont(new Font("Verdana", Font.BOLD, 24));
		lblNewTask.setBounds(10, 30, 774, 29);
		panelTask.add(lblNewTask);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSubmit.setBounds(376, 461, 100, 30);
		panelTask.add(btnSubmit);
		
		JButton btnBack = new JButton("Back");
		btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelTask.setVisible(false);
				panelAll.setVisible(true);
			}
		});
		btnBack.setBounds(650, 500, 100, 30);
		panelTask.add(btnBack);
		
		JTextArea textDescription = new JTextArea();
		textDescription.setBounds(180, 250, 500, 200);
		panelTask.add(textDescription);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(180, 125, 200, 20);
		panelTask.add(comboBox);
		
		ButtonGroup radioBtnOptions = new ButtonGroup();
		
		JRadioButton rdbtnHardware = new JRadioButton("Hardware");
		rdbtnHardware.setBackground(SystemColor.windowBorder);
		rdbtnHardware.setForeground(Color.WHITE);
		rdbtnHardware.setBounds(180, 160, 80, 25);
		radioBtnOptions.add(rdbtnHardware);
		panelTask.add(rdbtnHardware);
		
		JRadioButton rdbtnSoftware = new JRadioButton("Software");
		rdbtnSoftware.setForeground(Color.WHITE);
		rdbtnSoftware.setBackground(SystemColor.windowBorder);
		rdbtnSoftware.setBounds(262, 160, 74, 25);
		radioBtnOptions.add(rdbtnSoftware);
		panelTask.add(rdbtnSoftware);
		
		JRadioButton rdbtnOther = new JRadioButton("Other");
		rdbtnOther.setForeground(Color.WHITE);
		rdbtnOther.setBackground(SystemColor.windowBorder);
		rdbtnOther.setBounds(338, 160, 53, 25);
		radioBtnOptions.add(rdbtnOther);
		panelTask.add(rdbtnOther);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(180, 200, 200, 20);
		panelTask.add(comboBox_1);
		
		JLabel lblBackgroundtask = new JLabel("");
		lblBackgroundtask.setIcon(new ImageIcon(UserGUI.class.getResource("/img/background1.png")));
		lblBackgroundtask.setBounds(0, 0, 794, 550);
		panelTask.add(lblBackgroundtask);
		
		/*
		 * Panel - My Task
		 */
		panelMyTask = new JPanel();
		frmHelpdeskIt.getContentPane().add(panelMyTask, "name_218578712986622");
		panelMyTask.setLayout(null);
		
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMyTask.setVisible(false);
				panelAll.setVisible(true);				
			}
		});
		btnBack_1.setBounds(650, 500, 100, 30);
		panelMyTask.add(btnBack_1);
		
		JLabel lblMyRequests = new JLabel("My requests");
		lblMyRequests.setForeground(Color.WHITE);
		lblMyRequests.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
		lblMyRequests.setBounds(328, 20, 150, 30);
		panelMyTask.add(lblMyRequests);
		
		JLabel lblBackgroundmytask = new JLabel("");
		lblBackgroundmytask.setIcon(new ImageIcon(UserGUI.class.getResource("/img/background1.png")));
		lblBackgroundmytask.setBounds(0, 0, 795, 560);
		panelMyTask.add(lblBackgroundmytask);
		
		/*
		 * Panel - My Software
		 */
		panelMySoftware = new JPanel();
		frmHelpdeskIt.getContentPane().add(panelMySoftware, "name_218590026900741");
		panelMySoftware.setLayout(null);
		
		JButton btnBack_2 = new JButton("Back");
		btnBack_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMySoftware.setVisible(false);
				panelAll.setVisible(true);				
			}
		});
		btnBack_2.setBounds(650, 500, 100, 30);
		panelMySoftware.add(btnBack_2);
		
		JLabel lblBackgroundsoftware = new JLabel("");
		lblBackgroundsoftware.setIcon(new ImageIcon(UserGUI.class.getResource("/img/background1.png")));
		lblBackgroundsoftware.setBounds(0, 0, 794, 550);
		panelMySoftware.add(lblBackgroundsoftware);
		
		/*
		 * Panel - My Devices
		 */
		panelMyDevices = new JPanel();
		frmHelpdeskIt.getContentPane().add(panelMyDevices, "name_218598029981563");
		
		JButton btnBack_3 = new JButton("Back");
		btnBack_3.setBounds(650, 500, 100, 30);
		btnBack_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBack_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMyDevices.setVisible(false);
				panelAll.setVisible(true);	
			}
		});
		panelMyDevices.setLayout(null);
		panelMyDevices.add(btnBack_3);
		
		JLabel lblImglaptop = new JLabel("");
		lblImglaptop.setIcon(new ImageIcon(UserGUI.class.getResource("/devices/laptop.png")));
		lblImglaptop.setBounds(40, 30, 128, 118);
		panelMyDevices.add(lblImglaptop);
		
		JLabel lblImgscreen = new JLabel("");
		lblImgscreen.setIcon(new ImageIcon(UserGUI.class.getResource("/devices/screen.png")));
		lblImgscreen.setBounds(40, 190, 128, 118);
		panelMyDevices.add(lblImgscreen);
		
		JLabel lblImgphone = new JLabel("");
		lblImgphone.setIcon(new ImageIcon(UserGUI.class.getResource("/devices/mobile.png")));
		lblImgphone.setBounds(40, 340, 128, 120);
		panelMyDevices.add(lblImgphone);
		
		JLabel lblImgstorage = new JLabel("");
		lblImgstorage.setIcon(new ImageIcon(UserGUI.class.getResource("/devices/storage.png")));
		lblImgstorage.setBounds(430, 30, 128, 128);
		panelMyDevices.add(lblImgstorage);
		
		JLabel lblImgkeyboard = new JLabel("");
		lblImgkeyboard.setIcon(new ImageIcon(UserGUI.class.getResource("/devices/keyboard.png")));
		lblImgkeyboard.setBounds(430, 180, 128, 128);
		panelMyDevices.add(lblImgkeyboard);
		
		JLabel lblImgmouse = new JLabel("");
		lblImgmouse.setIcon(new ImageIcon(UserGUI.class.getResource("/devices/mouse.png")));
		lblImgmouse.setBounds(430, 340, 128, 128);
		panelMyDevices.add(lblImgmouse);
		
		JLabel lblModel = new JLabel("Model:");
		lblModel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblModel.setForeground(Color.WHITE);
		lblModel.setBounds(178, 40, 50, 14);
		panelMyDevices.add(lblModel);
		
		JLabel lblSn = new JLabel("S/N:");
		lblSn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSn.setForeground(Color.WHITE);
		lblSn.setBounds(178, 60, 50, 14);
		panelMyDevices.add(lblSn);
		
		JLabel lblOs = new JLabel("OS:");
		lblOs.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOs.setForeground(Color.WHITE);
		lblOs.setBounds(178, 80, 46, 14);
		panelMyDevices.add(lblOs);
		
		JLabel lblCpu = new JLabel("CPU:");
		lblCpu.setForeground(Color.WHITE);
		lblCpu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCpu.setBounds(178, 100, 46, 14);
		panelMyDevices.add(lblCpu);
		
		JLabel lblRam = new JLabel("RAM:");
		lblRam.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRam.setForeground(Color.WHITE);
		lblRam.setBounds(178, 120, 46, 14);
		panelMyDevices.add(lblRam);
		
		JLabel lblScreenModel = new JLabel("Model:");
		lblScreenModel.setForeground(Color.WHITE);
		lblScreenModel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblScreenModel.setBounds(178, 190, 50, 14);
		panelMyDevices.add(lblScreenModel);
		
		JLabel lblSnScreen = new JLabel("S/N:");
		lblSnScreen.setForeground(Color.WHITE);
		lblSnScreen.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSnScreen.setBounds(178, 210, 50, 14);
		panelMyDevices.add(lblSnScreen);
		
		JLabel lblResolution = new JLabel("Resolution:");
		lblResolution.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblResolution.setForeground(Color.WHITE);
		lblResolution.setBounds(178, 230, 70, 14);
		panelMyDevices.add(lblResolution);
		
		JLabel lblModelMobile = new JLabel("Model:");
		lblModelMobile.setForeground(Color.WHITE);
		lblModelMobile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblModelMobile.setBounds(178, 345, 50, 14);
		panelMyDevices.add(lblModelMobile);
		
		JLabel lblSnMobile = new JLabel("S/N:");
		lblSnMobile.setForeground(Color.WHITE);
		lblSnMobile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSnMobile.setBounds(178, 365, 50, 14);
		panelMyDevices.add(lblSnMobile);
		
		JLabel lblOsMobile = new JLabel("OS:");
		lblOsMobile.setForeground(Color.WHITE);
		lblOsMobile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOsMobile.setBounds(178, 385, 46, 14);
		panelMyDevices.add(lblOsMobile);
		
		JLabel lblCpuMobile = new JLabel("CPU:");
		lblCpuMobile.setForeground(Color.WHITE);
		lblCpuMobile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCpuMobile.setBounds(178, 405, 46, 14);
		panelMyDevices.add(lblCpuMobile);
		
		JLabel lblRamMobile = new JLabel("RAM:");
		lblRamMobile.setForeground(Color.WHITE);
		lblRamMobile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRamMobile.setBounds(178, 425, 46, 14);
		panelMyDevices.add(lblRamMobile);
		
		JLabel lblNumberMobile = new JLabel("Tel. no.:");
		lblNumberMobile.setForeground(Color.WHITE);
		lblNumberMobile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNumberMobile.setBounds(178, 445, 70, 14);
		panelMyDevices.add(lblNumberMobile);
		
		JLabel lblStorageModel = new JLabel("Model:");
		lblStorageModel.setForeground(Color.WHITE);
		lblStorageModel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStorageModel.setBounds(568, 40, 50, 14);
		panelMyDevices.add(lblStorageModel);
		
		JLabel lblSnStorage = new JLabel("S/N:");
		lblSnStorage.setForeground(Color.WHITE);
		lblSnStorage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSnStorage.setBounds(568, 60, 50, 14);
		panelMyDevices.add(lblSnStorage);
		
		JLabel lblTypeStorage = new JLabel("Type:");
		lblTypeStorage.setForeground(Color.WHITE);
		lblTypeStorage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTypeStorage.setBounds(568, 80, 46, 14);
		panelMyDevices.add(lblTypeStorage);
		
		JLabel lblCapacity = new JLabel("Capacity:");
		lblCapacity.setForeground(Color.WHITE);
		lblCapacity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCapacity.setBounds(568, 100, 55, 14);
		panelMyDevices.add(lblCapacity);
		
		JLabel lblModelKey = new JLabel("Model:");
		lblModelKey.setForeground(Color.WHITE);
		lblModelKey.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblModelKey.setBounds(568, 225, 50, 14);
		panelMyDevices.add(lblModelKey);
		
		JLabel lblSnKey = new JLabel("S/N:");
		lblSnKey.setForeground(Color.WHITE);
		lblSnKey.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSnKey.setBounds(568, 245, 50, 14);
		panelMyDevices.add(lblSnKey);
		
		JLabel lblModelMouse = new JLabel("Model:");
		lblModelMouse.setForeground(Color.WHITE);
		lblModelMouse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblModelMouse.setBounds(568, 350, 50, 14);
		panelMyDevices.add(lblModelMouse);
		
		JLabel lblSnMouse = new JLabel("S/N:");
		lblSnMouse.setForeground(Color.WHITE);
		lblSnMouse.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSnMouse.setBounds(568, 370, 50, 14);
		panelMyDevices.add(lblSnMouse);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBounds(390, 40, 3, 450);
		panelMyDevices.add(separator_5);
		
		JLabel lblBackgrounddevices = new JLabel("");
		lblBackgrounddevices.setBounds(0, 0, 794, 550);
		lblBackgrounddevices.setIcon(new ImageIcon(UserGUI.class.getResource("/img/background1.png")));
		panelMyDevices.add(lblBackgrounddevices);
		
	}
	
	// Functions
	
	/*
	 * Get all user info from DB
	 */
	private void getUserInfo(int id){
		
		connectToDb = ConnectDB.helpDeskDB();
		
		try {
			String query = "select name, surname, email, department, position, office from users where id = '"+id+"'";
			PreparedStatement pst = connectToDb.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				lblName.setText(rs.getString("name"));
				lblSurname.setText(rs.getString("surname"));
				lblEmail.setText(rs.getString("email"));
				lblDepartment.setText(rs.getString("department"));
				lblPosition.setText(rs.getString("position"));
				lblOffice.setText(rs.getString("office"));
			}
		rs.close();
		pst.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
						
	}
	
	/*
	 * Get all users requests from DB
	 */
	private void getUserRequest(int id){
		
	}
	
	/*
	 * Get all users devices from DB
	 */
	private void getUserDevices(int id){
		
		connectToDb = ConnectDB.helpDeskDB();
		
		try {
			String query = "SELECT u.id, u.name, u.surname, d.name, d.sn, d.os, d.cpu, d.ram
					FROM user_devices AS ud
					INNER JOIN users AS u
					ON ud.user_id = u.id
					INNER JOIN devices AS d
					ON ud.device_id = d.id
					WHERE u.id ='"+id+"' ";
			PreparedStatement pst = connectToDb.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
			// Display all user's devices
			}
			rs.close();
			pst.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Get all users software from DB
	 */
	private void getUserSoftware(int id){
		
	}
	
	/*
	 * Add request to DB
	 */
	private void addUserRequest(int id){
		
	}
}
