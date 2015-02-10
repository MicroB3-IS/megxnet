package net.megx.ui.table.demo;

public class Country {
	private String name;
	private int population = -1;
	private int gdpPPP = -1;
	private int unemploymentRate;
	
	public Country(String name, int population, int gdpPPP, int unemploymentRate) {
		this.name = name;
		this.population = population;
		this.gdpPPP = gdpPPP;
		this.unemploymentRate = unemploymentRate;
	}

	public Country() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public int getGdpPPP() {
		return gdpPPP;
	}
	public void setGdpPPP(int gdpPPP) {
		this.gdpPPP = gdpPPP;
	}
	public int getUnemploymentRate() {
		return unemploymentRate;
	}
	public void setUnemploymentRate(int unemploymentRate) {
		this.unemploymentRate = unemploymentRate;
	}
}
