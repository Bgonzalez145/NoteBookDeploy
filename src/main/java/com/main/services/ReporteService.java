package com.main.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.main.documents.Nota;
import com.main.repositorys.NotaRepository;
import com.main.security.repository.UsuarioRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReporteService {

	@Autowired
	private NotaRepository notaRepository; 
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public boolean exportReport(String reportFormat, String nombreUsuario) throws FileNotFoundException, JRException {
		boolean retorno = false;
		byte[] pdfBytes = null;
		// Traer las notas para el usuario
        List<Nota> notas = notaRepository.imprimirNotasUsuario(nombreUsuario);
        
        // Traer el correo electronico del usuario
        String correoTo = usuarioRepository.findByNombreUsuario(nombreUsuario).get().getEmail();
        
		// Cargar el archivo del reporte
        File file = ResourceUtils.getFile("classpath:reporte.jrxml");
        
        // Compilar el reporte desde el archivo (Error)
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        
        // Generar los datos del reporte
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(notas);
        
        // Crear los parametros del reporte
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Note Book");
        
        // Imprimir el reporte 
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        // Exportar el reporte
        //String path = System.getProperty("user.dir") + "\\src\\main\\resources";
        if(reportFormat.equalsIgnoreCase("pdf")) {
        	//JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\notas.pdf");
        	pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        	System.out.println(pdfBytes);
        	retorno = true; 
        }    
		return retorno && this.sendMail(correoTo, pdfBytes);
	}

	public boolean sendMail(String correoTo, byte[] pdfBytes) {
		
		 String userName = "prueba.david145@gmail.com";
		 String passWord = "Prueba67ytDavid"; // Mongo Atlas, Gmail
		 String passWord1 = "@Prueba67ytDavid"; // Heroku, Github
		
		 Properties props = new Properties();
	     props.put("mail.smtp.host" , "smtp.gmail.com");
	     props.put("mail.smtp.user" , userName); 

	     // Uso de TLS
	     props.put("mail.smtp.auth", "true"); 
	     props.put("mail.smtp.starttls.enable", "true");
	     props.put("mail.smtp.password", passWord);
	     
	     // Uso de SSL
	     props.put("mail.smtp.socketFactory.port", "587");
	     props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	     props.put("mail.smtp.auth", "true");
	     props.put("mail.smtp.port", "587");
	     
	     // Creacion de la sesion
	     Session session  = Session.getDefaultInstance(props , null);
	     
	     // Informacion del mensaje
	     String to = correoTo;
	     String subject = "Reporte de notas";
	     
	     // Creacion del mensaje
	     Message mensaje = new MimeMessage(session);
	     
	     try {
	    	 
	    	 mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	    	 mensaje.setSubject(subject);
	    	 mensaje.setText("Se ha exportado el reporte.");
	    	 
	    	// String fileName = System.getProperty("user.dir") + "\\src\\main\\resources" + "\\notas.pdf";
	    	// DataSource dataSource = new FileDataSource(fileName);
	    	 
	    	// Envio del archivo adjunto	    	
	    	 DataHandler dataHandler = new DataHandler(pdfBytes,"application/octet-stream");
	    	 
	    	 BodyPart messageBodyPart = new MimeBodyPart();
	    	// messageBodyPart.setDataHandler(new DataHandler(dataSource));
	    	 messageBodyPart.setDataHandler(dataHandler);
	         messageBodyPart.setFileName("notas.pdf");
	    	 
	    	 Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
	         
	         mensaje.setContent(multipart);
	         
	         // Enviar el mensaje
	         Transport transport = session.getTransport("smtp");
	         transport.connect("smtp.gmail.com" , 587 , userName, passWord);
	         Transport.send(mensaje, userName, passWord);
	         
	         // Borrar el archivo
	      /* File fichero = new File(fileName);
	         if (fichero.delete()) {
	        	 // Fichero borrado
	         } else {
	        	   // No se borro el fichero
	         } */
	         return true;
	        }
	        catch(Exception exc) {
	            System.out.println(exc);
	            return false;
	        }
	     
	     /* Nota: hay que desactivar "Aplicaciones seguras"
	        Link: https://myaccount.google.com/lesssecureapps */
	}
	
}
