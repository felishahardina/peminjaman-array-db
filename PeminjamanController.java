/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Buku;
import model.BukuDao;
import model.Mahasiswa;
import model.MahasiswaDao;
import model.Peminjaman;
import model.PeminjamanDao;
import view.FormPeminjaman;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LABSIDOSEN
 */
public class PeminjamanController {
    FormPeminjaman viewPeminjaman;
    Peminjaman peminjaman;
    MahasiswaDao mahasiswaDao;
    BukuDao bukuDao;
    PeminjamanDao peminjamanDao;

    public PeminjamanController(FormPeminjaman viewPeminjaman) {
        this.viewPeminjaman = viewPeminjaman;
        mahasiswaDao = new MahasiswaDao();
        bukuDao = new BukuDao();
        peminjamanDao = new PeminjamanDao();
    }
    
    public void clearForm(){
        viewPeminjaman.getTxtTglpinjam().setText("");
        viewPeminjaman.getTxtTglkembali().setText("");
    }
    
    public void isiCboMahasiswa(){
        List<Mahasiswa> list = mahasiswaDao.getAllMahasiswa();
        viewPeminjaman.getCboMahasiswa().removeAllItems();
        for (Mahasiswa mahasiswa : list) {
            viewPeminjaman.getCboMahasiswa()
                    .addItem(mahasiswa.getNobp()+"-"+mahasiswa.getNama());
        }
    }
    
    public void isiCboBuku(){
        List<Buku> list = bukuDao.getAllBuku();
        viewPeminjaman.getCboBuku().removeAllItems();
        for (Buku buku : list) {
            viewPeminjaman.getCboBuku()
                    .addItem(buku.getKodeBuku()+"-"+buku.getJudul());
        }
    }
    
    public void insert(){
        peminjaman = new Peminjaman();
        Mahasiswa mahasiswa = mahasiswaDao.getMahasiswa(
                viewPeminjaman.getCboMahasiswa().getSelectedIndex());
        Buku buku = bukuDao.getBuku(
                viewPeminjaman.getCboBuku().getSelectedIndex());
        peminjaman.setMahasiswa(mahasiswa);
        peminjaman.setBuku(buku);
        peminjaman.setTglpinjam(viewPeminjaman.getTxtTglpinjam().getText());
        peminjaman.setTglkembali(viewPeminjaman.getTxtTglkembali().getText());
        peminjamanDao.insert(peminjaman);
        JOptionPane.showMessageDialog(viewPeminjaman, "Entri Data Ok");
    }
    
    public void update(){
        int index = viewPeminjaman.getTabelPeminjaman().getSelectedRow();
        peminjaman = new Peminjaman();
        Mahasiswa mahasiswa = mahasiswaDao.getMahasiswa(
                viewPeminjaman.getCboMahasiswa().getSelectedIndex());
        Buku buku = bukuDao.getBuku(
                viewPeminjaman.getCboBuku().getSelectedIndex());
        peminjaman.setMahasiswa(mahasiswa);
        peminjaman.setBuku(buku);
        peminjaman.setTglpinjam(viewPeminjaman.getTxtTglpinjam().getText());
        peminjaman.setTglkembali(viewPeminjaman.getTxtTglkembali().getText());
        peminjamanDao.update(index, peminjaman);
        JOptionPane.showMessageDialog(viewPeminjaman, "Update Data Ok");
    }
    
    public void delete(){
        int index = viewPeminjaman.getTabelPeminjaman().getSelectedRow();
        peminjamanDao.delete(index);
        JOptionPane.showMessageDialog(viewPeminjaman, "Delete Data Ok");
    }
    
    
    public void viewData(){
        DefaultTableModel model = 
                (DefaultTableModel)viewPeminjaman.getTabelPeminjaman().getModel();
        model.setNumRows(0);
        List<Peminjaman> list = peminjamanDao.getAllPeminjaman();
        for(Peminjaman peminjaman : list){
            Object[] data = {
                peminjaman.getMahasiswa().getNobp(),
                peminjaman.getBuku().getKodeBuku(),
                peminjaman.getTglpinjam(),
                peminjaman.getTglkembali()
                     
            };
            model.addRow(data);
        }
    }
    
    public void getPeminjaman(){
        int index = viewPeminjaman.getTabelPeminjaman().getSelectedRow();
        peminjaman = peminjamanDao.getPeminjaman(index);
        if(peminjaman != null) {
            viewPeminjaman.getCboMahasiswa().setSelectedItem(
                    peminjaman.getMahasiswa().getNobp()+"-"+peminjaman.getMahasiswa().getNama());
            viewPeminjaman.getCboBuku().setSelectedItem(
                    peminjaman.getBuku().getKodeBuku()+"-"+peminjaman.getBuku().getJudul());
            viewPeminjaman.getTxtTglpinjam().setText(peminjaman.getTglpinjam());
            viewPeminjaman.getTxtTglkembali().setText(peminjaman.getTglkembali());
        }
    }  
}
