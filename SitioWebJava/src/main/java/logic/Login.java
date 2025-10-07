package logic;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.LinkedList;



import data.*;
import entities.*;

public class Login {
	
private DataUsuario du;
private DataFacultad df;
	
	public Login() {
		du=new DataUsuario();
		df=new DataFacultad();
	}
	
	
	public Usuario validate(Usuario u) {
	    // 1. Buscar usuario en la BD por email
	    Usuario usuarioDB = du.getByEmail(u.getEmail());

	    if (usuarioDB == null) {
	        return null; // usuario no encontrado
	    }

	    try {
	        // 2. Validar contraseña ingresada contra la almacenada (hash + salt)
	        if (verifyPassword(u.getPassword(), usuarioDB.getPassword(), usuarioDB.getSalt())) {
	            return usuarioDB; // login correcto
	        } else {
	            return null; // contraseña incorrecta
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	


	public LinkedList<Usuario> getAll(){
		return du.getAll();								
	}
	
	public LinkedList<Facultad> getAllFacultades(){
		return df.getAllFacultades();
	}

	// --- Métodos utilitarios para password ---
	/*
	public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(salt.getBytes()); // aplicar salt
	    byte[] hashedBytes = md.digest(password.getBytes());
	    return Base64.getEncoder().encodeToString(hashedBytes);
	}
	*/
	
	public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    // Unimos explícitamente password + salt para evitar diferencias de orden
	    String combined = password + salt;
	    byte[] hashedBytes = md.digest(combined.getBytes(StandardCharsets.UTF_8));
	    return Base64.getEncoder().encodeToString(hashedBytes);
	}


	public static String generateSalt() {
	    byte[] salt = new byte[16];
	    new SecureRandom().nextBytes(salt);
	    return Base64.getEncoder().encodeToString(salt);
	}

	/*public static boolean verifyPassword(String enteredPassword, String storedHash, String storedSalt) 
	        throws NoSuchAlgorithmException  {
	    String newHash = hashPassword(enteredPassword, storedSalt);
	    return newHash.equals(storedHash);
	}*/
	public static boolean verifyPassword(String enteredPassword, String storedHash, String storedSalt) 
	        throws NoSuchAlgorithmException  {
	    String newHash = hashPassword(enteredPassword, storedSalt);

	    // Debug para ver qué pasa
	    System.out.println("Ingresada: " + enteredPassword);
	    System.out.println("Salt guardado: " + storedSalt);
	    System.out.println("Hash nuevo: " + newHash);
	    System.out.println("Hash guardado: " + storedHash);

	    return newHash.equals(storedHash);
	}
	// -----------------------------------------


}