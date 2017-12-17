public class Person
{
	private String name;
	private String email;
	private long counter;

	public Person(String name, String email, long counter)
	{
		this.name = name;
		this.email = email;
		this.counter = counter;
	}

	public String getName()
	{
		return name;
	}

	public String getEmail()
	{
		return email;
	}

	public long getCounter()
	{
		return counter;
	}
}