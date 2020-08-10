package com.main.security.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.main.security.document.ImageModel;
import com.main.security.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void crearImagen(MultipartFile file) {
    	ImageModel imagen;
		try {
			imagen = new ImageModel(file.getOriginalFilename(), 
					                file.getContentType(),
					  compressBytes(file.getBytes()));
			imageRepository.crearImagen(imagen);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
    
    public void crearImagenAdmin() {
		BufferedImage buffer = this.leerImagen(System.getProperty("user.dir") + "\\src\\main\\resources\\" + "admin.jpg");
    	ImageModel imagen;
		try {
			imagen = new ImageModel("admin", 
					                "image/jpeg",
					  compressBytes(obtenerBytesDeBuffer(buffer)));
			imageRepository.crearImagen(imagen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    public ImageModel obtenerImagen(String name) {
    	return imageRepository.obtenerImagen(name);
    }
    
    public void borrarImagen(String name) {
    	imageRepository.borrarImagen(name);
    }
    
	public boolean existeImagen(String filename) {
		return imageRepository.existeImagen(filename);
	}

    // Comprime los bytes de la imagen 
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}
	
	// Descomprime los bytes de la imagen
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	private BufferedImage leerImagen(String filenameOrigen) {
		try {
			// Crear un archivo de origen
			File file = new File(filenameOrigen);
			// Leer el archivo y ponerlo en el buffer
			BufferedImage buffer = ImageIO.read(file);
			return buffer;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] obtenerBytesDeBuffer(BufferedImage buffer) {
		try {
			// Conversion de BufferedImage a ByteArrayOutputStream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(buffer, "jpg", baos);
			baos.flush();
			// Obtencion de byte[] a partir de ByteArrayOutputStream
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
