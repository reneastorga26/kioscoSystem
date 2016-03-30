
package sistemakiosco;

import UI.Login;
import UI.MainMenu;
import Conexion.Conexion;
import Controller.ControladorBD;
import Controller.ControladorDate;
import Watch.Reloj;
import javax.swing.JOptionPane;

public class sismain {
    
    private static Conexion conexion;
    private static ControladorBD controladorBD = new ControladorBD();
    private static ControladorDate controladorDate;
    private static Login login;
    private static Reloj reloj;

    public static Reloj getReloj() {
        return reloj;
    }
    

    public static Login getLogin() {
        return login;
    }

    public static ControladorDate getControladorDate() {
        return controladorDate;
    }
    
    
    public static ControladorBD getControladorBD() {
        return controladorBD;
    }
    
    public static Conexion getConexion() {
        return conexion;
    }

    
    public static void main(String[] args) {
        login = new Login();
        login.setVisible(true);
        reloj=new Reloj();
    }
    
    public static void accederSISKIOS(String usuario, String password){
        conexion = new Conexion(usuario,password);
        
            boolean resultadoConexion = conexion.conectar();
            if(resultadoConexion){
            login.setVisible(false);
            System.out.println("Conexion ok");
            new MainMenu().setVisible(true); 
            controladorDate = new ControladorDate();
            }
            else{
                System.out.println("error al conectarse");
                JOptionPane.showMessageDialog(null, "EL USUARIO Y/O PASSWORD NO SON CORRECTOS","Mensaje",JOptionPane.INFORMATION_MESSAGE);
            }
     

            
        }
    }
   
    

