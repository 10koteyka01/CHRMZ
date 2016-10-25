package DB_for_CRMZ;
import connect.HowToConnect;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.util.Calendar.DATE;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Gui_for_breakings extends javax.swing.JFrame {
private int ID_br, ID_machine, ID_worker, ID_worker_made;
private String br_mes, br_mes_made, sql;
private Date br_date, br_date_made;
private String sql_for_disp = "";
private String sql_for_disp_br_made = "";

SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
SimpleDateFormat format_for_db = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat format_for_db_show = new SimpleDateFormat("yyyy-MM-dd");

static HowToConnect how = For_general_methods.how;//экземпляр класса для подключения к БД

private final SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
private final Calendar date_before;
private final Calendar now_date = new GregorianCalendar();

boolean isChangedByButton = false;

String fam, name_w, patr, tel, fam_made, name_w_made;
int ID;
String dep, firm, name_mach, num;


For_general_methods give_method = new For_general_methods();

public Gui_for_breakings() {
    initComponents();
    date_before = new GregorianCalendar();
    date_before.add(Calendar.DATE, -31);//вычитаем месяц из текущего значения даты  
    now_date.add(DATE, 1);
    showTable(sql_for_disp); 
    show_table_for_call_close(sql_for_disp_br_made);
    initial_all_combos();
    }

private void initial_all_combos()
{
    fillCombo("machine", "department", combo_department);
    fillCombo("machine", "producer", combo_firm); 
    fillCombo("machine", "machine_number", combo_number);
    fillCombo("machine", "name_machine", combo_name_machine);
    fillCombo("workers", "Family", combo_worker_family);
    fillCombo("workers", "Name", combo_worker_name);
    fillCombo("workers", "Family", combo_br_worker);
    fillCombo("workers", "Name", combo_br_name_worker);
}
private void clean_all_comps()
{
    combo_department.setSelectedItem("");
    combo_firm.setSelectedItem("");
    combo_number.setSelectedItem("");
    combo_name_machine.setSelectedItem("");
    combo_worker_family.setSelectedItem("");
    combo_worker_name.setSelectedItem("");
    label_br_dep.setText("");
    label_br_firm.setText("");
    label_br_numb.setText("");
    label_br_name.setText("");
    combo_br_worker.setSelectedItem("");
    combo_br_name_worker.setSelectedItem("");
    jTextPane1.setText("");
    jTextPane3.setText("");
    jLabel18.setText("");
    jLabel20.setText("");
    workers_label_made.setText("");
    jLabel12.setText("");
    jLabel6.setText("");
    workers_label.setText("");
}

public void fillCombo(String name_table, String name_column, JComboBox box)
{
    sql = "SELECT DISTINCT " + name_column + " FROM " + name_table + " where Status is null;";
    try
    {
    how.pstat = how.con.prepareStatement(sql);
    how.rs = how.pstat.executeQuery();
    while(how.rs.next())
    {
        box.addItem(how.rs.getString(name_column));
    }
    }
    catch(SQLException e)
    {
        System.err.println("SQL exception occured with combobox filling " + e);
    }
    
}
public String search_by_ID(String name_column, String name_table, String name_ID, int num_ID)
{
    String item = "0";
    sql = "SELECT " + name_column + " FROM " + name_table + " where " + name_ID + " = " + num_ID + ";";
    try
    {
    how.pstat = how.con.prepareStatement(sql);
    how.rs = how.pstat.executeQuery();
    while(how.rs.next())
    {
    item = how.rs.getString(name_column);//какой-то косяк
    }
    }
    catch(SQLException e)
    {
        System.err.println("SQL exception occured with combobox filling of selected item: " + e);
    }
    return item;
}
   
private void showTable(String sql)//вывести таблицу по датам date1 - начальная дата, date2 - конечная дата
    {

    DefaultTableModel mod = new DefaultTableModel();
        
        mod.addColumn("ID");
        mod.addColumn("Цех");
        mod.addColumn("Название");
        mod.addColumn("Номер");
        mod.addColumn("Что сломалось");
        mod.addColumn("Дата и время");
        mod.addColumn("Фамилия");
        //добавляем к сегодняшней дате 1 день для корректного отображения таблицы
        jTable1.setModel(mod);
        try
        {
            //сортируем по сегодняшней дате, по умолчанию
            if(sql == null || sql.equals("")) sql = "SELECT breaking.ID_br, machine.department, machine.name_machine,\n" +
"machine.machine_number, breaking.br_mes, breaking.br_date, workers.Family\n" +
"FROM crmz.breaking\n" +
"INNER JOIN crmz.machine ON breaking.ID_machine = machine.ID_machine\n" +
"INNER JOIN crmz.workers ON breaking.ID_worker = workers.ID_worker \n" +
"where breaking.br_date BETWEEN '" + format_for_db_show.format(date_before.getTime()) + "' and '" + format_for_db_show.format(now_date.getTime()) + "' order by machine.department;"; 
  
            how.pstat = how.con.prepareStatement(sql);
            how.rs = how.pstat.executeQuery();
            while(how.rs.next())
            {
                ID_br = Integer.parseInt(how.rs.getString(1));
                String mach_dep = how.rs.getString(2);
                String mach_name = how.rs.getString(3);
                int mach_numb = Integer.parseInt(how.rs.getString(4));
                br_mes = how.rs.getString(5);
                br_date = how.rs.getTimestamp(6);
                String worker_fam = how.rs.getString(7);
                
                Object[] cont = {ID_br, mach_dep, mach_name, mach_numb, br_mes, br_date, worker_fam};
                
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
    private void show_table_for_call_close(String sql)
    {
        DefaultTableModel mod = new DefaultTableModel();
        
        mod.addColumn("ID");
        mod.addColumn("Цех");
        mod.addColumn("Название");
        mod.addColumn("Номер");
        mod.addColumn("Что сделано");
        mod.addColumn("Дата и время");
        mod.addColumn("Фамилия");
        jTable2.setModel(mod);
        try
        {
            //сортируем по сегодняшней дате, по умолчанию
            if(sql == null || sql.equals("")) sql = "SELECT breaking.ID_br, machine.department, machine.name_machine,\n" +
"machine.machine_number, breaking.br_mes_made, breaking.dr_date_made, workers.Family\n" +
"FROM crmz.breaking\n" +
"INNER JOIN crmz.machine ON breaking.ID_machine = machine.ID_machine\n" +
"INNER JOIN crmz.workers ON breaking.ID_worker_made = workers.ID_worker \n" +
"where \n" +
"breaking.br_date BETWEEN '" + format_for_db_show.format(date_before.getTime()) 
                + "' and '" + format_for_db_show.format(now_date.getTime()) + "' order by machine.department;";
            how.pstat = how.con.prepareStatement(sql);
            how.rs = how.pstat.executeQuery();
            while(how.rs.next())
            {
                ID_br = Integer.parseInt(how.rs.getString("ID_br"));
                String mach_dep = how.rs.getString(2);
                String mach_name = how.rs.getString(3);
                int mach_numb = Integer.parseInt(how.rs.getString(4));
                br_mes_made = how.rs.getString("br_mes_made");
                br_date_made = how.rs.getTimestamp("dr_date_made");
                String worker_fam_made = how.rs.getString(7);
                
                Object[] cont = {ID_br, mach_dep, mach_name, mach_numb, br_mes_made, br_date_made, worker_fam_made};
                
                mod.addRow(cont);
                jTable2.setModel(mod);
            }

            
        }
        catch(SQLException | NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Smth wrong with GUI");
            System.err.println("In method show_table_for_call_close occurs exception:");
            System.err.println(e);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        Станок = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        combo_department = new javax.swing.JComboBox<>();
        combo_firm = new javax.swing.JComboBox<>();
        combo_name_machine = new javax.swing.JComboBox<>();
        combo_number = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        combo_worker_family = new javax.swing.JComboBox<>();
        combo_worker_name = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        workers_label = new javax.swing.JLabel();
        jbr_date = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jbr_date_before = new javax.swing.JSpinner();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        Станок2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        label_br_dep = new javax.swing.JLabel();
        label_br_firm = new javax.swing.JLabel();
        label_br_name = new javax.swing.JLabel();
        label_br_numb = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        combo_br_worker = new javax.swing.JComboBox<>();
        combo_br_name_worker = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        workers_label_made = new javax.swing.JLabel();
        jbr_date_made = new javax.swing.JSpinner();
        jPanel9 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jbr_date_before1 = new javax.swing.JSpinner();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Станок.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Выбор станка", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), java.awt.Color.black)); // NOI18N
        Станок.setToolTipText("");
        Станок.setPreferredSize(new java.awt.Dimension(315, 312));
        Станок.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("цех");
        Станок.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 92, -1, -1));

        jLabel2.setText("фирма");
        Станок.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 123, 46, -1));

        jLabel3.setText("название");
        Станок.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 154, 56, -1));

        jLabel4.setText("номер");
        Станок.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 182, 56, -1));

        jButton1.setText("Выбрать");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Станок.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(138, 245, -1, -1));

        jLabel11.setText("ID");
        Станок.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));
        Станок.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 29, 14));

        combo_department.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Станок.add(combo_department, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 89, 137, -1));

        combo_firm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Станок.add(combo_firm, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 120, 137, -1));

        combo_name_machine.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Станок.add(combo_name_machine, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 151, 137, -1));

        combo_number.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Станок.add(combo_number, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 182, 137, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Вызов", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), java.awt.Color.black)); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(498, 250));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setText("Добавить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 245, -1, -1));

        jScrollPane1.setViewportView(jTextPane1);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 53, 271, 160));

        jLabel5.setText("ID");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 15, -1));

        jLabel6.setPreferredSize(new java.awt.Dimension(11, 14));
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 20, -1, -1));

        jLabel7.setText("Когда передан вызов");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jLabel8.setText("Наладчик");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        combo_worker_family.setEditable(true);
        combo_worker_family.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jPanel4.add(combo_worker_family, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 185, -1));

        combo_worker_name.setEditable(true);
        combo_worker_name.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jPanel4.add(combo_worker_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 185, -1));

        jLabel9.setText("Фамилия");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, -1, -1));

        jLabel10.setText("Имя");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, -1));

        jButton4.setText("Выбрать ответственного");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 245, 185, -1));

        jButton5.setText("Обновить");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 245, -1, -1));

        jButton6.setText("Удалить");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 245, -1, -1));
        jPanel4.add(workers_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 28, 16));

        jbr_date.setModel(new javax.swing.SpinnerDateModel());
        jbr_date.setEditor(new javax.swing.JSpinner.DateEditor(jbr_date, "HH:mm dd.MM.yyyy"));
        jPanel4.add(jbr_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 53, 185, -1));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Режим отображения базы", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), java.awt.Color.black)); // NOI18N

        jButton3.setText("Показать");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jbr_date_before.setModel(new javax.swing.SpinnerDateModel());
        jbr_date_before.setEditor(new javax.swing.JSpinner.DateEditor(jbr_date_before, " dd.MM.yyyy"));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Все вызовы за период");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Открытые вызовы за период");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jRadioButton1)
                .addGap(10, 10, 10)
                .addComponent(jRadioButton2)
                .addGap(18, 18, 18)
                .addComponent(jbr_date_before, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jButton3)
                .addGap(30, 30, 30))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton3)
                .addComponent(jbr_date_before, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jRadioButton1)
                .addComponent(jRadioButton2))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Цех", "Название", "Номер", "Дата и время", "Что сломалось", "Фамилия"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Станок, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                    .addComponent(Станок, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.addTab("Добавить вызов", jPanel1);

        Станок2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Выбор станка", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), java.awt.Color.black)); // NOI18N
        Станок2.setToolTipText("");
        Станок2.setAutoscrolls(true);
        Станок2.setPreferredSize(new java.awt.Dimension(315, 312));
        Станок2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setText("цех");
        Станок2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel14.setText("фирма");
        Станок2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 46, -1));

        jLabel15.setText("название");
        Станок2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 56, -1));

        jLabel16.setText("номер");
        Станок2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 56, -1));

        jLabel17.setText("ID");
        Станок2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 15, -1));
        Станок2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 29, 14));
        Станок2.add(label_br_dep, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 46, 14));
        Станок2.add(label_br_firm, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 46, 14));
        Станок2.add(label_br_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 46, 14));
        Станок2.add(label_br_numb, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 46, 14));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Вызов", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), java.awt.Color.black)); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(498, 278));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton8.setText("Закрыть (обновить) вызов");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 245, 184, -1));

        jScrollPane3.setViewportView(jTextPane3);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 53, 271, 160));

        jLabel19.setText("ID");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 15, -1));
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 20, 46, 14));

        jLabel21.setText("Когда закрыт вызов");
        jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jLabel22.setText("Наладчик");
        jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, -1));

        combo_br_worker.setEditable(true);
        combo_br_worker.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jPanel6.add(combo_br_worker, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 150, 185, -1));

        combo_br_name_worker.setEditable(true);
        combo_br_name_worker.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        jPanel6.add(combo_br_name_worker, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 185, -1));

        jLabel23.setText("Фамилия");
        jPanel6.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, -1, -1));

        jLabel24.setText("Имя");
        jPanel6.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, -1, -1));

        jButton9.setText("Выбрать ответственного");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 245, 185, -1));

        jButton11.setText("Удалить");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 245, -1, -1));
        jPanel6.add(workers_label_made, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 28, 16));

        jbr_date_made.setModel(new javax.swing.SpinnerDateModel());
        jbr_date_made.setEditor(new javax.swing.JSpinner.DateEditor(jbr_date_made, "HH:mm dd.MM.yyyy"));
        jPanel6.add(jbr_date_made, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 53, 185, -1));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Режим отображения базы", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), java.awt.Color.black)); // NOI18N

        jButton12.setText("Показать");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jbr_date_before1.setModel(new javax.swing.SpinnerDateModel());
        jbr_date_before1.setEditor(new javax.swing.JSpinner.DateEditor(jbr_date_before1, " dd.MM.yyyy"));

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Все вызовы за период");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Открытые вызовы за период");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jRadioButton3)
                .addGap(10, 10, 10)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addComponent(jbr_date_before1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton12)
                .addComponent(jbr_date_before1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jRadioButton3)
                .addComponent(jRadioButton4))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Цех", "Название", "Номер", "Дата и время", "Что cделано", "Фамилия"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Станок2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 516, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(257, 257, 257)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Станок2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(327, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Закрыть вызов", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//кнопка выбрать ответственного
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        fam = combo_worker_family.getSelectedItem().toString();
        name_w = combo_worker_name.getSelectedItem().toString();
        sql = "SELECT ID_worker FROM workers where Family = '" + fam + "' and Name = '" + name_w + "';";
        ID_worker = give_method.searchID(sql, workers_label);
    }//GEN-LAST:event_jButton4ActionPerformed
