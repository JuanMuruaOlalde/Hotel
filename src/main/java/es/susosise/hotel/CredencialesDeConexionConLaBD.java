package es.susosise.hotel;

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
        

}
