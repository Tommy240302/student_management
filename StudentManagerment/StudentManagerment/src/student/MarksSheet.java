/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Quang dep trai
 */
public class MarksSheet {
    Connection con = AccountConection.getConnection();
    PreparedStatement ps;
    public boolean isIdExist (int id){
        try {
            ps = con.prepareStatement("select * from score where student_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     public void getScoreValue(JTable table, int sid) {
        String sql = "select * from score where student_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,sid);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[12];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getDouble(12);
             
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet .class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
