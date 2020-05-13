package it.polito.tdp.borders.model;

public class Country {
	private String abbr;
	private int id;
	private String name;
	
	/**
	 * @param abbr
	 * @param id
	 * @param name
	 */
	public Country(String abbr, int id, String name) {
		super();
		this.abbr = abbr;
		this.id = id;
		this.name = name;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Country name=" + name+ "\n";
	}
	
	
	
	

}
