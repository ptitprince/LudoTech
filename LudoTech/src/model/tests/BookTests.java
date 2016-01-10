package model.tests;

import org.junit.Before;
import model.services.BookServices;

public class BookTests extends Tests {

	private BookServices bookServices;
	
	@Before
	public void before() {
		this.bookServices = new BookServices();
		super.before();
	}
	
}
