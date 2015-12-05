package mvc.model;

import java.util.ArrayList;

import observerPattern.Observable;
import observerPattern.Observer;

public abstract class AbstractModel implements Observable {

	private ArrayList<Observer> observerList = new ArrayList<Observer>();

	public void addObserver(Observer observer) {
		this.observerList.add(observer);
	}

	public void removeObserver(Observer observer) {
		observerList.remove(observer);
	}

	public void notifyObservers(String string) {
		for (Observer observer : observerList)
			observer.update(string);
	}

}
