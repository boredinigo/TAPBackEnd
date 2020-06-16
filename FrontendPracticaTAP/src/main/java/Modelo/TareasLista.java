package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TareasLista {

	public int idTarea;
	public int idLista;
	public String nombreLista;
	public String nombreTarea;
	public String descripcion;
	public String estado;
	public int idPrioridad;
	public LocalDate fechaLimite;
	
	
	
	public TareasLista(int idTarea, int idLista, String nombreTarea, String descripcion, String estado,
			int idPrioridad, Date fecha){

		this.idTarea = idTarea;
		this.idLista = idLista;
		this.nombreTarea = nombreTarea;
		this.descripcion = descripcion;
		this.estado = estado;
		this.idPrioridad = idPrioridad;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
		
		fechaLimite = LocalDate.parse((CharSequence) fecha, formatter);
	}

}
