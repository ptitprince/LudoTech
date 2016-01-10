package backend.tests;

import org.junit.Before;

import backend.services.BookServices;

public class BookTests extends Tests {

	private BookServices bookServices;
	
	@Before
	public void before() {
		this.bookServices = new BookServices();
		super.before();
	}
	
}
