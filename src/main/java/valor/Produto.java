package valor;

public class Produto {

	private double volume;
	private boolean empacotado;
	
	public Produto(double volume) {
		this.volume = volume;
		this.empacotado = false;
	}
	
	public Produto (){
		this.volume = 250.0;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean isEmpacotado() {
		return empacotado;
	}

	public boolean setEmpacotado(boolean empacotado) {
		return this.empacotado = empacotado;
	}
}
