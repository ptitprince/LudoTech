package controller.catalog;

import controller.observable.ObservableGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.pojos.Game;
import model.services.GameServices;

/**
 * Controleur de la vue listant les jeux enregistrés en base de données
 */
public class GameListController {

	/**
	 * Table JavaFX listant les jeux
	 */
	@FXML
	private TableView<ObservableGame> gameTable;
	
	/**
	 * Colonne JavaFX listant les identifiants des jeux
	 */
	@FXML
	private TableColumn<ObservableGame, String> idColumn;
	
	/**
	 * Colonne JavaFX listant les noms des jeux
	 */
	@FXML
	private TableColumn<ObservableGame, String> nameColumn;
	
	/**
	 * Colonne JavaFX listant les catégories des jeux
	 */
	@FXML
	private TableColumn<ObservableGame, String> categoryColumn;
	
	/**
	 * Colonne JavaFX listant les âges des jeux
	 */
	@FXML
	private TableColumn<ObservableGame, String> ageColumn;
	
	/**
	 * Colonne JavaFX listant les nombres de joueurs des jeux
	 */
	@FXML
	private TableColumn<ObservableGame, String> nbPlayersColumn;
	
	/**
	 * Colonne JavaFX listant les années d'édition des jeux
	 */
	@FXML
	private TableColumn<ObservableGame, String> yearColumn;
	
	/**
	 * Services permettant l'accès aux traitements métiers de l'application
	 */
	private GameServices gameServices;
	
	public GameListController() {
		this.gameServices = new GameServices();
	}

	/**
	 * Initialisation de la correspondance entre les colonnes JavaFX et les propriétés de la classe observant des jeux (ObservableGame)
	 */
	@FXML
	private void initialize() {
		this.idColumn.setCellValueFactory(new PropertyValueFactory<ObservableGame, String>("id"));
		this.nameColumn.setCellValueFactory(new PropertyValueFactory<ObservableGame, String>("name"));
		this.categoryColumn.setCellValueFactory(new PropertyValueFactory<ObservableGame, String>("category"));
		this.ageColumn.setCellValueFactory(new PropertyValueFactory<ObservableGame, String>("age"));
		this.nbPlayersColumn.setCellValueFactory(new PropertyValueFactory<ObservableGame, String>("nbPlayers"));
		this.yearColumn.setCellValueFactory(new PropertyValueFactory<ObservableGame, String>("year"));
	}
	
	/**
	 * Chargement de la liste des jeux dans la table
	 */
	public void loadData() {
		ObservableList<ObservableGame> gameData = FXCollections.observableArrayList();
		for (Game game : this.gameServices.getGameList()) {
			gameData.add(new ObservableGame(game));
		}
		this.gameTable.setItems(gameData);
	}
	
}