//выбор станка
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dep = combo_department.getSelectedItem().toString();
        firm = combo_firm.getSelectedItem().toString();
        name_mach = combo_name_machine.getSelectedItem().toString();
        num = combo_number.getSelectedItem().toString();
        sql = "Select ID_machine from machine where department = " 
                + dep + " and producer = '" + firm + "' and name_machine = '" 
                + name_mach + "' and machine_number = " + num + ";";
        ID_machine = give_method.searchID(sql, jLabel12);
    }//GEN-LAST:event_jButton1ActionPerformed
//добавить вызов в базу    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        br_mes = jTextPane1.getText();
        String formattedDate = sm.format(jbr_date.getValue());
        ID_br = give_method.search_of_empty_ID("breaking", "ID_br", 1);
        sql = "INSERT INTO crmz.breaking (`ID_br`, br_date, br_mes,"
                + " `ID_machine`, `ID_worker`, `ID_worker_made`, "
                + "dr_date_made, br_mes_made, br_stat) "
                + "VALUES (" + ID_br + ", '" + formattedDate + "', '" + br_mes + "', " + ID_machine + ", " + ID_worker + ", NULL, DEFAULT, NULL, NULL)";
        executeSQLQ(sql, "Inserted"); 
        showTable(null); 
        clean_all_comps();
    }//GEN-LAST:event_jButton2ActionPerformed
