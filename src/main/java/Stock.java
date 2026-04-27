public class Stock {
	private Maquina maquina;
	private Producto producto;
	private int cantidad;
	private Integer posicion_en_maquina;
	private Integer cantidadBaja; //0 si aún hay suficientes productos, 1 si no hay suficientes productos
	private int limiteCantidadBaja; //límite inferior de productos suficientes
	
	public Stock() {}
	
	public Stock(Maquina maquina, Producto producto, int cantidad, Integer posicion_en_maquina) {
		this.maquina = maquina;
		this.producto = producto;
		this.cantidad = cantidad;
		this.posicion_en_maquina = posicion_en_maquina;
		this.cantidadBaja = 0;
		this.limiteCantidadBaja = 0;
	}
	
	public Producto getProducto() {
		return this.producto;
	}
	
	public int getCantidad(){
		return this.cantidad;
	}
	
	public Integer getPosicion() {
		return this.posicion_en_maquina;
	}
	
	public void cambiarPosicion(Integer posicion) {
		this.posicion_en_maquina = posicion;
	}
	
	public void actualizarCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public void avisoCantidadBaja() {
		
	}
	
	public void calculoLimiteCantidadBaja() {
		
	}
}
