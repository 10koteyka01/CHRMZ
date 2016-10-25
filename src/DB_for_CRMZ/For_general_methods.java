
package DB_for_CRMZ;

import connect.HowToConnect;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class For_general_methods {
    String fam, name_w, patr, tel;
    //int ID;
    
static HowToConnect how = new HowToConnect();
public void executeSQLQ(String query, String message)//выполнить запрос SQL
    {
        try 
        {
            if((how.pstat.executeUpdate(query)) == 1)
            {
                JOptionPane.showMessageDialog(null, "Data " + message + " succesfully");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Data not " + message);
            }
        } 
        catch (SQLException ex) 
        {
            System.err.println("Smth wrong with SQL, it write's this message: " + ex);
        }
   }
public int search_of_empty_ID(String name_table, String name_column, int num_of_column)
{
    /*
    *name_column - название колонки ID в таблице name_table, в столбце с номером num_of_column
    *проверяем все строки, передаём их значение в массив и ищем неиспользуемое ID
    *метод должен передать его числовое значение
    */
    int ID = 1;
    
    try
    { 
    String sql = "Select * from " + name_table + " order by " + name_column + ";";
    how.pstat = how.con.prepareStatement(sql);
    how.rs = how.pstat.executeQuery();
    ArrayList<String> buf = new ArrayList<>();
    while(how.rs.next())
    {
        buf.add(how.rs.getString(num_of_column));
    }
    
    Iterator <String> iter = buf.iterator();
    
    while(iter.hasNext())
    {
        
        if(ID != Integer.parseInt(iter.next()))  
            break;
        ID++;
    }

    }
    catch(SQLException | NumberFormatException ex)
    {
        System.err.println("Поиск свободного значения ID завершился со следующей ошибкой " + ex);
    }
    return ID;
}
public int searchID(String sql, JLabel label)
{
    int ID = 0;
    try
    {
    how.pstat = how.con.prepareStatement(sql);
    how.rs = how.pstat.executeQuery();
    while(how.rs.next())
    {
        ID = Integer.parseInt(how.rs.getString(1));
        label.setText(String.valueOf(ID));
    }
    }
    catch(SQLException e)
    {
        System.err.println("SQL exception occured with machine ID search " + e);
    }
    return ID;
}

}