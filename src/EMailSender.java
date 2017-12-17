import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EMailSender
{
	private String emailAddress;
	private String emailPassword;

	public EMailSender(String emailAddress, String emailPassword)
	{
		this.emailAddress = emailAddress;
		this.emailPassword = emailPassword;
	}

	public String sendMail(Person person, String certificatePath)
	{
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(certificatePath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(Constants.EMAIL_ATTACHMENT_DESCRIPTON);
		attachment.setName(Constants.EMAIL_ATTACHMENT_NAME);

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(Constants.EMAIL_HOST);
		try
		{
			email.addTo(person.getEmail());
			email.setFrom(emailAddress, Constants.EMAIL_FROM, "utf-8");
			email.setSubject(Constants.EMAIL_SUBJECT);
			email.setMsg(Constants.EMAIL_BODY);
			email.attach(attachment);

			email.setSmtpPort(Constants.EMAIL_SMTP_PORT);
			email.setSSLOnConnect(true);
			email.setAuthentication(emailAddress, emailPassword);
			email.send();

			System.out.println(">>>E-Mail sent: [" + person.getEmail() + "]");
		}
		catch (EmailException e)
		{
			e.printStackTrace();
		}

		return certificatePath;
	}
}