package view;

import controller.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import model.QueryBase;
import model.QueryCubeGen;
import model.QueryCubeSpec;
import model.QueryDrilldown1;
import model.QueryDrilldown2;
import model.QueryRollup;


public class AppView extends javax.swing.JFrame {
    private Controller control;
    private DefaultTableModel hhBaseModel,
                              hhRollupModel,
                              hhDrilldown1Model,
                              hhDrilldown2Model,
                              cubeGenModel,
                              cubeSpecModel;
    
    public AppView(Controller control) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        this.control = control;
        initComponents();
        
        hhBaseTable.setAutoCreateRowSorter(true);
        hhBaseModel = (DefaultTableModel) hhBaseTable.getModel();
        
        hhRollupTable.setAutoCreateRowSorter(true);
        hhRollupModel = (DefaultTableModel) hhRollupTable.getModel();
        
        hhDrilldown1Table.setAutoCreateRowSorter(true);
        hhDrilldown1Model = (DefaultTableModel) hhDrilldown1Table.getModel();
        
        hhDrilldown2Table.setAutoCreateRowSorter(true);
        hhDrilldown2Model = (DefaultTableModel) hhDrilldown2Table.getModel();
        
        cubeGenTable.setAutoCreateRowSorter(true);
        cubeGenModel = (DefaultTableModel) cubeGenTable.getModel();
        
        cubeSpecTable.setAutoCreateRowSorter(true);
        cubeSpecModel = (DefaultTableModel) cubeSpecTable.getModel();
             
        this.setResizable(false);
        setLocationRelativeTo(null);  
        setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public void updateLocationDetails(ArrayList<Integer> mun,
                                        ArrayList<Integer> zone,
                                        ArrayList<Integer> brgy,
                                        ArrayList<Integer> purok){
        
        for(int i = 0; i < mun.size(); i++)
            munCombo.addItem(mun.get(i));
        
        for(int i = 0; i < zone.size(); i++)
            zoneCombo.addItem(zone.get(i));
        
        for(int i = 0; i < brgy.size(); i++)
            brgyCombo.addItem(brgy.get(i));
        
        for(int i = 0; i < purok.size(); i++)
            purokCombo.addItem(purok.get(i));
    }
    
    public void updateHHTables(ArrayList<QueryBase> base,
                                ArrayList<QueryRollup> rollup,
                                ArrayList<QueryDrilldown1> drill1,
                                ArrayList<QueryDrilldown2> drill2){
        Object temp[];
        
        hhBaseModel.getDataVector().removeAllElements();
        hhBaseModel.fireTableDataChanged();
        
        for(int i = 0; i < base.size(); i++) {
            temp = new Object[]{base.get(i).getLocation_id(),
                                base.get(i).getHouse_type(),
                                base.get(i).getHh_count(),
                                base.get(i).getRoof_avg(),
                                base.get(i).getWall_avg(),
                                base.get(i).getWater_avg(),
                                String.valueOf(base.get(i).getWelec_avg() * 100) + "%"};
            
            hhBaseModel.addRow(temp);
        }
        
        hhRollupModel.getDataVector().removeAllElements();
        hhRollupModel.fireTableDataChanged();
        
        for(int i = 0; i < rollup.size(); i++) {
            temp = new Object[]{rollup.get(i).getLocation_id(),
                                rollup.get(i).getHh_count(),
                                rollup.get(i).getRoof_avg(),
                                rollup.get(i).getWall_avg(),
                                rollup.get(i).getWater_avg(),
                                String.valueOf(rollup.get(i).getWelec_avg() * 100) + "%"};
            
            hhRollupModel.addRow(temp);
        }
        
        hhDrilldown1Model.getDataVector().removeAllElements();
        hhDrilldown1Model.fireTableDataChanged();
        
        for(int i = 0; i < drill1.size(); i++) {
            temp = new Object[]{drill1.get(i).getLocation_id(),
                                drill1.get(i).getHouse_type(),
                                drill1.get(i).getTenur_type(),
                                drill1.get(i).getHh_count(),
                                drill1.get(i).getRoof_avg(),
                                drill1.get(i).getWall_avg(),
                                drill1.get(i).getWater_avg(),
                                String.valueOf(drill1.get(i).getWelec_avg() * 100) + "%"};
            
            hhDrilldown1Model.addRow(temp);
        }
        
        hhDrilldown2Model.getDataVector().removeAllElements();
        hhDrilldown2Model.fireTableDataChanged();
        
        for(int i = 0; i < drill2.size(); i++) {
            temp = new Object[]{drill2.get(i).getLocation_id(),
                                drill2.get(i).getHouse_type(),
                                drill2.get(i).getTenur_type(),
                                drill2.get(i).getHousehold_id(),
                                drill2.get(i).getRoof_type(),
                                drill2.get(i).getWall_type(),
                                drill2.get(i).getWater_type(),
                                drill2.get(i).getWelec_status()};
            
            hhDrilldown2Model.addRow(temp);
        }
    }
    
