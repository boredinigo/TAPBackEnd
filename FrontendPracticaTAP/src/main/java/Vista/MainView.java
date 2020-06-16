package Vista;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import Controlador.Conexion;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */

public class MainView extends UI{
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        //Conexion conexion = Conexion.getSingletonInstance();
        
        Label inicioSesion = new Label ("INICIAR SESIÓN");
        Label registroNuevoUsuario = new Label ("REGISTRAR NUEVO USUARIO");
        
        final TextField usuario = new TextField();
        usuario.setCaption("Usuario:");
        
        final TextField contraseña = new TextField();
        contraseña.setCaption("Contraseña:");
        
        Button btnConectar = new Button("CONECTARSE");
        Button btnRegistrarse = new Button("REGISTRARSE");
        
        btnConectar.addClickListener(e -> {
        	layout.removeComponent(inicioSesion);
        	layout.removeComponent(usuario);
        	layout.removeComponent(contraseña);
        	layout.removeComponent(btnConectar);
        	layout.removeComponent(btnRegistrarse);
            layout.addComponent(new Label("Thanks " + usuario.getValue()));
        });
        
        
        btnRegistrarse.addClickListener(e -> {
        	layout.removeComponent(inicioSesion);
        	layout.removeComponent(usuario);
        	layout.removeComponent(contraseña);
        	layout.removeComponent(btnConectar);
        	layout.removeComponent(btnRegistrarse);
            layout.addComponents(registroNuevoUsuario, usuario, contraseña);
        });
               
        layout.addComponents(inicioSesion, usuario, contraseña, btnConectar, btnRegistrarse);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainView.class, productionMode = false)
    public static class MainViewServlet extends VaadinServlet {
    }
}
