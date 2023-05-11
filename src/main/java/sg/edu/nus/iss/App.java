package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {   //to get the directory path from user input when running the program
        //e.g. java sg.edu.nus.iss/App data
        //data is the dirPath which is the folder name
        String dirPath = args[0];
        //Use file class to check if directory exists
        //If directory doesn't exist, create the directory using variable dirPath
        File newDirectory = new File(dirPath);
        if(newDirectory.exists())
        {
            System.out.println("Directory "+newDirectory+" already exists");
        } else 
        {
            //make directory
            newDirectory.mkdirs();
        }

        System.out.println("Welcome to your shopping cart");
        //List collection to store the cart items of login user
        List<String> cartItems = new ArrayList<String>();
        //use console class to read keyboard input
        Console con = System.console();
        String input = "";
        //To keep track of current log-in user
        //This is also used as the filename to store the user cart items
        String loginuser = ""; 
        //Exist while loop if input is 'quit'
        while(!input.equals(("quit")))
        {
            input = con.readLine("What do you want to do? ");
            if(input.startsWith("login"))
            {
                // To read the data after 'login'
                Scanner scan = new Scanner(input.substring(6));
                while(scan.hasNext())
                {
                    loginuser = scan.next();
                }

                // Check if the file <loginuser> exists inside the dirPath folder/directory
                //if not exist, create a new file that you can write to the file
                //else /maybe override

                File loginFile = new File(dirPath+File.separator+loginuser);
                if(loginFile.exists())
                {
                    System.out.println("File "+loginuser+" already exists");
                } else 
                {   //create new file from the File class methods
                    loginFile.createNewFile();
                }

            }
            if(input.equals("users"))
            {
                //New file object
                File directoryPath = new File(dirPath);
                //Create a string array to contain the list from folder/directory
                String [] directoryListing = directoryPath.list();
                System.out.println("List of files and directories in the specific folder "+dirPath);
                for(String dirList: directoryListing)
                {
                    System.out.println(dirList);
                }
            }
            if(input.startsWith("add"))
            {
                //Remove comma from input
                input = input.replace(',', ' ');
                //Use FileWriter to set the file location and allow it to be apppended
                FileWriter fw = new FileWriter(dirPath+File.separator+loginuser, true);
                PrintWriter pw = new PrintWriter(fw);
                //Create string to hold items added
                String currentScanString = "";
                Scanner inputScanner = new Scanner(input.substring(4));
                while(inputScanner.hasNext())
                {
                    currentScanString = inputScanner.next();
                    cartItems.add(currentScanString);
                    //Write to file using PrintWriter
                    pw.write(currentScanString+"\n");
                }
                //Flush and close the fileWriter & PrintWriter objects
                pw.flush();
                pw.close();
                fw.close();
            }
            //user must be logged in by using login "name"
            if(input.equals("list"))
            {
                //Need a File class to Creates a new FileReader, given the name of the file to read from.
                File readFile = new File(dirPath+File.separator+loginuser);
                //use bufferedreader to read text from any kind of input source whether that be files, sockets, or something else. It reads FileReader object
                BufferedReader br = new BufferedReader(new FileReader(readFile));
                //Create a string to store the line from br
                String readFileInput = "";
                //Reset the cartItem List collection
                cartItems=new ArrayList<String>();
                //use while loop to read through all the item records in br
                while((readFileInput=br.readLine())!=null)
                {
                    System.out.println(readFileInput);
                    cartItems.add(readFileInput);
                }
                //exit from while loop and flush and close BufferedReader class/object
                br.close();
            }

            if(input.startsWith("delete"))
            {
                //StringVal[0] --> "delete"
                //StringVal[1] --> index to delete
                // split input into stringVal[0], stringVal[1]... base on " "
                String[] stringVal = input.split(" ");
                // String ValData = input.substring(7);
                //cartItems.remove(Integer.parseInt(ValData));
                //convert ValData[1] to int and put it in deleteIndex
                int deleteIndex = Integer.parseInt(stringVal[1]);
                //Check if deleteIndex is bigger than number of items in list
                if(deleteIndex<=cartItems.size())
                {
                    cartItems.remove(deleteIndex-1);
                } else
                {
                    System.out.println("Index out of range error.");
                }
                //Open FIleWriter and Buffered Writer 
                //Constructs a FileWriter object given a file name with a boolean indicating whether or not to append the data written.
                FileWriter fw = new FileWriter(dirPath+File.separator+loginuser, false);
                BufferedWriter bw = new BufferedWriter(fw);
                //Loop and write cartItems to file
                int listIndex = 0;
                while(listIndex < cartItems.size())
                {
                    bw.append(cartItems.get(listIndex));
                    //Go to next line
                    bw.newLine();
                    listIndex++;
                }
                // Flush and close writers
                bw.flush();
                fw.flush();
                bw.close();
                fw.close();
            }
        }
    }
}
