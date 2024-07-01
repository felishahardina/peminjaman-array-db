/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LABSIDOSEN
 */
public class PeminjamanDao {
    private List<Peminjaman> data = 
            new ArrayList<Peminjaman>();

    public PeminjamanDao() {
        MahasiswaDao mahasiswaDao = new MahasiswaDao();
        BukuDao bukuDao = new BukuDao();
        data.add(new Peminjaman(mahasiswaDao.getMahasiswa(0), bukuDao.getBuku(0), 
                "2022-01-01", "2022-01-03"));
    }
    
    public void insert(Peminjaman peminjaman){
        data.add(peminjaman);
    }
    
    public void update(int index,Peminjaman m){
        data.set(index,m);
    }
    
    public void delete(int index){
        data.remove(index);
    }
    
    public Peminjaman getPeminjaman(int index){
        return data.get(index);
    }
    
    public List<Peminjaman> getAllPeminjaman(){
        return data;
    }
}
