package Controlador;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Modelo.Listas;
import Modelo.Prioridad;
import Modelo.Tareas;
import Modelo.Usuarios;

public class Conexion {

	//Utilizamos el patrón Singleton para realizar la conexión del back con el front
	private static Conexion conexion;
	
    private Conexion() {
    	System.out.println("Iniciada por primera vez la conexión");
    }

    public static Conexion getSingletonInstance() {
        if (conexion == null){
        	conexion = new Conexion();
        }
        
        return conexion;
    }
	    
	//OrganizarTareasConListas organizar = OrganizarTareasConListas.getSingletonInstance();
	Listas[] listas;
	Tareas[] tareas;
	Usuarios[] usuario;
	Prioridad[] prioridad;
	
	//Crear usuario
    public boolean crearUsuario(String nombreUsuario, String contraseña) throws Exception {
        HttpClient cliente = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://tap-junio.herokuapp.com/usuario/");

        List<NameValuePair> parametros = new ArrayList<NameValuePair>(2);
        parametros.add(new BasicNameValuePair("Usuario", nombreUsuario));
        parametros.add(new BasicNameValuePair("Contraseña", contraseña));
        post.setEntity(new UrlEncodedFormEntity(parametros, "UTF-8"));

        HttpResponse response = cliente.execute(post);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
            	String texto = "[" + EntityUtils.toString(entity) + "]";
            	System.out.println(texto);
            	
            	final GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();
                
                usuario = gson.fromJson(texto, Usuarios[].class);
                System.out.println("Usuario:"); 
                for (Usuarios user : usuario) {
                	System.out.println("id = " + user.idUsuario + 
                            ", User = " + user.usuario); 
    			}
                
            	if	(response.toString().contains("200"))
            		return true;
            	else
            		return false;
            }
        }
		return false;
    }
    
    //Hacer login
    public boolean login(String nombreUsuario, String contraseña) throws Exception {
        HttpClient cliente = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://tap-junio.herokuapp.com/usuario/login");

        List<NameValuePair> parametros = new ArrayList<NameValuePair>(2);
        parametros.add(new BasicNameValuePair("Usuario", nombreUsuario));
        parametros.add(new BasicNameValuePair("Contraseña", contraseña));
        post.setEntity(new UrlEncodedFormEntity(parametros, "UTF-8"));

        HttpResponse response = cliente.execute(post);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
            	String texto = "[" + EntityUtils.toString(entity) + "]";
            	System.out.println(texto);
            	
            	final GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();
                
                usuario = gson.fromJson(texto, Usuarios[].class);
                System.out.println("Usuario:"); 
                for (Usuarios user : usuario) {
                	System.out.println("id = " + user.idUsuario + 
                            ", User = " + user.usuario); 
    			}
                
            	if	(response.toString().contains("200"))
            		return true;
            	else
            		return false;
            }
        }
		return false;
    }
    
    
    //CrearLista
    public String crearLista(String nombreLista) throws Exception {
        HttpClient cliente = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://tap-junio.herokuapp.com/tarea/");

        List<NameValuePair> parametros = new ArrayList<NameValuePair>(1);
        parametros.add(new BasicNameValuePair("nombre", nombreLista));
        post.setEntity(new UrlEncodedFormEntity(parametros, "UTF-8"));

        HttpResponse response = cliente.execute(post);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {

        		String texto = "[" + EntityUtils.toString(entity) + "]";
            	System.out.println(texto);
            	System.out.println(response.getStatusLine());
            	System.out.println(entity.toString());
            	if	(response.toString().contains("200")) {
            		final GsonBuilder gsonBuilder = new GsonBuilder();
                    final Gson gson = gsonBuilder.create();
                    
                    listas = gson.fromJson(texto, Listas[].class);
                    
                    return Integer.toString(listas[0].idLista);
            	}
            	else
            		return "";
            }
        }
		return "";
    }
    
    //Crear tarea
	public boolean crearTarea(String nombreTarea, String descripcion, String nombrePrioridad, String estado, LocalDate fecha, String nombreLista) throws Exception {
		
		String idLista = "-1"; 
		String idPrioridad; 
	
		String idUsuario = Integer.toString(usuario[0].idUsuario);
		
		for (Listas lista : listas) {
			if(lista.nombreLista == nombreLista)
				idLista = Integer.toString(lista.idLista);
		}
		if(idLista.equals("-1")) {
			idLista = crearLista(nombreLista);
		}
		
		for (Prioridad prioridad : prioridad) {
			if(prioridad.nombre == nombrePrioridad)
				idPrioridad = Integer.toString(prioridad.idPrioridad);
		}

		System.out.println("Nombre " + nombreTarea);
		System.out.println("Descripcion " + descripcion);
		System.out.println("Estado " + estado);
		System.out.println("Fecha límite " + fecha.toString());
		System.out.println("Prioridad " + nombrePrioridad);
		System.out.println("idLista " + idLista);
		System.out.println("idUsu " + idUsuario);
		
		HttpClient cliente = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://tap-junio.herokuapp.com/tarea/");

        List<NameValuePair> parametros = new ArrayList<NameValuePair>(7);
        parametros.add(new BasicNameValuePair("idUsuario", idUsuario));
        parametros.add(new BasicNameValuePair("idLista", idLista));
        parametros.add(new BasicNameValuePair("Nombre", nombreTarea));
        parametros.add(new BasicNameValuePair("Descripcion", ""));
        parametros.add(new BasicNameValuePair("Estado", estado));
        parametros.add(new BasicNameValuePair("Priotidad", nombrePrioridad));
        parametros.add(new BasicNameValuePair("Fecha límite", fecha.toString()));
        

        post.setEntity(new UrlEncodedFormEntity(parametros, "UTF-8"));

        HttpResponse response = cliente.execute(post);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
            	
            	System.out.println(EntityUtils.toString(entity));
            	System.out.println(response.getStatusLine());
            	System.out.println(entity.toString());
            	if	(response.toString().contains("200"))
            		return true;
            	else
            		return false;
            }
        }
		return false;
	}

	//Modificar tareas
	public boolean modificarTareas(String nombreTarea, String descripcion, String prioridad, String estado, LocalDate fecha, String idTarea) throws Exception{
		
		idTarea = idTarea.replace("Optional[", "");
		idTarea = idTarea.substring(0, idTarea.length()-1);
		
		String idLista = "0"; 
		String idUsuario = Integer.toString(usuario[0].idUsuario);
		for (Tareas tarea : tareas) {
			if(Integer.toString(tarea.idTarea).equals(idTarea))
				idLista = Integer.toString(tarea.idLista);
		}

		System.out.println("Nombre " + nombreTarea);
		System.out.println("Descripcion " + descripcion);
		System.out.println("Prioridad " + prioridad);
		System.out.println("Estado " + estado);
		System.out.println("Fecha límite" + fecha.toString());
		System.out.println("idTarea " + idTarea);
		System.out.println("idLista " + idLista);
		System.out.println("idUsuario " + idUsuario);
		
		HttpClient cliente = HttpClients.createDefault();
		HttpPut put = new HttpPut("https://tap-junio.herokuapp.com/tarea/" + idTarea);

        List<NameValuePair> parametros = new ArrayList<NameValuePair>(7);
        parametros.add(new BasicNameValuePair("idUsuario ", idUsuario));
        parametros.add(new BasicNameValuePair("idLista ", idLista));
        parametros.add(new BasicNameValuePair("Nombre ", nombreTarea));
        parametros.add(new BasicNameValuePair("Descripcion ", descripcion));
        parametros.add(new BasicNameValuePair("Estado ", estado));
        parametros.add(new BasicNameValuePair("Prioridad ", prioridad));
        parametros.add(new BasicNameValuePair("Fecha límite ", fecha.toString()));
        

        put.setEntity(new UrlEncodedFormEntity(parametros, "UTF-8"));

        HttpResponse response = cliente.execute(put);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                // do something useful
            	System.out.println(EntityUtils.toString(entity));
            	System.out.println(response.getStatusLine());
            	System.out.println(entity.toString());
            	if	(response.toString().contains("200"))
            		return true;
            	else
            		return false;
            }
        }
		return false;
	}
	
	//Borrar tarea
	public boolean borrarTarea(String idTarea) throws Exception{
		
		idTarea = idTarea.replace("Optional[", "");
		idTarea = idTarea.substring(0, idTarea.length()-1);
		
		String idLista = "0"; 
		String idUsuario = Integer.toString(usuario[0].idUsuario);
		for (Tareas tarea : tareas) {
			if(Integer.toString(tarea.idTarea).equals(idTarea))
				idLista = Integer.toString(tarea.idLista);
		}

		System.out.println("idTarea " + idTarea);
		System.out.println("idLista " + idLista);
		System.out.println("idUsuario " + idUsuario);
		
		HttpClient cliente = HttpClients.createDefault();
		HttpDelete delete = new HttpDelete("https://tap-junio.herokuapp.com/tarea/" + idTarea);
		
        HttpResponse response = cliente.execute(delete);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                // do something useful
            	System.out.println(EntityUtils.toString(entity));
            	System.out.println(response.getStatusLine());
            	System.out.println(entity.toString());
            	if	(response.toString().contains("200"))
            		return true;
            	else
            		return false;
            }
        }
		return false;
	}
	
	//Leer lista 
    public void leerLista()
    {
    	try
        {
            //creamos una URL donde esta nuestro webservice
            URL url = new URL("https://tap-junio.herokuapp.com/lista/");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("Accept", "application/json");
            if (conexion.getResponseCode() != 200) 
            {
                throw new RuntimeException("Failed : HTTP error code : " + conexion.getResponseCode());
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
            StringBuilder builder = new StringBuilder();
            int x;
            
            while ((x = reader.read()) != -1)
            {
            	builder.append((char) x);
            }

            String output = builder.toString();
            
            
            final GsonBuilder gsonBuilder = new GsonBuilder();
            final Gson gson = gsonBuilder.create();
            
            listas = gson.fromJson(output, Listas[].class);
            

            System.out.println("Listas:"); 
            for (Listas lista : listas) {
            	System.out.println("id = " + lista.idLista + 
                        ", Nombre = " + lista.nombreLista); 
			}

            conexion.disconnect();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    	
    }
    
    //Leer tareas
    public void leerTareas()
    {
    	try
        {
            //creamos una URL donde esta nuestro webservice
            URL url = new URL("https://tap-junio.herokuapp.com/tarea/");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("Accept", "application/json");
            if (conexion.getResponseCode() != 200) 
            {
                throw new RuntimeException("Failed : HTTP error code : " + conexion.getResponseCode());
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
          
            StringBuilder builder = new StringBuilder();
            int x;
            while ((x = reader.read()) != -1)
            {
              builder.append((char) x);
            }
 
            String output = builder.toString();
            
            
            final GsonBuilder gsonBuilder = new GsonBuilder();
            final Gson gson = gsonBuilder.create();
    
            tareas = gson.fromJson(output, Tareas[].class);
            System.out.println("Tareas:"); 
            for (Tareas tarea : tareas) {
            	System.out.println("id = " + tarea.idTarea + 
                        ", Nombre = " + tarea.nombreTarea); 
			}

            conexion.disconnect();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    	
    }

	public Usuarios[] getUsuario() {
		return usuario;
	}
}
