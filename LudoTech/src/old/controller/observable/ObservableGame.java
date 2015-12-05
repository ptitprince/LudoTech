package controller.observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.pojos.Game;

/**
 * Encapsule la classe métier Game pour une utilisation dans une interface graphique JavaFX
 */
public class ObservableGame {

	/**
	 * Jeu observé
	 */
	private Game game;
	
	public ObservableGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Construction de la valeur de la colonne "id"
	 * @return l'identifiant du jeu sous la forme d'une propriété de colonne
	 */
	public StringProperty idProperty() {
		return new SimpleStringProperty(this.game.getGameID() + "");
	}
	
	/**
	 * Construction de la valeur de la colonne "name"
	 * @return le nom du jeu sous la forme d'une propriété de colonne
	 */
	public StringProperty nameProperty() {
		return new SimpleStringProperty(this.game.getName());
	}
	
	/**
	 * Construction de la valeur de la colonne "category"
	 * @return la categorie du jeu sous la forme d'une propriété de colonne
	 */
	public StringProperty categoryProperty() {
		return new SimpleStringProperty(this.game.getCategory());
	}
	
	/**
	 * Construction de la valeur de la colonne "age"
	 * @return l'age minimum recommandé pour le jeu sous la forme d'une propriété de colonne
	 */
	public StringProperty ageProperty() {
		return new SimpleStringProperty(this.game.getMinimumAge() + "");
	}
	
	/**
	 * Construction de la valeur de la colonne "nbPlayers"
	 * @return l'intervalle du nombre de joueur pour le jeu sous la forme d'une propriété de colonne
	 */
	public StringProperty nbPlayersProperty() {
		return new SimpleStringProperty(this.game.getMinimumPlayers() + " - " + this.game.getMaximumPlayers());
	}
	
	/**
	 * Construction de la valeur de la colonne "year"
	 * @return l'année d'édition du jeu sous la forme d'une propriété de colonne
	 */
	public StringProperty yearProperty() {
		return new SimpleStringProperty(this.game.getPublishingYear() + "");
	}
}
