package lab7.bsu.rct.group8.roslik.lab6.var1c;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	private static final String FRAME_TITLE = "������ ���������� ���������";
	private static final int FRAME_MINIMUM_WIDTH = 500;
	private static final int FRAME_MINIMUM_HEIGHT = 500;
	private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
	private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
	private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
	private static final int SMALL_GAP = 5;
	private static final int MEDIUM_GAP = 10;
	private static final int LARGE_GAP = 15;
	private static final int SERVER_PORT = 4567;
	private final JTextField textFieldTo;
	private final JTextArea textAreaIncoming;
	private final JTextArea textAreaOutgoing;
	
	private InstantMessenger messenger;
	public InstantMessenger getMessenger() {
		return messenger;
	}
	
	public MainFrame() {
		super(FRAME_TITLE);
		setMinimumSize(new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));

		// ������������� ����
		final Toolkit kit = Toolkit.getDefaultToolkit();
		setLocation((kit.getScreenSize().width - getWidth()) / 2, (kit.getScreenSize().height - getHeight()) / 2);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu chatMenu = new JMenu("����");
		menuBar.add(chatMenu);
		
		Action logInAction = new AbstractAction("����") {
			public void actionPerformed(ActionEvent arg0) {
				String value = JOptionPane.showInputDialog(MainFrame.this, "������� ��� ��� �������", "����", JOptionPane.QUESTION_MESSAGE);
				messenger.setSender(value);
			}
		};
		
		Action findUserAction = new AbstractAction("����� ������������") {
			public void actionPerformed(ActionEvent arg0) {
				String value = JOptionPane.showInputDialog(MainFrame.this, "������� ��� ��� ������", "����� ������������", JOptionPane.QUESTION_MESSAGE);
				
				User user;
				if (messenger.getDataBase().getUser(value) == null) {
					JOptionPane.showMessageDialog(MainFrame.this, "������������ "+ value + " �� ����������", "������", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					user = messenger.getDataBase().getUser(value);
					JOptionPane.showMessageDialog(MainFrame.this, "������������ ������!\n "+ user.getName() + " ��������� � ���� ������.", "������������ "+ user.getName(), JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		};
		
		Action openPrivateDialogAction = new AbstractAction("������ ���������") {
			public void actionPerformed(ActionEvent arg0) {
				String value = JOptionPane.showInputDialog(MainFrame.this, "����: ", "����� ������������", JOptionPane.QUESTION_MESSAGE);
				
				User user;
				if (messenger.getDataBase().getUser(value) == null) {
					JOptionPane.showMessageDialog(MainFrame.this, "������������ "+ value + " �� ����������", "������", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					user = messenger.getDataBase().getUser(value);
				}
				PrivateDialogFrame dialogFrame = new PrivateDialogFrame(user, MainFrame.this);
			}
		};
		
		chatMenu.add(logInAction);
		chatMenu.add(findUserAction);
		chatMenu.add(openPrivateDialogAction);
		
		// ��������� ������� ��� ����������� ���������� ���������
		textAreaIncoming = new JTextArea(INCOMING_AREA_DEFAULT_ROWS, 0);
		textAreaIncoming.setEditable(false);
		
		// ���������, �������������� ��������� ��������� �������
		final JScrollPane scrollPaneIncoming = new JScrollPane(textAreaIncoming);
		
		// ������� �����
		final JLabel labelTo = new JLabel("����������:");
		
		// ���� ����� ����� ������������ � ������ ����������
		textFieldTo = new JTextField(TO_FIELD_DEFAULT_COLUMNS);
		
		// ��������� ������� ��� ����� ���������
		textAreaOutgoing = new JTextArea(OUTGOING_AREA_DEFAULT_ROWS, 0);
		
		// ���������, �������������� ��������� ��������� �������
		final JScrollPane scrollPaneOutgoing = new JScrollPane(textAreaOutgoing);
		
		// ������ ����� ���������
		final JPanel messagePanel = new JPanel();
		messagePanel.setBorder(BorderFactory.createTitledBorder("���������"));

		// ������ �������� ���������
		final JButton sendButton = new JButton("���������");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				messenger.sendMessage(textFieldTo.getText(), textAreaOutgoing.getText());
			}
		});

		// ���������� ��������� ������ "���������"
		final GroupLayout layout2 = new GroupLayout(messagePanel);
		messagePanel.setLayout(layout2);
		layout2.setHorizontalGroup(layout2.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout2.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout2.createSequentialGroup()
								.addGap(LARGE_GAP)
								.addComponent(labelTo)
								.addGap(SMALL_GAP)
								.addComponent(textFieldTo))
							.addComponent(scrollPaneOutgoing)
							.addComponent(sendButton))
				.addContainerGap());
			layout2.setVerticalGroup(layout2.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout2.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelTo)
						.addComponent(textFieldTo))
					.addGap(MEDIUM_GAP)
					.addComponent(scrollPaneOutgoing)
					.addGap(MEDIUM_GAP)
					.addComponent(sendButton)
				.addContainerGap());

		// ���������� ��������� ������
		final GroupLayout layout1 = new GroupLayout(getContentPane());
		setLayout(layout1);
		layout1.setHorizontalGroup(layout1.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout1.createParallelGroup()
						.addComponent(scrollPaneIncoming)
						.addComponent(messagePanel))
				.addContainerGap());
		layout1.setVerticalGroup(layout1.createSequentialGroup()
			.addContainerGap()
			.addComponent(scrollPaneIncoming)
			.addGap(MEDIUM_GAP)
			.addComponent(messagePanel)
			.addContainerGap());
		messenger = new InstantMessenger(this);
	}

	public JTextArea getTextAreaOutgoing() {
		return textAreaOutgoing;
	}

	public int getServerPort() {
		return SERVER_PORT;
	}

	public JTextArea getTextAreaIncoming() {
		return textAreaIncoming;
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}