//щелчок по таблице
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        clean_all_comps();
        int i = jTable1.getSelectedRow();
        TableModel mod = jTable1.getModel();
        ID_br = (int) mod.getValueAt(i, 0);
        jLabel6.setText(String.valueOf(ID_br));
        ID_machine = Integer.parseInt(search_by_ID("ID_machine", "breaking", "ID_br", ID_br));
        ID_worker = Integer.parseInt(search_by_ID("ID_worker", "breaking", "ID_br" , ID_br));
        jLabel12.setText(String.valueOf(ID_machine));
        workers_label.setText(String.valueOf(ID_worker));
        jTextPane1.setText(mod.getValueAt(i, 4).toString());
        jbr_date.setValue(mod.getValueAt(i, 5));
        dep = search_by_ID("department", "machine", "ID_machine", ID_machine);
        firm = search_by_ID("producer", "machine", "ID_machine", ID_machine);
        name_mach = search_by_ID("name_machine", "machine", "ID_machine", ID_machine);
        num = search_by_ID("machine_number", "machine", "ID_machine", ID_machine);
        combo_department.setSelectedItem(dep);
        combo_firm.setSelectedItem(firm);
        combo_name_machine.setSelectedItem(name_mach);
        combo_number.setSelectedItem(num);
        fam = search_by_ID("Family", "workers", "ID_worker", ID_worker);
        name_w = search_by_ID("Name", "workers", "ID_worker", ID_worker);
        combo_worker_family.setSelectedItem(fam);
        combo_worker_name.setSelectedItem(name_w);
    }//GEN-LAST:event_jTable1MouseClicked