    public void updateCubeTables(ArrayList<QueryCubeGen> gen,
                                ArrayList<QueryCubeSpec> spec){
        Object temp[];
        
        cubeGenModel.getDataVector().removeAllElements();
        cubeGenModel.fireTableDataChanged();
        
        for(int i = 0; i < gen.size(); i++) {
            temp = new Object[]{gen.get(i).getLocation_id(),
                                gen.get(i).getCalamity_type(),
                                gen.get(i).getFrequency_sum(),
                                String.valueOf(gen.get(i).getAid_avg() * 100) + "%",
                                String.valueOf(gen.get(i).getStrong_avg() * 100) + "%",
                                String.valueOf(gen.get(i).getWeak_avg() * 100) + "%",
                                String.valueOf(gen.get(i).getOthers_avg() * 100) + "%"};
            
            cubeGenModel.addRow(temp);
        }
        
        cubeSpecModel.getDataVector().removeAllElements();
        cubeSpecModel.fireTableDataChanged();
        
        for(int i = 0; i < spec.size(); i++) {
            temp = new Object[]{spec.get(i).getMun(),
                                spec.get(i).getZone(),
                                spec.get(i).getBrgy(),
                                spec.get(i).getPurok(),
                                spec.get(i).getCalamity_type(),
                                spec.get(i).getFrequency_sum(),
                                String.valueOf(spec.get(i).getAid_avg() * 100) + "%",
                                String.valueOf(spec.get(i).getStrong_avg() * 100) + "%",
                                String.valueOf(spec.get(i).getWeak_avg() * 100) + "%",
                                String.valueOf(spec.get(i).getOthers_avg() * 100) + "%"};
            
            cubeSpecModel.addRow(temp);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JTabbedPane();
        hhPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        hhBaseExecute = new javax.swing.JButton();
        calamityCheck = new javax.swing.JCheckBox();
        locationCheck = new javax.swing.JCheckBox();
        calamityCombo = new javax.swing.JComboBox();
        zoneButton = new javax.swing.JRadioButton();
        munCombo = new javax.swing.JComboBox();
        zoneCombo = new javax.swing.JComboBox();
        brgyButton = new javax.swing.JRadioButton();
        purokButton = new javax.swing.JRadioButton();
        purokCombo = new javax.swing.JComboBox();
        brgyCombo = new javax.swing.JComboBox();
        munButton = new javax.swing.JRadioButton();
        aidCombo = new javax.swing.JComboBox();
        hhTablePanel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        hhBaseTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        hhRollupTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        hhDrilldown1Table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        hhDrilldown2Table = new javax.swing.JTable();
        cubePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        cubeExecute = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        cubeGenTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        cubeSpecTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Original Queries");

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel1.setText("Average House Materials and Status of Households per Location");

        hhBaseExecute.setText("Execute");
        hhBaseExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hhBaseExecuteActionPerformed(evt);
            }
        });

        calamityCheck.setText("Calamity affected households");
        calamityCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calamityCheckActionPerformed(evt);
            }
        });

        locationCheck.setText("Location based");
        locationCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationCheckActionPerformed(evt);
            }
        });

        calamityCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Any calamity", "Bagyo", "Baha", "Tagtuyot", "Lindol", "Pagsabog ng Bulcan", "Landslide", "Tsunami", "Sunog", "Forest Fire", "Armadong Digmaan", "Others"}));
        calamityCombo.setEnabled(false);

        zoneButton.setText("Zone");
        zoneButton.setEnabled(false);
        zoneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoneButtonActionPerformed(evt);
            }
        });

        munCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { }));
        munCombo.setEnabled(false);

        zoneCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { }));
        zoneCombo.setEnabled(false);

        brgyButton.setText("Barangay");
        brgyButton.setEnabled(false);
        brgyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brgyButtonActionPerformed(evt);
            }
        });

        purokButton.setText("Purok");
        purokButton.setEnabled(false);
        purokButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purokButtonActionPerformed(evt);
            }
        });

        purokCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { }));
        purokCombo.setEnabled(false);

        brgyCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {  }));
        brgyCombo.setEnabled(false);

        munButton.setText("Municipality");
        munButton.setEnabled(false);
        munButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                munButtonActionPerformed(evt);
            }
        });

        aidCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Any aid status", "Receives aid", "Did not receive aid"}));
        aidCombo.setEnabled(false);

        hhBaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "location_id", "house_type_desc", "hh_count", "roof_avg", "wall_avg", "water_avg", "welec_avg"
            }){
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }}
            );
            jScrollPane10.setViewportView(hhBaseTable);

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 661, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 315, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
            );

            hhTablePanel.addTab("Base", jPanel1);

            hhRollupTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String [] {
                    "location_id", "hh_count", "roof_avg", "wall_avg", "water_avg", "welec_avg"
                }){
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                    }}
                );
                jScrollPane11.setViewportView(hhRollupTable);

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 661, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                );
                jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 315, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
                );

                hhTablePanel.addTab("Rollup", jPanel2);

                hhDrilldown1Table.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null}
                    },
                    new String [] {
                        "location_id", "house_type_desc", "tenur_desc", "hh_count", "roof_avg", "wall_avg", "water_avg", "welec_avg"
                    }){
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return false;
                        }}
                    );
                    jScrollPane12.setViewportView(hhDrilldown1Table);

                    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                    jPanel3.setLayout(jPanel3Layout);
                    jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 661, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                    );
                    jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 315, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
                    );

                    hhTablePanel.addTab("Drill Down 1", jPanel3);

                    hhDrilldown2Table.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                            {null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null},
                            {null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                            "location_id", "house_type_desc", "tenur_desc", "household_id", "roof_desc", "wall_desc", "water_desc", "welec_status"
                        }){
                            public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return false;
                            }}
                        );
                        jScrollPane13.setViewportView(hhDrilldown2Table);

                        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                        jPanel4.setLayout(jPanel4Layout);
                        jPanel4Layout.setHorizontalGroup(
                            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 661, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                        );
                        jPanel4Layout.setVerticalGroup(
                            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 315, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))
                        );

                        hhTablePanel.addTab("Drill Down 2", jPanel4);

                        javax.swing.GroupLayout hhPanelLayout = new javax.swing.GroupLayout(hhPanel);
                        hhPanel.setLayout(hhPanelLayout);
                        hhPanelLayout.setHorizontalGroup(
                            hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(hhPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hhTablePanel)
                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                        .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSeparator1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(hhBaseExecute))
                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                        .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(calamityCheck)
                                            .addGroup(hhPanelLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(calamityCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(aidCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(50, 50, 50)
                                        .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(locationCheck)
                                            .addGroup(hhPanelLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                                        .addComponent(zoneButton)
                                                        .addGap(34, 34, 34)
                                                        .addComponent(zoneCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                                        .addComponent(munButton)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(munCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                                        .addComponent(purokButton)
                                                        .addGap(20, 20, 20)
                                                        .addComponent(purokCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                                        .addComponent(brgyButton)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(brgyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
                        );
                        hhPanelLayout.setVerticalGroup(
                            hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hhPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hhBaseExecute))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                        .addComponent(calamityCheck)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(calamityCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(aidCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(hhPanelLayout.createSequentialGroup()
                                        .addComponent(locationCheck)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(hhPanelLayout.createSequentialGroup()
                                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(munCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(munButton))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(zoneButton)
                                                    .addComponent(zoneCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(hhPanelLayout.createSequentialGroup()
                                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(brgyButton)
                                                    .addComponent(brgyCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(hhPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(purokButton)
                                                    .addComponent(purokCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(18, 18, 18)
                                .addComponent(hhTablePanel)
                                .addContainerGap())
                        );

                        mainPanel.addTab(" Household Materials", hhPanel);

                        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
                        jLabel2.setText("Average House Materials, Aid Status, and Calamity Frequency per Location");

                        cubeExecute.setText("Execute");
                        cubeExecute.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cubeExecuteActionPerformed(evt);
                            }
                        });

                        cubeGenTable.setModel(new javax.swing.table.DefaultTableModel(
                            new Object [][] {
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null}
                            },
                            new String [] {
                                "location_id", "calamity_desc", "frequency_sum", "aid_avg", "strong_avg", "weak_avg", "others_avg"
                            }){
                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return false;
                                }}
                            );
                            jScrollPane15.setViewportView(cubeGenTable);

                            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                            jPanel5.setLayout(jPanel5Layout);
                            jPanel5Layout.setHorizontalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGap(0, 661, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                            );
                            jPanel5Layout.setVerticalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGap(0, 402, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane15))
                            );

                            jTabbedPane1.addTab("General Location", jPanel5);

                            cubeSpecTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object [][] {
                                    {null, null, null, null, null, null, null, null, null, null},
                                    {null, null, null, null, null, null, null, null, null, null},
                                    {null, null, null, null, null, null, null, null, null, null},
                                    {null, null, null, null, null, null, null, null, null, null}
                                },
                                new String [] {
                                    "mun", "zone", "brgy", "purok", "calamity_desc", "frequency_sum", "aid_avg", "strong_avg", "weak_avg", "others_avg"
                                }){
                                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                                        return false;
                                    }}
                                );
                                jScrollPane16.setViewportView(cubeSpecTable);

                                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                                jPanel6.setLayout(jPanel6Layout);
                                jPanel6Layout.setHorizontalGroup(
                                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGap(0, 661, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                                );
                                jPanel6Layout.setVerticalGroup(
                                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGap(0, 402, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane16))
                                );

                                jTabbedPane1.addTab("Specific Location", jPanel6);

                                javax.swing.GroupLayout cubePanelLayout = new javax.swing.GroupLayout(cubePanel);
                                cubePanel.setLayout(cubePanelLayout);
                                cubePanelLayout.setHorizontalGroup(
                                    cubePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(cubePanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(cubePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTabbedPane1)
                                            .addGroup(cubePanelLayout.createSequentialGroup()
                                                .addGroup(cubePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jSeparator2))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cubeExecute)))
                                        .addContainerGap())
                                );
                                cubePanelLayout.setVerticalGroup(
                                    cubePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cubePanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(cubePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cubeExecute))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTabbedPane1)
                                        .addContainerGap())
                                );

                                mainPanel.addTab("Calamity Cube", cubePanel);

                                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                                getContentPane().setLayout(layout);
                                layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mainPanel)
                                );
                                layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mainPanel)
                                );

                                pack();
                            }// </editor-fold>//GEN-END:initComponents

    private void hhBaseExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hhBaseExecuteActionPerformed
        ArrayList<String> cond = new ArrayList<String>();
        boolean isCalam = false;
        String mun, zone, brgy, purok;
        
        if( !calamityCheck.isSelected() && !locationCheck.isSelected()){
            control.updateHHTables();
        } else{           
            if(calamityCheck.isSelected()){
                isCalam = true;
                switch(calamityCombo.getSelectedItem().toString()){
                    case "Bagyo": cond.add("C.calamity_id = 1"); break;
                    case "Baha": cond.add("C.calamity_id = 2"); break;
                    case "Tagtuyot": cond.add("C.calamity_id = 3"); break;
                    case "Lindol": cond.add("C.calamity_id = 4"); break;
                    case "Pagsabog ng Bulcan": cond.add("C.calamity_id = 5"); break;
                    case "Landslide": cond.add("C.calamity_id = 6"); break;
                    case "Tsunami": cond.add("C.calamity_id = 7"); break;
                    case "Sunog": cond.add("C.calamity_id = 8"); break;
                    case "Forest Fire": cond.add("C.calamity_id = 9"); break;
                    case "Armadong Digmaan": cond.add("C.calamity_id = 10"); break;
                    case "Others": cond.add("C.calamity_id = 11"); break;
                }
                
                switch(aidCombo.getSelectedItem().toString()){
                    case "Receives aid": cond.add("C.avg_aid > 0"); break;
                    case "Did not receive aid": cond.add("C.avg_aid = 0"); break;
                }
            }
            
            if(locationCheck.isSelected()){   
                if(munButton.isSelected()){
                    mun = munCombo.getSelectedItem().toString();
                    cond.add("L.mun = " + mun);
                }
                
                if(zoneButton.isSelected()){
                    zone = zoneCombo.getSelectedItem().toString();
                    cond.add("L.zone = " + zone);
                }
                
                if(brgyButton.isSelected()){
                    brgy = brgyCombo.getSelectedItem().toString();
                    cond.add("L.brgy = " + brgy);
                }
                
                if(purokButton.isSelected()){
                    purok = purokCombo.getSelectedItem().toString();
                    cond.add("L.purok = " + purok);
                }
            }
            control.sliceHHTables(isCalam, cond);
        }
    }//GEN-LAST:event_hhBaseExecuteActionPerformed

    private void calamityCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calamityCheckActionPerformed
        if(calamityCheck.isSelected()){
            calamityCombo.setEnabled(true);
            aidCombo.setEnabled(true);
        } else{
            calamityCombo.setEnabled(false);
            aidCombo.setEnabled(false);
        }
    }//GEN-LAST:event_calamityCheckActionPerformed

    private void locationCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationCheckActionPerformed
        if(locationCheck.isSelected()){
            munButton.setEnabled(true);
            zoneButton.setEnabled(true);
            brgyButton.setEnabled(true);
            purokButton.setEnabled(true);
        } else{
            munButton.setEnabled(false);
            zoneButton.setEnabled(false);
            brgyButton.setEnabled(false);
            purokButton.setEnabled(false);
        }
    }//GEN-LAST:event_locationCheckActionPerformed

    private void munButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_munButtonActionPerformed
        if(munButton.isSelected())
            munCombo.setEnabled(true);
        else
            munCombo.setEnabled(false);
    }//GEN-LAST:event_munButtonActionPerformed

    private void zoneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoneButtonActionPerformed
        if(zoneButton.isSelected())
            zoneCombo.setEnabled(true);
        else
            zoneCombo.setEnabled(false);
    }//GEN-LAST:event_zoneButtonActionPerformed

    private void brgyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brgyButtonActionPerformed
        if(brgyButton.isSelected())
            brgyCombo.setEnabled(true);
        else
            brgyCombo.setEnabled(false);
    }//GEN-LAST:event_brgyButtonActionPerformed

    private void purokButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purokButtonActionPerformed
        if(purokButton.isSelected())
            purokCombo.setEnabled(true);
        else
            purokCombo.setEnabled(false);
    }//GEN-LAST:event_purokButtonActionPerformed

    private void cubeExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cubeExecuteActionPerformed
        control.updateCubeTables();
    }//GEN-LAST:event_cubeExecuteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox aidCombo;
    private javax.swing.JRadioButton brgyButton;
    private javax.swing.JComboBox brgyCombo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox calamityCheck;
    private javax.swing.JComboBox calamityCombo;
    private javax.swing.JButton cubeExecute;
    private javax.swing.JTable cubeGenTable;
    private javax.swing.JPanel cubePanel;
    private javax.swing.JTable cubeSpecTable;
    private javax.swing.JButton hhBaseExecute;
    private javax.swing.JTable hhBaseTable;
    private javax.swing.JTable hhDrilldown1Table;
    private javax.swing.JTable hhDrilldown2Table;
    private javax.swing.JPanel hhPanel;
    private javax.swing.JTable hhRollupTable;
    private javax.swing.JTabbedPane hhTablePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox locationCheck;
    private javax.swing.JTabbedPane mainPanel;
    private javax.swing.JRadioButton munButton;
    private javax.swing.JComboBox munCombo;
    private javax.swing.JRadioButton purokButton;
    private javax.swing.JComboBox purokCombo;
    private javax.swing.JRadioButton zoneButton;
    private javax.swing.JComboBox zoneCombo;
    // End of variables declaration//GEN-END:variables
}
