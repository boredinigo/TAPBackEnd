package Modelo;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

//https://tap-junio.herokuapp.com/tarea/
public class Tareas {

	@SerializedName("idTarea")
	public int idTarea;
	
	@SerializedName("idUsuario")
	public int idUsuario;

	@SerializedName("idLista")
	public int idLista;

	@SerializedName("idPrioridad")
	public int idPrioridad;
	
	@SerializedName("nombreTarea")
	public String nombreTarea;

	@SerializedName("descripcion")
	public String descripcion;

	@SerializedName("fecha")
	public Date fecha;

	@SerializedName("estado")
	public String estado;
}