// обновить отображение БД
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        showTable(sql_for_disp); 
    }//GEN-LAST:event_jButton3ActionPerformed
//метод для обновления поля date_before из элемента jspinner
    private void upd_date_before(JSpinner spn)
    {
        date_before.setTime((Date) spn.getValue());
        date_before.add(Calendar.HOUR, 0);
        date_before.add(Calendar.MINUTE, 0);
    }
//удаление вызова из базы
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        ID_br = Integer.parseInt(jLabel6.getText());
        sql = "DELETE FROM crmz.breaking WHERE breaking.ID_br = '" + ID_br + "';";
        executeSQLQ(sql, "Deleted");
        showTable(null); 
        clean_all_comps();
    }//GEN-LAST:event_jButton6ActionPerformed
//обновление вызова
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        br_mes = jTextPane1.getText();
        String formattedDate = sm.format(jbr_date.getValue());
        ID_br = Integer.parseInt(jLabel6.getText());
        sql = "UPDATE crmz.breaking SET br_date = '" + formattedDate+ "', br_mes = '" + br_mes + "', ID_machine = '" + ID_machine + "', ID_worker = '" + ID_worker + "' WHERE ID_br = '" + ID_br + "' limit 1;";
        executeSQLQ(sql, "Updated"); 
        showTable(null); 
        clean_all_comps();
    }//GEN-LAST:event_jButton5ActionPerformed
