package DB_for_CRMZ;

import connect.HowToConnect;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CallReport extends javax.swing.JFrame {
    
    public CallReport() {
    initComponents();
    }
    
SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
SimpleDateFormat format_for_db = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat format_for_db_show = new SimpleDateFormat("yyyy-MM-dd");

static HowToConnect how = For_general_methods.how;//экземпляр класса для подключения к БД
For_general_methods give_method = new For_general_methods();
String sql = "";
private final SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
Calendar date_before;
Calendar now_date;

private void show_table_calls()
{
    DefaultTableModel mod = new DefaultTableModel();
        
        
        mod.addColumn("Цех");
        mod.addColumn("Название");
        mod.addColumn("Номер");
        mod.addColumn("Дата и время");
        mod.addColumn("Что сломалось");
        mod.addColumn("Что сделано");
        mod.addColumn("Вермя закрытия");
        mod.addColumn("Фамилия");
        
        Table_for_calls.setModel(mod);
        try
        {
        date_before = upd_date(jbr_date_before, 0, 0);
        now_date = upd_date(jbr_date_now, 23, 59);     
            //сортируем по сегодняшней дате, по умолчанию
        sql = "SELECT machine.department, machine.name_machine,\n" +
"machine.machine_number, breaking.br_date, breaking.br_mes, breaking.br_mes_made,\n" +
"breaking.dr_date_made, workers.Family\n" +
"FROM crmz.breaking\n" +
"INNER JOIN crmz.machine ON breaking.ID_machine = machine.ID_machine\n" +
"INNER JOIN crmz.workers ON breaking.ID_worker_made = workers.ID_worker " +
"where breaking.br_date BETWEEN '" + format_for_db_show.format(date_before.getTime()) + "' and '" + format_for_db_show.format(now_date.getTime()) + "' order by machine.department;"; 
  
            how.pstat = how.con.prepareStatement(sql);
            how.rs = how.pstat.executeQuery();
            while(how.rs.next())
            {
                String mach_dep = how.rs.getString(1);
                String mach_name = how.rs.getString(2);
                int mach_numb = Integer.parseInt(how.rs.getString(3));
                Date br_date = how.rs.getTimestamp(4);
                String br_mes = how.rs.getString(5);
                String br_mes_made = how.rs.getString(6);
                Date br_date_made = how.rs.getTimestamp(7);
                String family = how.rs.getString(8);
                Object[] cont = {mach_dep, mach_name, mach_numb, br_date, br_mes, br_mes_made, br_date_made, family};
                
                mod.addRow(cont);
                Table_for_calls.setModel(mod);
            }
        }
        catch(SQLException | NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Smth wrong with GUI");
            System.err.println(e);
        }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbr_date_before = new javax.swing.JSpinner();
        jbr_date_now = new javax.swing.JSpinner();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_for_calls = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbr_date_before.setModel(new javax.swing.SpinnerDateModel());
        jbr_date_before.setEditor(new javax.swing.JSpinner.DateEditor(jbr_date_before, "dd.MM.yyyy"));
        jPanel1.add(jbr_date_before, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 110, -1));

        jbr_date_now.setModel(new javax.swing.SpinnerDateModel());
        jbr_date_now.setEditor(new javax.swing.JSpinner.DateEditor(jbr_date_now, "dd.MM.yyyy"));
        jPanel1.add(jbr_date_now, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 110, -1));

        jButton1.setText("Вывести отчёт");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 120, -1));

        jButton2.setText("Печать");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 120, -1));

        Table_for_calls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Цех", "Название", "№ станка", "Время вызова", "Что сломалось", "Что сделано", "Время закрытия", "Кто закрыл"
            }
        ));
        jScrollPane1.setViewportView(Table_for_calls);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //метод для обновления поля date_before из элемента jspinner
    private Calendar upd_date(JSpinner spn, int hour, int min)
    {
        Calendar date = new GregorianCalendar();
        date.setTime((Date) spn.getValue());
        date.add(Calendar.HOUR, hour);
        date.add(Calendar.MINUTE, min);
        return date;
    }
    //показать таблицу с параметрами, указаными в jSpinner
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        show_table_calls();
    }//GEN-LAST:event_jButton1ActionPerformed
//команда на печать файла
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MessageFormat header = new MessageFormat("Отчёт по вызовам");
        MessageFormat footer = new MessageFormat("Page[0, number, integer]");
        try
        {
            Table_for_calls.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        }
        catch(java.awt.print.PrinterException e)
        {
            System.err.println("Cannot print list" + e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
public void gui_start()
{
    /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
     try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CallReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CallReport().setVisible(true);
            }
        });
}/*
public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
/*        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CallReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
     /*   java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CallReport().setVisible(true);
            }
        });
    }
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_for_calls;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jbr_date_before;
    private javax.swing.JSpinner jbr_date_now;
    // End of variables declaration//GEN-END:variables
}
