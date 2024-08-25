package src;

import Koneksi_DB.koneksinya;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class Obat extends javax.swing.JFrame {

    ResultSet Rs;

    public Obat() {
        initComponents();
        populateKategoriComboBox(); 
        Datatabel();
    }

    public void Tabelklik() {
        int row = tabelobat.getSelectedRow();
        txtidobat.setText(tabelobat.getValueAt(row, 0).toString());
        txtnama.setText(tabelobat.getValueAt(row, 1).toString());
        txtkategori.setSelectedItem(tabelobat.getValueAt(row, 2).toString());
        txtbentuk.setText(tabelobat.getValueAt(row, 3).toString());
        txtdosis.setText(tabelobat.getValueAt(row, 4).toString());
        txttglkadaluarsa.setText(tabelobat.getValueAt(row, 5).toString());
        txtharga.setText(tabelobat.getValueAt(row, 6).toString());
        txtsupplier.setText(tabelobat.getValueAt(row, 7).toString());
        txtefek.setText(tabelobat.getValueAt(row, 8).toString());
        txtkontraindikasi.setText(tabelobat.getValueAt(row, 9).toString());
        txtinstruksi.setText(tabelobat.getValueAt(row, 10).toString());
    }

    private void Autonomor() {
        String sql = "SELECT MAX(id_obat) FROM obat";
        try {
            Statement state = koneksinya.GetConnection().createStatement();
            ResultSet Rs = state.executeQuery(sql);
            
            int nextId = 1;
            if (Rs.next()) {
                String maxIdStr = Rs.getString(1);
                if (maxIdStr != null) {
                    try {
                        int maxId = Integer.parseInt(maxIdStr.replace("OB", ""));
                        nextId = maxId + 1;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Error parsing ID: " + e.getMessage());
                    }
                }
            }
            
            txtidobat.setText("OB" + nextId);
            
            Rs.close();
            state.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    

    public void Reset() {
        txtidobat.setText(null);
        txtnama.setText(null);
        txtkategori.setSelectedItem(null);
        txtbentuk.setText(null);
        txtdosis.setText(null);
        txttglkadaluarsa.setText(null);
        txtharga.setText(null);
        txtsupplier.setText(null);
        txtefek.setText(null);
        txtkontraindikasi.setText(null);
        txtinstruksi.setText(null);
    }

    public void Datatabel() {
        DefaultTableModel tabel = new DefaultTableModel();
        tabel.addColumn("ID Obat");
        tabel.addColumn("Nama Obat");
        tabel.addColumn("Kategori Obat");
        tabel.addColumn("Bentuk Sediaan");
        tabel.addColumn("Dosis");
        tabel.addColumn("Tgl Kadaluarsa");
        tabel.addColumn("Harga");
        tabel.addColumn("Supplier");
        tabel.addColumn("Efek Samping");
        tabel.addColumn("Kontraindikasi");
        tabel.addColumn("Instruksi Penggunaan");
        
        try {
            Statement state = koneksinya.GetConnection().createStatement();
            ResultSet Rs = state.executeQuery("SELECT * FROM obat");
            
            while (Rs.next()) {
                tabel.addRow(new Object[]{
                    Rs.getString("id_obat"),
                    Rs.getString("nama_obat"),
                    Rs.getString("kategori"),
                    Rs.getString("bentuk_sediaan"),
                    Rs.getString("dosis"),
                    Rs.getDate("tgl_kadaluarsa"),
                    Rs.getInt("harga"),
                    Rs.getString("supplier"),
                    Rs.getString("efek_samping"),
                    Rs.getString("kontraindikasi"),
                    Rs.getString("instruksi_penggunaan")
                });
            }
            
            tabelobat.setModel(tabel);
            
            state.close();
            Rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    public void Simpan() {
        String idObat = txtidobat.getText();
        String namaObat = txtnama.getText();
        String kategoriObat = txtkategori.getSelectedItem().toString();
        String bentukSediaan = txtbentuk.getText();
        String dosis = txtdosis.getText();
        String tglKadaluarsa = txttglkadaluarsa.getText();
        String hargaStr = txtharga.getText();
        String supplier = txtsupplier.getText();
        String efekSamping = txtefek.getText();
        String kontraindikasi = txtkontraindikasi.getText();
        String instruksiPenggunaan = txtinstruksi.getText();

        if (idObat.isEmpty() || namaObat.isEmpty() || kategoriObat.isEmpty() || bentukSediaan.isEmpty() || dosis.isEmpty() || 
            tglKadaluarsa.isEmpty() || hargaStr.isEmpty() || supplier.isEmpty() || efekSamping.isEmpty() || kontraindikasi.isEmpty() || instruksiPenggunaan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua Kolom Wajib Diisi!", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        String idKategori = "";
        String namaKategori = "";
    
        if (kategoriObat.contains(" - ")) {
            String[] kategoriParts = kategoriObat.split(" - ");
            if (kategoriParts.length == 2) {
                idKategori = kategoriParts[0];
                namaKategori = kategoriParts[1];
            }
        }
    
        int harga;
        try {
            harga = Integer.parseInt(hargaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(tglKadaluarsa);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Tanggal Kadaluarsa harus berupa format yyyy-MM-dd!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

    
        try {
            Statement state = koneksinya.GetConnection().createStatement();
            state.executeUpdate("INSERT INTO obat (id_obat, nama_obat, id_kategori, kategori, bentuk_sediaan, dosis, tgl_kadaluarsa, harga, supplier, efek_samping, kontraindikasi, instruksi_penggunaan) VALUES ('" + idObat + "', '" + namaObat + "', '" + idKategori + "', '" + namaKategori + "', '" + bentukSediaan + "', '" + dosis + "', '" + tglKadaluarsa + "', " + harga + ", '" + supplier + "', '" + efekSamping + "', '" + kontraindikasi + "', '" + instruksiPenggunaan + "')");
            JOptionPane.showMessageDialog(this, "Data Obat Berhasil Disimpan!");
            Reset();
            Datatabel();
            state.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data Obat Gagal Disimpan!");
            System.out.println("Error: " + e.getMessage());
        }
    }
    

    public void Update() {
        String idObat = txtidobat.getText();
        String namaObat = txtnama.getText();
        String kategoriObat = txtkategori.getSelectedItem().toString();
        String bentukSediaan = txtbentuk.getText();
        String dosis = txtdosis.getText();
        String tglKadaluarsa = txttglkadaluarsa.getText();
        String hargaStr = txtharga.getText();
        String supplier = txtsupplier.getText();
        String efekSamping = txtefek.getText();
        String kontraindikasi = txtkontraindikasi.getText();
        String instruksiPenggunaan = txtinstruksi.getText();

            if (idObat.isEmpty() || namaObat.isEmpty() || kategoriObat.isEmpty() || bentukSediaan.isEmpty() || dosis.isEmpty() || 
            tglKadaluarsa.isEmpty() || hargaStr.isEmpty() || supplier.isEmpty() || efekSamping.isEmpty() || kontraindikasi.isEmpty() || instruksiPenggunaan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua Kolom Wajib Diisi!", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        
        String idKategori = "";
        String namaKategori = "";
    
        if (kategoriObat.contains(" - ")) {
            String[] kategoriParts = kategoriObat.split(" - ");
            if (kategoriParts.length == 2) {
                idKategori = kategoriParts[0];
                namaKategori = kategoriParts[1];
            }
        }
    
        int harga;
        try {
            harga = Integer.parseInt(hargaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(tglKadaluarsa);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Tanggal Kadaluarsa harus berupa format yyyy-MM-dd!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Statement state = koneksinya.GetConnection().createStatement();
            state.executeUpdate("UPDATE obat SET nama_obat='" + namaObat + "', id_kategori='" + idKategori + "', kategori='" + namaKategori + "', bentuk_sediaan='" + bentukSediaan + "', dosis='" + dosis + "', tgl_kadaluarsa='" + tglKadaluarsa + "', harga=" + harga + ", supplier='" + supplier + "', efek_samping='" + efekSamping + "', kontraindikasi='" + kontraindikasi + "', instruksi_penggunaan='" + instruksiPenggunaan + "' WHERE id_obat='" + idObat + "'");
            JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate!");
            Reset();
            Datatabel();
            state.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data Gagal Diupdate!");
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void Delete() {
        String idObat = txtidobat.getText();
        try {
            Statement state = koneksinya.GetConnection().createStatement();
            state.executeUpdate("DELETE FROM obat WHERE id_obat='" + idObat + "'");
            JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus!");
            Reset();
            Datatabel();
            state.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data Gagal Dihapus!");
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelobat = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtidobat = new javax.swing.JTextField();
        txtnama = new javax.swing.JTextField();
        txtkategori = new JComboBox<>();
        txtbentuk = new javax.swing.JTextField();
        txtdosis = new javax.swing.JTextField();
        txttglkadaluarsa = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtsupplier = new javax.swing.JTextField();
        txtefek = new javax.swing.JTextField();
        txtkontraindikasi = new javax.swing.JTextField();
        txtinstruksi = new javax.swing.JTextField();
        btnget = new javax.swing.JButton();
        btnsimpan = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();

        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15));
        jLabel3.setForeground(new java.awt.Color(255, 204, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/books.png")));
        jLabel3.setText("DETAIL OBAT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        tabelobat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "ID OBAT", "NAMA OBAT"
            }
        ));
        tabelobat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelobatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelobat);

        jLabel1.setText("ID OBAT");
        jLabel2.setText("NAMA OBAT");
        jLabel4.setText("KATEGORI OBAT");
        jLabel5.setText("BENTUK SEDIAAN");
        jLabel6.setText("DOSIS");
        jLabel7.setText("TGL KADALUARSA");
        jLabel8.setText("HARGA");
        jLabel9.setText("SUPPLIER");
        jLabel10.setText("EFEK SAMPING");
        jLabel11.setText("KONTRAINDIKASI");
        jLabel12.setText("INSTRUKSI PENGGUNAAN");

        btnget.setText("GET CODE");
        btnget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btngetActionPerformed(evt);
            }
        });

        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Save Blue 24 n p8.png")));
        btnsimpan.setText("SIMPAN");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Edit.png")));
        btnupdate.setText("UPDATE");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Recycle Bin.png")));
        btndelete.setText("DELETE");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });
        
        // btnclear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Recycle Bin.png")));
        btnclear = new javax.swing.JButton();
        btnclear.setText("Clear");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    )
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtinstruksi)
                    .addComponent(txtkontraindikasi)
                    .addComponent(txtefek)
                    .addComponent(txtsupplier)
                    .addComponent(txtharga)
                    .addComponent(txttglkadaluarsa)
                    .addComponent(txtdosis)
                    .addComponent(txtbentuk)
                    .addComponent(txtkategori)
                    .addComponent(txtnama)
                    .addComponent(txtidobat))
                .addGap(18, 18, 18)
                .addComponent(btnget)
                .addGap(42, 42, 42))
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(btnsimpan)
                .addGap(18, 18, 18)
                .addComponent(btnupdate)
                .addGap(18, 18, 18)
                .addComponent(btndelete)
                .addGap(18, 18, 18)
                .addComponent(btnclear)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtidobat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnget))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtkategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtbentuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtdosis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txttglkadaluarsa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtefek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtkontraindikasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtinstruksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsimpan)
                    .addComponent(btnupdate)
                    .addComponent(btndelete)
                    .addComponent(btnclear))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void populateKategoriComboBox() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            conn = koneksinya.GetConnection();
            String query = "SELECT id_kategori, nama_kategori FROM kategori";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
    
            while (rs.next()) {
                String idKategori = rs.getString("id_kategori");
                String namaKategori = rs.getString("nama_kategori");
                String displayText = idKategori + " - " + namaKategori;
                txtkategori.addItem(displayText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {
        txtidobat.setText("");
        txtnama.setText("");
        txtbentuk.setText("");
        txtdosis.setText("");
        txttglkadaluarsa.setText("");
        txtharga.setText("");
        txtsupplier.setText("");
        txtefek.setText("");
        txtkontraindikasi.setText("");
        txtinstruksi.setText("");
        txtkategori.setSelectedIndex(0); // Mengatur JComboBox ke pilihan pertama
    }
    

    private void btngetActionPerformed(java.awt.event.ActionEvent evt) {
        Autonomor();
    }

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {
        Simpan();
    }
    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {
        Update();
    }
    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {
        Delete();
    }
    private void tabelobatMouseClicked(java.awt.event.MouseEvent evt) {
        Tabelklik();
    }
    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Obat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Obat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Obat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Obat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Obat().setVisible(true);
            }
        });
    }
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnget;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btnupdate;
    private javax.swing.JButton btnclear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelobat;
    private javax.swing.JTextField txtidobat;
    private javax.swing.JTextField txtnama;
    private javax.swing.JComboBox<String> txtkategori;
    private javax.swing.JTextField txtbentuk;
    private javax.swing.JTextField txtdosis;
    private javax.swing.JTextField txttglkadaluarsa;
    private javax.swing.JTextField txtharga;
    private javax.swing.JTextField txtsupplier;
    private javax.swing.JTextField txtefek;
    private javax.swing.JTextField txtkontraindikasi;
    private javax.swing.JTextField txtinstruksi;
}