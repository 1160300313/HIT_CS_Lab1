package P3;

public class Person {
	private final String name;

	/**
	 * Create a person with a name.
	 * 
	 * @param name
	 */
	public Person(String name) {
		this.name = name;
	}

	/**
	 * Get the name of this person.
	 * 
	 * @return the name of the person
	 */
	public String getName() {
		return this.name;
	}
}
