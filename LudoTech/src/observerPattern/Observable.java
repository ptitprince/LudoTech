package observerPattern;

public interface Observable {
	public void addObserver(Observer obs);

	public void removeObserver(Observer observer);

	public void notifyObservers(String str);
}
