package model.exceptions;

import javax.swing.JOptionPane;

import gui.utils.TextView;
import model.POJOs.Book;

public class BookAlreadyExistException extends Exception {

	private Book book;

	public BookAlreadyExistException(Book book) {
		this.book = book;
	}

	public Book getBook() {
		return book;
	}
	
	public void show() {
		String text = TextView.get(this.getClass().getSimpleName());
		JOptionPane.showMessageDialog(null, String.format(text, this.book.getMember().getFirstName() + " " + this.book.getMember().getLastName(), this.book.getStartDate().toLocaleString()));
	}
	
}
