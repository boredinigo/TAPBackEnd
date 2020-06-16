package Modelo;

import com.google.gson.annotations.SerializedName;

//https://tap-junio.herokuapp.com/prioridad/
public class Prioridad {

	@SerializedName("idPrioridad")
	public int idPrioridad;
	
	@SerializedName("nombre")
	public String nombre;
}
