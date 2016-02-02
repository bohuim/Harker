import java.io.*;
import java.util.*;

public class FileUtil
{
    /**
     * Method: loadFile(file location)
     * Usage: ArrayList<String> txt = FileUtil.loadFile(C:GoogleDrive/...);
     * --------------------------------------
     * loads the given file in an ArrayList and returns it
     * 
     * @param fileName - location and name of the file to be read
     * @return ArrayList representation of the file with every line as an element
     */
	public static ArrayList<String> loadFile(String fileName)
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line = in.readLine();
			ArrayList<String> lines = new ArrayList<String>();
			while (line != null)
			{
				lines.add(line);
				line = in.readLine();
			}
			in.close();

			return lines;
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method: saveFile(String, ArrayList<String>)
	 * Usage: FileUtil.saveFile(fileName, content);
	 * -----------------------------------------------
	 * saves the given ArrayList as a txt file with each element as a line 
	 * to the given location and name
	 * 
	 * @param fileName - location and name of file
	 * @param content - ArrayList of content to be saved
	 */
	public static void saveFile(String fileName, ArrayList<String> content)
	{
		try
		{
			PrintWriter out = new PrintWriter(new FileWriter(fileName), true);
			for (String line : content)
				out.println(line);
			out.close();
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}