// показать все станки
    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        upd_date_before(jbr_date_before); 
        sql_for_disp = null;
    }//GEN-LAST:event_jRadioButton1ActionPerformed
// показать станки на вызове
    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        upd_date_before(jbr_date_before);
        sql_for_disp = "SELECT breaking.ID_br, machine.department, machine.name_machine,\n" +
"machine.machine_number, breaking.br_mes, breaking.br_date, workers.Family\n" +
"FROM crmz.breaking\n" +
"INNER JOIN crmz.machine ON breaking.ID_machine = machine.ID_machine\n" +
"INNER JOIN crmz.workers ON breaking.ID_worker = workers.ID_worker \n" +
"where breaking.br_mes_made is NULL  \n" +
"and breaking.br_date BETWEEN '" + format_for_db_show.format(date_before.getTime()) 
                + "' and '" + format_for_db_show.format(now_date.getTime()) + "' order by machine.department;";
    }//GEN-LAST:event_jRadioButton2ActionPerformed
//закрыть (обновить) вызов
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        br_mes_made = jTextPane3.getText();
        String formattedDate = sm.format(jbr_date_made.getValue());
        ID_br = Integer.parseInt(jLabel20.getText());
        sql = "UPDATE crmz.breaking SET dr_date_made = '" + formattedDate+ "', br_mes_made = '" + br_mes_made + "', ID_worker_made = '" + ID_worker_made + "' WHERE ID_br = '" + ID_br + "' limit 1;";
        executeSQLQ(sql, "Updated"); 
        show_table_for_call_close(null); 
        clean_all_comps();
    }//GEN-LAST:event_jButton8ActionPerformed
//кнопка выбрать ответственного за выполненный ремонт
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        fam_made = combo_br_worker.getSelectedItem().toString();
        name_w_made = combo_br_name_worker.getSelectedItem().toString();
        sql = "SELECT ID_worker FROM workers where Family = '" + fam_made + "' and Name = '" + name_w_made + "';";
        ID_worker_made = give_method.searchID(sql, workers_label_made);
    }//GEN-LAST:event_jButton9ActionPerformed
//кнопка удалить
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        ID_br = Integer.parseInt(jLabel20.getText());
        sql = "DELETE FROM crmz.breaking WHERE breaking.ID_br = '" + ID_br + "';";
        executeSQLQ(sql, "Deleted");
        showTable(null); 
        show_table_for_call_close(null); 
        clean_all_comps();
    }//GEN-LAST:event_jButton11ActionPerformed
