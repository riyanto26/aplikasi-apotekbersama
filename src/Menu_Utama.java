package src;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Menu_Utama extends javax.swing.JFrame {

    public Menu_Utama() {
        initComponents();

        ImageIcon icon = new ImageIcon(getClass().getResource("/Images/logobiu.png"));
        Image image = icon.getImage();

        int width = 800;
        int height = 280;
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        jLabel2.setIcon(new ImageIcon(scaledImage));
        jLabel2.setPreferredSize(new Dimension(width, height));
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itemuser = new javax.swing.JMenuItem();
        itemkeluar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Apotik Bersama");
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(211, 211, 211));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));

        // jLabel2.setBackground(new java.awt.Color(0, 153, 102));
        // jLabel2.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(533, 533, 533)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(533, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        itemuser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/User 3 24 n p8.png"))); 
        itemuser.setText("Pengguna");
        itemuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemuserActionPerformed(evt);
            }
        });
        jMenu1.add(itemuser);

        itemkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Delete-Blue.png"))); 
        itemkeluar.setText("Keluar");
        itemkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemkeluarActionPerformed(evt);
            }
        });
        jMenu1.add(itemkeluar);
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Menu Data");
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Pills-1.png"))); 
        jMenuItem3.setText("Data Obat");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Bantuan");
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/About.png"))); 
        jMenuItem8.setText("Tentang");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
        );

        pack();
    }
    private void itemuserActionPerformed(java.awt.event.ActionEvent evt) {
        Form_Petugas fp = new Form_Petugas();
        fp.setVisible(true);
    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        Obat fo = new Obat();
        fo.setVisible(true);
    }

    private void itemkeluarActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {
        About about = new About();
        about.setVisible(true);
    }
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Menu_Utama().setVisible(true);
        });
    }
    private javax.swing.JMenuItem itemkeluar;
    private javax.swing.JMenuItem itemuser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
}
