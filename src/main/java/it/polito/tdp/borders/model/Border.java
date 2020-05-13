package it.polito.tdp.borders.model;

public class Border {
	private Country nazione1;
	private Country nazione2;
	private int year;
	/**
	 * @param nazione1
	 * @param nazione2
	 * @param year
	 */
	public Border(Country nazione1, Country nazione2, int year) {
		super();
		this.nazione1 = nazione1;
		this.nazione2 = nazione2;
		this.year = year;
	}
	public Country getNazione1() {
		return nazione1;
	}
	public void setNazione1(Country nazione1) {
		this.nazione1 = nazione1;
	}
	public Country getNazione2() {
		return nazione2;
	}
	public void setNazione2(Country nazione2) {
		this.nazione2 = nazione2;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	} 
	
	
	

}
