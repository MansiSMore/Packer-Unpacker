import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.*; 
import java.security.MessageDigest;

class Pack
{
	FileOutputStream fostream = null; 
	FileInputStream fistream = null;
	String validExt[]={".txt",".cpp",".c"};
	Hashtable<String,String> ht;
	Hashtable<String,Long> ht2;
	List<String> lst;
	Iterator it;
	int scanned = 0,packed = 0;
	
	public Pack(String sourceFolderName,String destinationFile)  // Parameterised Constructor for resource allocation
	{
		try
		{
			File folder = new File(sourceFolderName);
			
			fostream = new FileOutputStream(destinationFile);
			
			lst = new ArrayList<String>();
			
			ht2 = new Hashtable<String,Long>();
			
			//System.setProperty(folder);
			
			traverseAllFiles(folder);
			
		    fostream.close();

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	void createHashTable(String srcPath) throws Exception
	{
		ht = new Hashtable<String,String>();
		try
		(Stream<Path> filePathStream=Files.walk(Paths.get(srcPath))) {  // Path is the name of class
         filePathStream.forEach(filePath -> {
			 if (Files.isRegularFile(filePath))  // To check if the file is regular file
			{
			 try
			 {
				 String filename = srcPath+"\\"+filePath.getFileName().toString();
				 
			     String checksum = getMD5Checksum(filename);
			 
			     if(!ht.contains(checksum))
			     {
				    ht.put(checksum,filename);
			     }
			 }
			 catch(Exception e)
			 {
				 // System.out.println("In catch block !!");
				 e.printStackTrace();
			 }
			}
			  });
					
					//fistream.close();
		 
	  }
	  catch(Exception e)
	  {
		e.printStackTrace();
	  }
	}
	
	void traverseAllFiles(File srcFolder)
	{
		String srcPath = srcFolder.getAbsolutePath();
		try{
		createHashTable(srcPath);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		try
		(Stream<Path> filePathStream=Files.walk(Paths.get(srcPath))) {  // Path is the name of class
         filePathStream.forEach(filePath -> { // For every file
            if (Files.isRegularFile(filePath))  // To check if the file is regular file
			{
				String filename = srcPath+"\\"+filePath.getFileName().toString();  // Canonical method calls(Left to right)
				String ext = filename.substring(filename.lastIndexOf("."));
				List<String> list = Arrays.asList(validExt);
			
			  if(ht.contains(filename) && list.contains(ext))
			  {
				try
				{
					fistream = new FileInputStream(filename);
					File srcFile = new File(filename);

					pack(srcFile.getAbsolutePath());
					String file = filename.substring(filename.lastIndexOf('\\')+1);
					ht2.put(file,srcFile.length());
					lst.add(file);
					packed++;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
			 }
			
			 else
			 {
				scanned++;
			 }
			
			}
			

        });
		
					//fistream.close();
		}
	  catch(Exception e)
	  {
		e.printStackTrace();
	  }
	  
	  System.out.println("-------------------------------------");
	  System.out.println("Scanned Files : "+(scanned+packed)+
	                   "\nPacked Files  : "+packed);
	  System.out.println("Files Packed : "+lst);
	  System.out.println("Files Packed : "+ht2);
	  System.out.println("-------------------------------------");
	
	}
	
	 byte[] createChecksum(String filename) throws Exception
	 {
		 
       FileInputStream fis =  new FileInputStream(filename);

       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do 
	   {
           numRead = fis.read(buffer);
           if (numRead > 0)
		   {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
		 
       return complete.digest();
   }

     String getMD5Checksum(String filename) throws Exception
	 {
		   
       byte[] b = createChecksum(filename);
       String result = "";

       for (int i=0; i < b.length; i++)
	   {
           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		   
       }
		  return result;
       
     }
   
	void pack(String filename)
	{
		byte inputHeader[] = new byte[100];
		byte data[] = new byte[1024];
		int datalength = 0;
		byte key = (byte)1010;
		int j;
		
		File srcFile = new File(filename);
		
		String header = filename+" "+srcFile.length()+" ";
		
		for(int i=header.length();i<100;i++)
		{
			header += " ";
		}
		try
		{
		inputHeader = header.getBytes();
		inputHeader = encrypt(inputHeader);
		
		
		fostream.write(inputHeader,0,inputHeader.length);
		
		fistream = new FileInputStream(filename);
		
		while((datalength = fistream.read(data,0,data.length))>0)
		{
			data = encrypt(data);
			fostream.write(data,0,datalength);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	byte[] encrypt(byte arr[])
	{
		byte key = (byte)1010;
		for(int i=0;i<arr.length;i++)
		{
			arr[i] = (byte)(arr[i]^key);
		}
		return arr;
	}
}

/*class Pack
{	
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the folder name from where you want to pack the files : ");
		String srcfolder = scan.next();
		
		System.out.print("\nEnter the file name into which the files are to be packed : ");
		String destFile = scan.next();
		
		Packing pack = new Packing(srcfolder,destFile);
	}
}*/
