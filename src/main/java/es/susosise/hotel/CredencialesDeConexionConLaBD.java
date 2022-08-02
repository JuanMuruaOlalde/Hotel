package es.susosise.hotel;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public final class CredencialesDeConexionConLaBD {
        private String baseDeDatos;
        private String usuario;
        private String contraseña;
        
        public CredencialesDeConexionConLaBD() {
            
        }
        
        public CredencialesDeConexionConLaBD(String baseDeDatos, String usuario, String contraseña) {
            this.baseDeDatos = baseDeDatos;
            this.usuario = usuario;
            this.contraseña = contraseña;
        }

        public String getBaseDeDatos() {
            return baseDeDatos;
        }

        public void setBaseDeDatos(String baseDeDatos) {
            this.baseDeDatos = baseDeDatos;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getContraseña() {
            return contraseña;
        }

        public void setContraseña(String contraseña) {
            this.contraseña = contraseña;
        }
        

        protected String encriptar(String texto) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            byte[] lpc = PreferenciasGeneralesDeLaAplicacion.getLpc().getBytes();
            SecretKeySpec secreto = new SecretKeySpec(lpc, "Blowfish");
            Cipher maquina = Cipher.getInstance("Blowfish");
            maquina.init(Cipher.ENCRYPT_MODE, secreto);
            byte[] texto2 = maquina.doFinal(texto.getBytes());
            return new String(Base64.getEncoder().encode(texto2));
        }
        
        protected String desencriptar(String texto) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            byte[] lpc = PreferenciasGeneralesDeLaAplicacion.getLpc().getBytes();
            SecretKeySpec secreto = new SecretKeySpec(lpc, "Blowfish");
            Cipher maquina = Cipher.getInstance("Blowfish");
            maquina.init(Cipher.DECRYPT_MODE, secreto);
            byte[] texto2 = maquina.doFinal(Base64.getDecoder().decode(texto));
            return new String(texto2);
        }
}
