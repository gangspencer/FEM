import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Header {

	//Global Variables
	String path;
	int key;
	
	//Default Constructor
	public Header(){
	}
	
	//Intro Prompt
	public void initPrompt(){
		System.out.println("====================================");
		System.out.println("=======File Encryption Module=======");
		System.out.println("===========Spencer Gang=============");
		System.out.println("================v1.1================");
		System.out.println("====================================");
		menuPrompt();
	}
	
	//Menu Prompt
	private void menuPrompt(){
		System.out.println("OPTIONS:");
		System.out.println("ENCRYPTION		1");
		System.out.println("DECRYPTION		2");
		System.out.println("EXIT			3");
		optionHandler();
	}
	
	//Menu Option Handler
	private void optionHandler(){
		Scanner input = new Scanner(System.in);
		int option = input.nextInt();

		if(option == 1){
			grabInfo();
			encrypt();
		}else if(option == 2){
			grabInfo();
			decrypt();
		}else if(option == 3){
			System.exit(0);
		}else{
			System.out.println("INVALID OPTION!!!");
			menuPrompt();
		}
	}
	
	private void grabInfo(){
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter path to file: ");
		path = input.nextLine();
		System.out.println("Please input key: ");
		key = input.nextInt();
	}
	
	//Encryption Method
	public void encrypt(){
		
		//Variable for reading file
		int c;
		
		try{
			FileReader inputStream = new FileReader(path);
			FileWriter outputStream = new FileWriter(path + "_FEM");
			
			while((c = inputStream.read()) != -1){
				c += key;
				outputStream.write(c);
			}
			
			inputStream.close();
			outputStream.close();
			
			System.out.println("Encryption Successful!");
			System.out.println("");
			
			//After successful encryption restart
			menuPrompt();
			
		}catch(IOException e){
			//In case of file not found exception
			//Warn user and restart
			System.out.println("File Not Found...");
			System.out.println("Restarting");
			System.out.println(".");
			System.out.println(".");
			System.out.println(".");
			menuPrompt();
			
		}
	}
	
	private void decrypt(){
		
		//Variable for chars
		int c;
		
		try{
			FileReader inputStream = new FileReader(path);
			FileWriter outputStream = new FileWriter("decrypted_file");
			
			while((c = inputStream.read()) != -1){
				c -= key;
				outputStream.write(c);
			}
			
			inputStream.close();
			outputStream.close();
			
			System.out.println("Decryption Successful!!!");
			System.out.println("");
			
			menuPrompt();
			
		}catch(IOException e){
			//In case of file not found exception
			//Warn user and restart
			System.out.println("File Not Found...");
			System.out.println("Restarting");
			System.out.println(".");
			System.out.println(".");
			System.out.println(".");
			menuPrompt();
		}
		
	}

}
