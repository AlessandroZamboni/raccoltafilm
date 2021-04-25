package it.prova.raccoltafilm.model;

public enum Sesso {
	MASCHIO("M"), FEMMINA("S");

	private String abbreviazione;

	Sesso(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

}
