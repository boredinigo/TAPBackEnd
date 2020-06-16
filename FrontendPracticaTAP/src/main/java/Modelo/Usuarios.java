package Modelo;

import com.google.gson.annotations.SerializedName;

//https://tap-junio.herokuapp.com/usuario/
public class Usuarios {

	@SerializedName("idUsuario")
	public int idUsuario;
	
	@SerializedName("usuario")
	public String usuario;
	
	@SerializedName("contraseña")
	public int contraseña;
	
}
