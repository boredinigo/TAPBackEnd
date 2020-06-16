package Modelo;

import com.google.gson.annotations.SerializedName;

//https://tap-junio.herokuapp.com/lista/
public class Listas {

	@SerializedName("idLista")
	public int idLista;
	
	@SerializedName("nombreLista")
	public String nombreLista;
}