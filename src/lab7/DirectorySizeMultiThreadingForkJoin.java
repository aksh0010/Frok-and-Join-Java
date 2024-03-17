package lab7;

import java.io.File;
import java.util.concurrent.RecursiveTask;
/*DirectorySizeMultiThreadingForkJoin
 * Program requires arguments (Command line to run it)
 * */
public class DirectorySizeMultiThreadingForkJoin extends RecursiveTask<Long> {

	// Directory name we will fetch this from command line argv vector
    private final File directory;

    // constructer to initilize the directory
    public DirectorySizeMultiThreadingForkJoin(File directory) {
        this.directory = directory;
    }

    @Override
    protected Long compute() {
        long size = 0;
        //SImply checking is sinfle file
        if (directory.isFile()) {
            return directory.length();
        }
        // getting all files ( ignoring dir) and iterating using enhanced for loop
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
            	// Forking for each file and creating subtasks on each file from array instead of recursive call
                DirectorySizeMultiThreadingForkJoin subTask = new DirectorySizeMultiThreadingForkJoin(file);
                //Creating subtask thread
                subTask.fork();
                // Will add each subtask thread's result to final size
                size += subTask.join();
            }
        }

        return size;
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
	  		// Here there were issue on global declartion of varibale like other file so had to have local declaration
	  		
	        String directoryPath = args[0];
	        File directory = new File(directoryPath);
	        // checking if there is acutally directory present
	        if (!directory.exists() || !directory.isDirectory()) {
	        	System.out.println("No Such directory found.");
	            return;
	        }
	        //Time Start
	        long start_time = System.currentTimeMillis();
	        DirectorySizeMultiThreadingForkJoin task = new DirectorySizeMultiThreadingForkJoin(directory);
	        long size = task.compute();
	        //Time end
	        long end_time = System.currentTimeMillis();
	        long elapsed_time = end_time-start_time;
	        
	        System.out.println("Size of directory " + directoryPath + " is: " + size + " bytes.");
	        System.out.println("Total time was "+elapsed_time+"ms");
		       
    }}
}

