import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main
{
	private static BufferedWriter writer;
	private static String filePath;

	public static void prepareTextFile(String txtFileName)
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(txtFileName, true));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeLog(String text)
	{
		try
		{
			writer.write(text + "\n");
			writer.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		JSONReader reader = new JSONReader(Constants.JSON_FILENAME);
		CertificateCreator creator = new CertificateCreator(Constants.CERTIFICATE_FILENAME);
		EMailSender sender = new EMailSender(Constants.EMAIL_ADDRESS, Constants.EMAIL_PASSWORD);
		prepareTextFile(Constants.TXT_FILENAME);

		for (Person person : reader.readJSON())
		{
			if ((person.getCounter() >= Constants.SESSION_LIMIT) && (!person.getEmail().equals("") && !person.getName().equals("")))
			{
				filePath = sender.sendMail(person, creator.createCertificate(person).getPath());
				writeLog(person.getName() + " | " + person.getEmail() + " | " + person.getCounter());
				//creator.deleteCertificate(filePath);
			}
		}
		try
		{
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}