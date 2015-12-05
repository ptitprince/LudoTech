package controller;

import java.io.IOException;

import controller.catalog.GameListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Classe principale de l'application faisant office de lanceur et gérant l'interface graphique
 */
public class MainApplication extends Application {

	/**
	 * Théâtre de l'application (la fenêtre principale)
	 */
	private Stage mainStage;
	
	/**
	 * Vue principale regroupant tous les éléments graphiques (onglets et leur contenu compris)
	 */
	private BorderPane mainView;
    
    /**
     * Lanceur de l'application
     */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage mainStage) {
		this.mainStage = mainStage;
		this.mainStage.setTitle("LudoTech");
		try {
			FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/view/MainView.fxml"));
			this.mainView = (BorderPane) loader.load();
			Scene scene = new Scene(mainView);
			scene.getStylesheets().add("ressources/css/modena.css");
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		showGameListView();
	}
	
	/**
	 * Affichage de la vue listant les jeux dans l'onglet "Catalogue"
	 */
	public void showGameListView() {
	    try {
	    	FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/view/catalog/GameListView.fxml"));
			
			BorderPane gameListPage = (BorderPane) loader.load();
			TabPane tabPane = (TabPane) mainView.getCenter();
			tabPane.getTabs().get(1).setContent(gameListPage);
			
			GameListController controller = loader.getController();
			
			controller.loadData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
