
package DB_for_CRMZ;

import connect.HowToConnect;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Gui_for_machine extends javax.swing.JFrame {
    static HowToConnect how = For_general_methods.how;//экземпляр класса для подключение к БД
    For_general_methods give_method = new For_general_methods();
    
    String dep, firm, name_mach, num;
    int ID;
   
    public Gui_for_machine() {
        initComponents();
        showTable("");
    }
    
    public void initialVar()
   {
       dep = jdep.getText();
       firm = jfirm.getText();
       name_mach = jname_mach.getText();
       num = jnum.getText();
       ID = Integer.parseInt(jLabel2.getText());
   }
    
    public void cleanEdit()
   {
     jdep.setText("");
     jfirm.setText("");
     jname_mach.setText("");
     jnum.setText("");
     jLabel2.setText("");
     jdep.requestFocus();
   }
    
    private void showTable(String sql)//поиск в таблице по фамилии
    {
        DefaultTableModel mod = new DefaultTableModel();
        
        mod.addColumn("Цех");
        mod.addColumn("Фирма-изготовитель");
        mod.addColumn("Название станка");
        mod.addColumn("Номер");
        mod.addColumn("ID");
        
        jTable1.setModel(mod);
        try
        {
            if(sql == null || sql.equals("")) sql = "Select * from machine where machine.Status is null order by department, producer, name_machine, machine_number;";//сортируем станки по всей информации
            how.pstat = how.con.prepareStatement(sql);
            how.rs = how.pstat.executeQuery();
            while(how.rs.next())
            {
                ID = Integer.parseInt(how.rs.getString(5));
                dep = how.rs.getString(1);
                firm = how.rs.getString(2);
                name_mach = how.rs.getString(3);
                num = how.rs.getString(4);
                
                Object[] cont = {dep, firm, name_mach, num, ID};
                
               
                mod.addRow(cont);
                jTable1.setModel(mod);
            }
        }
        catch(SQLException | NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Smth wrong with GUI");
            System.err.println(e);
        }
    }
    public void executeSQLQ(String query, String message)//выполнить запрос SQL
   {
       give_method.executeSQLQ(query, message); 
   }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("crmz?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        machineQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT m FROM Machine m");
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jnum = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jdep = new javax.swing.JTextField();
        jfirm = new javax.swing.JTextField();
        jname_mach = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Цех", "Фирма-изготовитель", "Название", "Номер"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("ID");

        jLabel3.setText("Цех");

        jLabel4.setText("Фирма-изготовитель");

        jLabel5.setText("Название");

        jLabel6.setText("Номер");

        jButton1.setText("Добавить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Обновить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Скрыть");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Поиск");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Показать таблицу");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem2.setText("Показать всю таблицу");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Восстановить");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem1.setText("Удалить");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jname_mach, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(jdep))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(jnum)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(jButton5)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jdep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jname_mach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        initialVar();
        ID = give_method.search_of_empty_ID("crmz.machine", "ID_machine", 5);
        String query = "INSERT INTO crmz.machine (department, producer,"
                + " name_machine, machine_number, `ID_machine`) "
                + "VALUES (" + Integer.parseInt(dep) + ", '" + firm + "', '" + name_mach + "', " + Integer.parseInt(num) + ", " + ID + ");";
        executeSQLQ(query, "Inserted"); 
        showTable("");
        cleanEdit();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int i = jTable1.getSelectedRow();
        TableModel mod = jTable1.getModel();
        jLabel2.setText(mod.getValueAt(i, 4).toString());
        jdep.setText(mod.getValueAt(i, 0).toString());
        jfirm.setText(mod.getValueAt(i, 1).toString());
        jname_mach.setText(mod.getValueAt(i, 2).toString());
        jnum.setText(mod.getValueAt(i, 3).toString());
    }//GEN-LAST:event_jTable1MouseClicked
//кнопка обновить
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        initialVar();
        String query = "UPDATE crmz.machine SET department = '" + Integer.parseInt(dep) + "',producer= '" 
                + firm + "', name_machine = '" + name_mach + "',machine_number = '" + 
                Integer.parseInt(num) + "' WHERE ID_machine = '" + ID + "'limit 1;";
        executeSQLQ(query, "Updated");
        showTable("");
        cleanEdit();
    }//GEN-LAST:event_jButton2ActionPerformed
//кнопка скрыть
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        initialVar();
        String query = "UPDATE crmz.machine SET machine.Status = 'hide' WHERE ID_machine = '" + ID + "'limit 1;";
        executeSQLQ(query, "updated");
        showTable("");
        cleanEdit();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        initialVar();
        String query = "SELECT * FROM machine where department like '"+ Integer.parseInt(dep) +"' and Status is NULL;";
        showTable(query);
        cleanEdit();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       showTable("");
    }//GEN-LAST:event_jButton5ActionPerformed
        //кнопка меню удалить
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        initialVar();
        String query = "DELETE FROM machine WHERE ID_machine = '" + ID + "';";
        executeSQLQ(query, "Deleted");
        showTable("");
        cleanEdit();  
    }//GEN-LAST:event_jMenuItem1ActionPerformed
//кнопка меню показать всю таблицу
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        showTable("Select * from machine order by department, producer, name_machine, machine_number;");
    }//GEN-LAST:event_jMenuItem2ActionPerformed
//кнопка меню восстановить
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        initialVar();
        String query = "UPDATE crmz.machine SET machine.Status = NULL WHERE `ID_machine` = '" + ID + "' limit 1;";
        executeSQLQ(query, "updated");
        showTable("");
        cleanEdit();
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    public void gui_start()
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui_for_machine().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jdep;
    private javax.swing.JTextField jfirm;
    private javax.swing.JTextField jname_mach;
    private javax.swing.JTextField jnum;
    private javax.persistence.Query machineQuery;
    // End of variables declaration//GEN-END:variables
}
