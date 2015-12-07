package model.enums;
/**
 * Énumération utilisée pour l'état de l'emprunt 
 * (s'il est en cours, en retard, ou terminé en retard.)
 * @author Yves
 *
 */
public enum BorrowEnums {
	En_cours("En cours"),
	En_retard("En retard"),
	Terminé_en_retard("Terminé en retard");
	
	private String s;
	
	/**
	 * constructeur de la table enum.
	 * @param s
	 */
	BorrowEnums(String s) {
		this.s = s;
	}
	
	/**
	 * ici, permet d'afficher le texte choisi.
	 */
	public String toString() {
		return s;
	}
	

}
