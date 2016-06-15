import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Header {
	
	//Scanner for input
	Scanner input = new Scanner(System.in);
	
	//Variable for path
	String path;
	
	//Variable for encryption key
	int key;
	
	//Default Constructor
	public Header(){
	}
	
	//Initializes header
	public void initPrompt(){
		
		//Intro
		System.out.println("====================================");
		System.out.println("=======File Encryption Module=======");
		System.out.println("===========Spencer Gang=============");
		System.out.println("================v1.0================");
		System.out.println("====================================");
		System.out.println("Please paste full path to file and press enter:\n");
		
		//Grab Path
		path = input.nextLine();
		
		//Call for encryption key
		System.out.println("Please enter encryption key: ");
		
		//Grab Key
		key = input.nextInt();
		
		//User Verification
		System.out.println("Encrypting...");
	}
	
	public void encrypt(){
		
		//Variable for reading file
		int c;
		
		try{
			//File Reader
			FileReader inputStream = new FileReader(path);
			//File Writer
			FileWriter outputStream = new FileWriter(path + "_FEM");
			
			while((c = inputStream.read()) != -1){
				c *= key;
				outputStream.write(c);
			}
			
			inputStream.close();
			outputStream.close();
			
			System.out.println("Encryption Succesfull!");
			
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("File Not Found...");
		}
	}

}
