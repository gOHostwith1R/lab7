package lab7.bsu.rct.group8.roslik.lab6.var1c;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataBase {

	private ArrayList<User> users = new ArrayList<>(10);
	
	public DataBase() {
		readData();
	}
	
	private void readData() {
		try {
			File file = new File("DataBase.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null){
				String name = line;
				line = reader.readLine();
				String addres = line;
				users.add(new User(name, addres));			
			}
			reader.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public User getUser(String findName) {
		for (User user: users){
			if (findName.equals(user.getName())){
				return user;
			}
		}
		return null;
	}
	
}
