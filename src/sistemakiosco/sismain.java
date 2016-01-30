
package sistemakiosco;

import UI.Login;
import UI.MainMenu;
import Conexion.Conexion;
import Controller.ControladorBD;

public class sismain {
    
    private static Conexion conexion;
    private static ControladorBD controladorBD;

    public static ControladorBD getControladorBD() {
        return controladorBD;
    }
    
    public static Conexion getConexion() {
        return conexion;
    }
    private static Login login;
    
    public static void main(String[] args) {
        login = new Login();
        login.setVisible(true);
        
    }
    
    public static void accederSISKIOS(String usuario, String password){
        conexion = new Conexion(usuario,password);
        
            boolean resultadoConexion = conexion.conectar();
            if(resultadoConexion){
            login.setVisible(false);
            System.out.println("Conexion ok");
            new MainMenu().setVisible(true); 
            controladorBD = new ControladorBD();
            }
            else{
                System.out.println("error al conectarse");
            }
     

            
        }
    }
   
    

