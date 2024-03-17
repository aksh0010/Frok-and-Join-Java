package lab7;
import java.io.File;

public class DirectorySize {
	private static long total_size = 0; // to calculate total size
	private static String directoryPath ; // to store argv[0] meaning file path
	private static File directory; // File object on file path meaning directory
	/*DirectorySize
	 * Program requires arguments (Command line to run it)
	 * */
    public static long calculateDirectorySize(File directory) {
    	  //SImply checking is sinfle file
        if (directory.isFile()) {
            return directory.length();
        }
        
        // getting all files ( ignoring dir) and iterating using enhanced for loop
        
        File[] files = directory.listFiles();
        if (files != null) {
        	// SImply calculating file by recursive calling 
            for (File file : files) {
            	total_size += calculateDirectorySize(file);
            }
        }
        return total_size;
    }

    public static void main(String[] args) {
    	
    	// Simply checking for valid arguments for this program
    	//if it is 0 or more than 1 arguments meaning program should not run
    	if(args.length==0) {
    		  System.out.println("0 arguments found : Expected 1\nCompile it using command on terminal -> java DirectorySize \"Path\"");
    	         
    		return;
    	} else if (args.length != 1) {
            System.out.println("2 or more arugments found : Expected 1\nCompile it using command on terminal -> java DirectorySize \"Path\"");
            return;
        } else {
	        directoryPath = args[0];
	        directory = new File(directoryPath);

	    	// checking if there is acutally directory present
	        if (!directory.exists() || !directory.isDirectory()) {
	            System.out.println("No Such directory found.");
	            return;
	        }
	        // Time start
	        long start_time = System.currentTimeMillis();
	        long size = calculateDirectorySize(directory);
	        // Time end
	        long end_time = System.currentTimeMillis();
	        long elapsed_time = end_time-start_time;
	        System.out.println("Size of directory " + directoryPath + " is: " + size + " bytes.");
	        System.out.println("Total time was "+elapsed_time+"ms");
	       
        } 
    	}
}
