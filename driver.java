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
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class driver {
	public static void functional(account source_account, account_database dbase, Scanner kb) {
		while(true) {
			/*
			System.out.println("--------------------------------");
			System.out.println(source_account.getName() + " (For testing purposes only)");
			source_account.setAccountStatement();
			source_account.showAccountStatement();
			System.out.println("--------------------------------");
			*/
			
			//creates a unique auto code
			dbase.newAutoCode();
			source_account.setVerification_code(dbase.getVerification_code());

			//auto code is the key to my account
			dbase.getBase_map().put(source_account.getVerification_code(), source_account);
			String send_to_droid = source_account.getVerification_code().toString();
			System.out.println(send_to_droid);
			sending to android app
		    
			
			
			try {
				collect(send_to_droid);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    
			//GH SAYS Hey, I've sent over a verification code to your phone. Please say it back
			System.out.println("I've sent over a verification code to your phone. Please enter it below:");

		    boolean googleHomeVerify = false;
		    int tries = 3;
		    while(true) {
			    String verify_code = kb.nextLine();
		    	if(verify_code.equals(send_to_droid)) {
		    		googleHomeVerify = true;
		    		break;
		    	}
		    	else {
		    		tries--;
		    		System.out.println("Wrong secure authentification key. Please try again.");
		    	}
		    	if(tries == 0) {
		    		System.out.println("You have been locked out of your account. Try again in 10 seconds.");
		            final Timer timer = new Timer();
		            timer.scheduleAtFixedRate(new TimerTask() {
		                int i = 10;
		                public void run() {
		                    System.out.print(i--);
		                    System.out.print("...");
		                    if (i< 0) {
		                        timer.cancel();
		                        System.out.println();
		    		            System.out.println("Please try again.");
		                    }
		                }
		            }, 0, 1000);
		            tries = 3;
		            continue;
		    	}
		    }
		    
			//I receive the verification attempt and compare it to the proper one
			
			//IF correct, tell google home that we are in correct and set boolean to true
			
			//IF not correct, tell google home that we are not correct and do not set boolean
		    if(googleHomeVerify) {
				System.out.println("Welcome to Phat Elves Secure Banking. Here, you can transfer money from "
						+ "checking account to savings account or deposit money from savings acount "
						+ "into your checking account.");
				System.out.println("Message of the Day: You can also see how many meals you can provide "
						+ "by donating to Feeding America, a nonprofit organization with more than 200 "
						+ "food banks.");
			while(true) {
				System.out.println("Enter Request:");
				account unlocked_account = dbase.getBase_map().get(source_account.getVerification_code());
				
				//GH Says Hey, what do you want to do? You can add money, subtract, or show statement

				//also have ability to see how much i can donate
				
				
				String googleString = kb.nextLine();
				
				while(googleString.equals("Account Balance")) {
					unlocked_account.setAccountStatement();
					unlocked_account.showAccountStatement(); //have google read it out
					
					//GH asks if they want to check statement again
					//if so, continue
					//if else, break
					System.out.println("Would you like to see your account balance again? (Yes/No)");
					String more = kb.nextLine();
					
					if(more.equals("Yes"))
						continue;
					else if(more.equals("No"))
						break;
				}
				
				while(googleString.equals("Deposit")) {
					//GH asks how much to add
					//convert that data to Integer
					Integer add_money = 50;
					unlocked_account.addIn(add_money);
					unlocked_account.setAccountAdd(add_money);
					unlocked_account.showAddStatement();
					
					//GH asks if they want to add more
					//if so, continue
					System.out.println("Would you like to despot more? (Yes/No)");
					String more = kb.nextLine();
					
					if(more.equals("Yes"))
						continue;
					else if(more.equals("No"))
						break;
				}
				
				while(googleString.equals("Withdraw")) {
					//GH asks how much to subtract
					Integer take_money = 50;
					unlocked_account.takeOut(take_money);
					unlocked_account.setAccountTake(take_money);
					unlocked_account.showTakeStatement();
					
					//GH asks if they want to subtract more
					//if so, continue
					System.out.println("Would you like to withdraw more? (Yes/No)");
					String more = kb.nextLine();
					
					if(more.equals("Yes"))
						continue;
					else if(more.equals("No"))
						break;
				}
				
				while(googleString.equals("Stop Hunger")) {
					unlocked_account.mealsToSave();
					
					System.out.println("Would you like donate to Feeding America or another organization"
							+ "to help prevent world hunger? (Yes/No)");
					String more = kb.nextLine();
					
					if(more.equals("Yes")) {
						System.out.println("Head to the following link sent to your phone and input your Zip code to "
								+ "easily locate the nearest local food bank to you!");
						String hyper = ("https://www.feedingamerica.org/find-your-local-foodbank");
						try {
							outputApp(hyper);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Thanks for your donation and service to our connected community.");
						break;
					}
					else if(more.equals("No"))
						break;
				}
				
			}
			//jacob_test_account now has proper information linked
			//break;
		}
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
	
	private static void outputApp(String args) throws IOException {
		ServerSocket listener = new ServerSocket(9090);

                Socket socket = listener.accept();

                	PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                    out.println( args);
                    socket.close();

            listener.close();
	}
	
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
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
	/*	
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
		*/
		while(true) {
			
			//GH what is your name
			
			//I receive name put it in String name bucket
			String name = kb.nextLine();
			
			if(name.equals(jacob_account.getName()))//replace with IF GH hears proper name
				functional(jacob_account, dbase, kb);
			else if(name.equals(arthur_account.getName()))
				functional(arthur_account, dbase, kb);
			else if(name.equals(takuma_account.getName()))
				functional(takuma_account, dbase, kb);
			else if(name.equals(tyler_account.getName()))
				functional(tyler_account, dbase, kb);
			else {
				System.out.println("Please enter a name associated with Phat Elves Secure Banking");
				continue;
			}
		}
	}
}
