package Utilities;
 
import java.io.FileInputStream;
import java.util.Properties;
 
public class ConfigReader {
	static Properties prob=new Properties();
	static String projectpath=System.getProperty("user.dir");
 
	public static String getlocator(String key)
 
	{  try
	{
		String path=projectpath+"\\repository.properties";
		
		FileInputStream fis=new FileInputStream(path);
		
		  prob.load(fis);
		  fis.close();
		
	}
	catch(Exception e)
	{
		System.out.println(e.getMessage());
		
	}
	return prob.getProperty(key);
	}
	
}