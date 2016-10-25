
package connect;

import com.mysql.jdbc.Connection;
import java.awt.HeadlessException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public final class HowToConnect {
    public Connection con;
    public Statement stat;
    public PreparedStatement pstat;
    public ResultSet rs;
    
    public HowToConnect()
    {
        systemConnection();
    }
    
    public void systemConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/crmz?zeroDateTimeBehavior=convertToNull", "root", "root");
            //добавил "?zeroDateTimeBehavior=convertToNull", чтобы корректно отображалась дата со значением NULL
            stat = (Statement) con.createStatement();
            JOptionPane.showMessageDialog(null, "It's all successfully complited");
        }
        catch(ClassNotFoundException | SQLException | HeadlessException ex)
        {
            JOptionPane.showMessageDialog(null, "Exception occured in how to connect class");
        }
        
    }
}
