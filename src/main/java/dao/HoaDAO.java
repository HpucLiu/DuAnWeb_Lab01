/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Hoa;

/**
 *
 * @author Administrator
 */
public class HoaDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    //Phương thức lấy hoa theo gia 
    public ArrayList<Hoa> getTop10() {
        ArrayList<Hoa> ds = new ArrayList<>();
        String sql = "select top 10 * from Hoa order by gia desc";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return ds;
    }

    //Phương thức đọc hoa theo thể loại
    public ArrayList<Hoa> getByCategoryId(int maloai) {
        ArrayList<Hoa> ds = new ArrayList<>();
        String sql = "select * from Hoa where maloai=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maloai);
            rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return ds;
    }

    //Phương thức lấy tất cả sp
    public ArrayList<Hoa> getAll() {
        ArrayList<Hoa> ds = new ArrayList<>();
        String sql = "select * from Hoa";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ds.add(new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6)));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return ds;
    }

    //Phương thức cập nhật sp
    public boolean Update(Hoa hoa) {
        String sql = "Update hoa set tenhoa=?, gia=?, hinh=?, maloai=?, ngaycapnhat=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, hoa.getTenhoa());
            ps.setDouble(2, hoa.getGia());
            ps.setString(3, hoa.getHinh());
            ps.setInt(4, hoa.getMaloai());
            ps.setDate(5, hoa.getNgaycapnhat());
            ps.setInt(6, hoa.getMaloai());
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return false;
    }

    //Phương thức xoá sp
    public boolean Delete(int mahoa) {
        String sql = "Delete from hoa where mahoa=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, mahoa);
            int kq = ps.executeUpdate();
            if (kq > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return false;
    }

    //Phương thức lấy thông tin sp theo mahoa
    public Hoa getByID(int mahoa) {
        Hoa kq = null;
        String sql = "select * from hoa where mahoa=?";
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, mahoa);
            rs = ps.executeQuery();
            if (rs.next()) {
                kq = new Hoa(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5), rs.getDate(6));
            }
        } catch (Exception ex) {
            System.out.println("Loi:" + ex.toString());
        }
        return kq;
    }

    public static void main(String[] args) {
        HoaDAO hoaDao = new HoaDAO();
        ArrayList<Hoa> dsHoa = hoaDao.getAll();
        for (Hoa hoa : dsHoa) {
            System.out.println(hoa);
        }

        dsHoa = hoaDao.getByCategoryId(2);
        for (Hoa hoa : dsHoa) {
            System.out.println(hoa);
        }
    }
}
