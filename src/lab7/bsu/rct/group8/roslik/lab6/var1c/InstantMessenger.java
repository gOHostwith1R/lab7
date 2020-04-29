package lab7.bsu.rct.group8.roslik.lab6.var1c;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class InstantMessenger {

	private MainFrame frame;
	private PrivateDialogFrame dialogFrame;
	private String sender;
	private DataBase dataBase = new DataBase();
	
	public DataBase getDataBase() {
		return dataBase;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public InstantMessenger(final MainFrame f) {
		this.frame = f;
		startServer();
	}
	
	public void sendMessage(User user, String message, PrivateDialogFrame frame) {
		this.dialogFrame = frame;
		try {
			if (message.isEmpty()) {
				JOptionPane.showMessageDialog(frame,
						"������� ����� ���������", "������",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
						
			final Socket socket = new Socket(user.getAddres(), this.frame.getServerPort());
			final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			out.writeUTF(this.sender);
			out.writeUTF(message);
			out.writeUTF(user.getName());
			out.writeUTF("true");
			socket.close();
			frame.getTextAreaIn().append("� -> (" + user.getAddres() + "): "
					+ message + "\n");
			frame.getTextAreaOut().setText("");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					"�� ������� ��������� ���������: ����-������� �� ������",
					"������", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					"�� ������� ��������� ���������", "������",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void sendMessage(String destinationName, String message) {
		try {
			if (destinationName.isEmpty()) {
				JOptionPane.showMessageDialog(frame,
						"������� ��� ����-����������", "������",
						JOptionPane.ERROR_MESSAGE);
				return;	
			}
			if (message.isEmpty()) {
				JOptionPane.showMessageDialog(frame,
						"������� ����� ���������", "������",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			User temp = null;
			if (dataBase.getUser(destinationName)!=null){
				temp = dataBase.getUser(destinationName);
				} else {
					JOptionPane.showMessageDialog(frame,
							"������ ������������ �� ����������", "������",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
					
			final Socket socket = new Socket(temp.getAddres(), frame.getServerPort());
			final DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			out.writeUTF(this.sender);
			out.writeUTF(message);
			out.writeUTF(temp.getName());
			out.writeUTF("false");
			socket.close();
			frame.getTextAreaIncoming().append("� -> (" + temp.getAddres() + "): " + message + "\n");
			frame.getTextAreaOutgoing().setText("");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					"�� ������� ��������� ���������: ����-������� �� ������",
					"������", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, 
					"�� ������� ��������� ���������", "������",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void startServer() {
		new Thread(new Runnable() {
			
			public void run() {
				try {
					final ServerSocket serverSocket = new ServerSocket(frame.getServerPort());
					while (!Thread.interrupted()) {
						final Socket socket = serverSocket.accept();
						final DataInputStream in = new DataInputStream(socket.getInputStream());
						final String senderName = in.readUTF();
						final String message = in.readUTF();
						final String name = in.readUTF();
						final String flag = in.readUTF();
						socket.close();
						final String address = ((InetSocketAddress) socket
													.getRemoteSocketAddress())
													.getAddress()
													.getHostAddress();
						if (flag.equals("true")){
							dialogFrame.getTextAreaIn().append(name + " -> (" + address + "): " +  "������, "+ senderName  + "!\n");
						
						}else{
						frame.getTextAreaIncoming().append(name + " -> (" + address + "): " +  "������, "+ senderName  + "!\n");
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(frame, "������ � ������ �������", "������", JOptionPane.ERROR_MESSAGE);
					}
				}
		}).start();
	}
	
	
}
