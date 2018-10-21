package bank;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
//class Server {

//}




public class driver {
	public static void functional(account source_account, account_database dbase) {
		while(true) {
			System.out.println("--------------------------------");
			System.out.println(source_account.getName() + " (For testing purposes only)");
			source_account.setAccountStatement();
			source_account.showAccountStatement();
			System.out.println("--------------------------------");

			//creates a unique auto code
			dbase.newAutoCode();
			source_account.setVerification_code(dbase.getVerification_code());

			//auto code is the key to my account
			dbase.getBase_map().put(source_account.getVerification_code(), source_account);
			
			String send_to_droid = source_account.getVerification_code().toString();
			System.out.println(send_to_droid);
		    try {
				collect(send_to_droid);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//SEND TO ANDRIOD: jacob_account.getVerification_code
			//GH SAYS Hey, I've sent over a verification code to your phone. Please say it back
			
			//I receive the verification attempt and compare it to the proper one
			
			//IF correct, tell google home that we are in correct and set boolean to true
			boolean googleHomeVerify = true;
			
			//IF not correct, tell google home that we are not correct and do not set boolean
			if(googleHomeVerify) {
				
				account unlocked_account = dbase.getBase_map().get(source_account.getVerification_code());
				
				//GH Says Hey, what do you want to do? You can add money, subtract, or show statement
				//also have ability to see how much i can donate
				
				String googleString = "Add Money";
				
				while(googleString == "Account Balance") {
					unlocked_account.setAccountStatement();
					unlocked_account.showAccountStatement(); //have google read it out
					
					//GH asks if they want to check statement again
					//if so, continue
					//if else, break
					break;
				}
				
				while(googleString == "Add Money") {
					//GH asks how much to add
					//convert that data to Integer
					Integer add_money = 50;
					unlocked_account.addIn(add_money);
					unlocked_account.setAccountAdd(add_money);
					unlocked_account.showAddStatement();
					
					//GH asks if they want to add more
					//if so, continue
					break;
				}
				
				while(googleString == "Subtract Money") {
					//GH asks how much to subtract
					Integer take_money = 50;
					unlocked_account.takeOut(take_money);
					unlocked_account.setAccountTake(take_money);
					unlocked_account.showTakeStatement();
					
					//GH asks if they want to subtract more
					//if so, continue
					break;
				}
				
				while(googleString == "Stop Hunger") {
					unlocked_account.mealsToSave();
					
					//GH asks if they want to stop more hunger
					//if so, continue
					break;
				}
				
			}
			//jacob_test_account now has proper information linked
			break;
		}

	}
	
	private static void collect(String args) throws IOException {
		ServerSocket listener = new ServerSocket(9090);

                Socket socket = listener.accept();

                	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                    out.println("Your verification code is:" + args);
                    socket.close();

            listener.close();
	}
	
	public static void main(String[] args) {
		

	    /**
	     * Runs the server.
	     */
	    	
	        
	    
		
		//create server to send to andriod application
		
		//create database of accounts
		account_database dbase = new account_database();
		
		//create unique accounts for each demo memeber/team member
		account jacob_account = new account();
		jacob_account.setName("Jacob");
		
		account tyler_account = new account();
		tyler_account.setName("Tyler");
		
		account takuma_account = new account();
		takuma_account.setName("Takuma");
		
		account arthur_account = new account();
		arthur_account.setName("Arthur");
		
		System.out.println("--------------------------------");
		System.out.println("For testing purposes only please");
		
		jacob_account.setAccountStatement();
		jacob_account.showAccountStatement();
		

		jacob_account.addIn(100);
		jacob_account.setAccountStatement();
		jacob_account.showAccountStatement();
		
		jacob_account.takeOut(100);
		jacob_account.setAccountStatement();
		jacob_account.showAccountStatement();
		System.out.println("--------------------------------");
		
		while(true) {
			
			//GH what is your name
			
			//I receive name put it in String name bucket
			String name = "Tyler";

			if(name == jacob_account.getName())//replace with IF GH hears proper name
				functional(jacob_account, dbase);
			else if(name == arthur_account.getName())
				functional(arthur_account, dbase);
			else if(name == takuma_account.getName())
				functional(takuma_account, dbase);
			else if(name == tyler_account.getName())
				functional(tyler_account, dbase);
			break;
		}
	}
}
