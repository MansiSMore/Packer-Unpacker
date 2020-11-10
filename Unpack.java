import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.*; 


class Unpack
{
	FileInputStream fistream = null;
	FileOutputStream fostream = null;
	int unpacked = 0,scanned = 0;
	List<String> lst;
	Unpack(String srcFilename)
	{
		try
		{
		fistream = new FileInputStream(srcFilename);
		
		lst = new ArrayList<String>();
		
		unpack(srcFilename);
		
		fistream.close();
		}
		catch(Exception e)
		{
			System.out.println("\nNo such file exists ..");
			//e.printStackTrace();
		}
		
	}
	
	void unpack(String srcFilename)
	{
		byte fileheader[]=new byte[100];
		byte data[]=new byte[1024];
		int size,b,readsize = data.length;
		
		String header;
		try{
			while((b = fistream.read(fileheader,0,fileheader.length))>0)
			{
				unpacked++;
				scanned++;
				readsize = data.length;
			    //System.out.println("Header size : "+b);
			
				fileheader = decrypt(fileheader);
				
				header = new String(fileheader);
				//System.out.println("Header read : "+header+" EndOFHeader");
				String file[] = header.split("\\s");
				String filename = file[0].substring(file[0].lastIndexOf('\\')+1);
				int lengthtoread = Integer.parseInt(file[1]);
		
				File outFile = new File(filename);
		
				fostream = new FileOutputStream(outFile);
				
				lst.add(filename);
				
			//System.out.println(filename);
					
			if(lengthtoread < data.length)
			{
				readsize = lengthtoread;
			}				
			
			while((size = fistream.read(data,0,readsize))> 0)
			{
				data = decrypt(data);
				
				//System.out.println(readsize);
				String d = new String(data);
				//System.out.println("Data read : "+d);
				
				fostream.write(data,0,readsize);
				lengthtoread -= readsize;
				
				if(lengthtoread > data.length)
			    {
				readsize = data.length;
				}
				else
				{
					readsize = lengthtoread;
				}
				
				data = new byte[1024];
			}
		
		}
		System.out.println("-------------------------------------");
	    System.out.println("Scanned Files : "+scanned+
	                     "\nUnPacked Files  : "+unpacked);
		System.out.println("Unpacked Files : "+lst);
	    System.out.println("-------------------------------------");
		}
		/*catch(InvalidFileException e)
		{
			throw new InvalidFileException("Invalid packed file format");
		}*/
		catch(Exception e){}
		
	}

	byte[] decrypt(byte[] arr)
	{
		byte key = (byte)1010;
		for(int j=0;j<arr.length;j++)
			{
				arr[j] = (byte)(arr[j]^key);
			}
		return arr;
	}
}

/*class Unpack
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter the file name that is to be unpacked : ");
		String srcfile = scan.next();
		
		Unpacking unpack = new Unpacking(srcfile);
	}
}*/