//отобразить таблицу с новыми параметрами даты и состоянием станков
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        show_table_for_call_close(sql_for_disp_br_made);
    }//GEN-LAST:event_jButton12ActionPerformed
//все вызовы за период для окна с закрытием вызова
    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        upd_date_before(jbr_date_before1);
        sql_for_disp_br_made = null;
    }//GEN-LAST:event_jRadioButton3ActionPerformed
//открытые вызовы за период для окна с закрытием вызова
    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        upd_date_before(jbr_date_before1);  
        sql_for_disp_br_made = "SELECT breaking.ID_br, machine.department, machine.name_machine,\n" +
"machine.machine_number, breaking.br_mes_made, breaking.dr_date_made, workers.Family\n" +
"FROM crmz.breaking\n" +
"INNER JOIN crmz.machine ON breaking.ID_machine = machine.ID_machine\n" +
"INNER JOIN crmz.workers ON breaking.ID_worker = workers.ID_worker \n" +
"where breaking.br_mes_made is NULL  \n" +
"and breaking.br_date BETWEEN '" + format_for_db_show.format(date_before.getTime()) 
                + "' and '" + format_for_db_show.format(now_date.getTime()) + "' order by machine.department;";
    }//GEN-LAST:event_jRadioButton4ActionPerformed
// заполняем поля из выделенной строки в таблице 2
    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        clean_all_comps();
        int i = jTable2.getSelectedRow();
        TableModel mod = jTable2.getModel();
        ID_br = (int) mod.getValueAt(i, 0);
        jLabel20.setText(String.valueOf(ID_br));
        ID_machine = Integer.parseInt(search_by_ID("ID_machine", "breaking", "ID_br", ID_br));
        ID_worker = Integer.parseInt(search_by_ID("ID_worker", "breaking", "ID_br" , ID_br));
        jLabel18.setText(String.valueOf(ID_machine));
        workers_label_made.setText(String.valueOf(ID_worker));
        Object str;
        if( (str = mod.getValueAt(i, 4)) != null)  jTextPane3.setText(str.toString());
        if( (str = mod.getValueAt(i, 5)) != null)  jbr_date_made.setValue(str);//возможно, придётся конвертировать в строку
        
        dep = search_by_ID("department", "machine", "ID_machine", ID_machine);
        firm = search_by_ID("producer", "machine", "ID_machine", ID_machine);
        name_mach = search_by_ID("name_machine", "machine", "ID_machine", ID_machine);
        num = search_by_ID("machine_number", "machine", "ID_machine", ID_machine);
        label_br_dep.setText(dep);
        label_br_firm.setText(firm);
        label_br_name.setText(name_mach);
        label_br_numb.setText(num);
        fam = search_by_ID("Family", "workers", "ID_worker", ID_worker);
        name_w = search_by_ID("Name", "workers", "ID_worker", ID_worker);
        combo_br_worker.setSelectedItem(fam);
        combo_br_name_worker.setSelectedItem(name_w);
    }//GEN-LAST:event_jTable2MouseClicked

    public void executeSQLQ(String query, String message)//выполнить запрос SQL
   {
       give_method.executeSQLQ(query, message); 
   }
   
    public void gui_start()
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui_for_breakings().setVisible(true);
            }
        });
    }
    /*public static void main(String args[]) {
      
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
    /*    try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui_for_breakings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

       
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Gui_for_breakings().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> combo_br_name_worker;
    private javax.swing.JComboBox<String> combo_br_worker;
    private javax.swing.JComboBox<String> combo_department;
    private javax.swing.JComboBox<String> combo_firm;
    private javax.swing.JComboBox<String> combo_name_machine;
    private javax.swing.JComboBox<String> combo_number;
    private javax.swing.JComboBox<String> combo_worker_family;
    private javax.swing.JComboBox<String> combo_worker_name;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JSpinner jbr_date;
    private javax.swing.JSpinner jbr_date_before;
    private javax.swing.JSpinner jbr_date_before1;
    private javax.swing.JSpinner jbr_date_made;
    private javax.swing.JLabel label_br_dep;
    private javax.swing.JLabel label_br_firm;
    private javax.swing.JLabel label_br_name;
    private javax.swing.JLabel label_br_numb;
    private javax.swing.JLabel workers_label;
    private javax.swing.JLabel workers_label_made;
    private javax.swing.JPanel Станок;
    private javax.swing.JPanel Станок2;
    // End of variables declaration//GEN-END:variables
}
