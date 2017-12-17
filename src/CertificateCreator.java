import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class CertificateCreator
{
	private BufferedImage blackCertificate;

	public CertificateCreator(String certFileName)
	{
		try
		{
			blackCertificate = ImageIO.read(new File(certFileName));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public int calculateX(String text, Graphics2D graphics, Font font, int start, int end)
	{
		FontMetrics metrics = graphics.getFontMetrics(font);
		return start + ((end - start - metrics.stringWidth(text)))/2;
	}

	public File createCertificate(Person person)
	{
		String fileName = person.getName().replaceAll(" ", "_") + "." + Constants.CERTIFICATE_FILETYPE;
		try
		{
			Path certificatePath = Paths.get(Constants.CERTIFICATE_FOLDERNAME);

			if (!Files.exists(certificatePath))
			{
				Files.createDirectories(certificatePath);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		File certificatesFolder = new File (Constants.CERTIFICATE_FOLDERNAME);
		File certificatePNG = new File(certificatesFolder ,fileName);
		Font font = new Font(Constants.FONT_NAME, Constants.FONT_STYLE,Constants.FONT_SIZE);
		try
		{
			BufferedImage certificateImage = new BufferedImage(blackCertificate.getColorModel(),
					blackCertificate.copyData(null),
					blackCertificate.getColorModel().isAlphaPremultiplied(),
					null);
			Graphics2D certificate = (Graphics2D) certificateImage.getGraphics();
			certificate.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			certificate.setColor(Color.BLACK);
			certificate.setFont(font);
			certificate.drawString(person.getName(), calculateX(person.getName(), certificate, font, Constants.X_START, Constants.X_END), Constants.Y);
			certificate.dispose();

			ImageIO.write(certificateImage, Constants.CERTIFICATE_FILETYPE, certificatePNG);

			System.out.println(">>>Certificate created: [" + person.getName() + ", " + person.getCounter() + "]");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return certificatePNG;
	}

	public boolean deleteCertificate(String certificatePath)
	{
		File certificateFile = new File(certificatePath);
		return certificateFile.delete();
	}
}