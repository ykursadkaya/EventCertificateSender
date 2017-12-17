import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSONReader
{
	private FileReader fileReader;
	private JSONParser parser = new JSONParser();
	private ArrayList<Person> personList = new ArrayList<Person>();


	public JSONReader(String jsonFileName)
	{
		try
		{
			fileReader = new FileReader(jsonFileName);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	public ArrayList<Person> readJSON()
	{
		try
		{
			Object obj = parser.parse(fileReader);

			JSONArray jsonList = (JSONArray) obj;
			for (Object it : jsonList)
			{
				JSONObject jsonObj = (JSONObject) it;

				String name = (String) jsonObj.get(Constants.NAME_FIELD);
				String email = (String) jsonObj.get(Constants.EMAIL_FIELD);
				long counter = Long.valueOf((String) jsonObj.get(Constants.COUNTER_FIELD)).longValue();

				personList.add(new Person(name, email, counter));

				System.out.println(">>>Added to the list: [" + name + ", " + email + ", " + counter + "]");
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException | ParseException e)
		{
			e.printStackTrace();
		}

		return personList;
	}
}