/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BukuDb;
import dao.MahasiswaDb;
import dao.PeminjamanDb;
import model.Buku;
import model.Mahasiswa;
import model.Peminjaman;
import view.FormPeminjamanDb;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LABSIDOSEN
 */
public class PeminjamanControllerDb {
    FormPeminjamanDb viewPeminjamanDb;
    Peminjaman peminjaman;
    MahasiswaDb mahasiswaDb;
    BukuDb bukuDb;
    PeminjamanDb peminjamanDb;

    public PeminjamanControllerDb(FormPeminjamanDb viewPeminjamanDb) {
        this.viewPeminjamanDb = viewPeminjamanDb;
        mahasiswaDb = new MahasiswaDb();
        bukuDb = new BukuDb();
        peminjamanDb = new PeminjamanDb();
    }
    
    public void clearForm(){
        viewPeminjamanDb.getTxtTglpinjam().setText("");
        viewPeminjamanDb.getTxtTglkembali().setText("");
    }
    
    public void isiCboMahasiswa(){
        
        try {
            List<Mahasiswa> list = mahasiswaDb.getAllMahasiswa();
            viewPeminjamanDb.getCboMahasiswa().removeAllItems();
            for (Mahasiswa mahasiswa : list) {
                viewPeminjamanDb.getCboMahasiswa()
                        .addItem(mahasiswa.getNobp()+ "-" + mahasiswa.getNama());
            }
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void isiCboBuku(){
        try {
            List<Buku> list = bukuDb.getAllBuku();
            viewPeminjamanDb.getCboBuku().removeAllItems();
            for (Buku buku : list) {
                viewPeminjamanDb.getCboBuku()
                        .addItem(buku.getKodeBuku()+"-"+buku.getJudul());
            }
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insert(){
        try {
            peminjaman = new Peminjaman();
            Mahasiswa mahasiswa = mahasiswaDb.getMahasiswa(
                    viewPeminjamanDb.getCboMahasiswa().getSelectedItem().toString().split("-")[0]);
            Buku buku = bukuDb.getBuku(
                    viewPeminjamanDb.getCboBuku().getSelectedItem().toString().split("-") [0]);
            peminjaman.setMahasiswa(mahasiswa);
            peminjaman.setBuku(buku);
            peminjaman.setTglpinjam(viewPeminjamanDb.getTxtTglpinjam().getText());
            peminjaman.setTglkembali(viewPeminjamanDb.getTxtTglkembali().getText());
            peminjamanDb.insert(mahasiswa, buku, peminjaman);
            JOptionPane.showMessageDialog(viewPeminjamanDb, "Entri Data Ok");
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update(){
        try {
            Mahasiswa mahasiswa = mahasiswaDb.getMahasiswa(
                    viewPeminjamanDb.getCboMahasiswa().getSelectedItem().toString().split("-")[0]);
            Buku buku = bukuDb.getBuku(
                    viewPeminjamanDb.getCboBuku().getSelectedItem().toString().split("-") [0]);
            peminjaman.setMahasiswa(mahasiswa);
            peminjaman.setBuku(buku);
            peminjaman.setTglpinjam(viewPeminjamanDb.getTxtTglpinjam().getText());
            peminjaman.setTglkembali(viewPeminjamanDb.getTxtTglkembali().getText());
            peminjamanDb.update(peminjaman);
            JOptionPane.showMessageDialog(viewPeminjamanDb, "update Data Ok");
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(){
        try {
            String nobp = viewPeminjamanDb.getTabelPeminjaman().getValueAt(
                    viewPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 0).toString();
            String kodebuku = viewPeminjamanDb.getTabelPeminjaman().getValueAt(
                    viewPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 1).toString();
            peminjamanDb.delete(nobp, kodebuku);
            JOptionPane.showMessageDialog(viewPeminjamanDb, "Delete Data Ok");
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void viewData(){
        try {
            DefaultTableModel model =
                    (DefaultTableModel)viewPeminjamanDb.getTabelPeminjaman().getModel();
            model.setNumRows(0);
            List<Peminjaman> list = peminjamanDb.getAllPeminjaman();
            for(Peminjaman peminjaman : list){
                Object[] data = {
                    peminjaman.getMahasiswa().getNobp(),
                    peminjaman.getBuku().getKodeBuku(),
                    peminjaman.getTglpinjam(),
                    peminjaman.getTglkembali()
                        
                };
                model.addRow(data);
            }
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void actionClickedTabel(){
        try {
            String nobp = viewPeminjamanDb.getTabelPeminjaman().getValueAt(viewPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 0).toString();
            String kodebuku = viewPeminjamanDb.getTabelPeminjaman().getValueAt(viewPeminjamanDb.getTabelPeminjaman().getSelectedRow(), 1).toString();
            peminjaman = peminjamanDb.getPeminjaman(nobp, kodebuku);
            viewPeminjamanDb.getCboMahasiswa().setSelectedItem(peminjaman.getMahasiswa().getNobp()+"-"+ peminjaman.getMahasiswa().getNama());
            viewPeminjamanDb.getCboBuku().setSelectedItem(
                    peminjaman.getBuku().getKodeBuku() + "-" + peminjaman.getBuku().getJudul());
            viewPeminjamanDb.getTxtTglpinjam().setText(peminjaman.getTglpinjam());
            viewPeminjamanDb.getTxtTglkembali().setText(peminjaman.getTglkembali());
        } catch (Exception ex) {
            Logger.getLogger(PeminjamanControllerDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
