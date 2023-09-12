package controller.dao;
import model.Timekeeper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DAOTimekeeper extends DAODepartment {

    public DAOTimekeeper(Connection conn) {
        super(conn);
    }

    @Override
    public Timekeeper[] selectAll() {
        List<Timekeeper> timekeepers = new ArrayList<>();
        Timekeeper[] timekeeperArray;
        String query = "SELECT * FROM TIMEKEEPER";
        try {
            this.rs = this.statement.executeQuery(query);
            while (rs.next()) {
                Timekeeper timekeeper = new Timekeeper();
                timekeeper.setTimekeeperId(rs.getInt("TIMEKEEPER_ID"));
                timekeeper.setDateTime(rs.getTimestamp("DATE_TIME"));
                timekeeper.setInOut(rs.getString("IN_OUT"));
                timekeeper.setEmpId(rs.getInt("EMP_ID"));
                timekeepers.add(timekeeper);
            }
            timekeeperArray = new Timekeeper[timekeepers.size()];
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return timekeepers.toArray(timekeeperArray);
    }

    @Override
    public Timekeeper[] selectByName(String name) {
        List<Timekeeper> timekeepers = new ArrayList<>();
        String query = "SELECT * FROM TIMEKEEPER WHERE EMP_ID LIKE ?";
        Timekeeper[] timekeeperArray;
        try {
            this.preStatement = conn.prepareStatement(query);
            preStatement.setString(1, "%" + name + "%");
            rs = preStatement.executeQuery();
            while (rs.next()) {
                Timekeeper timekeeper = new Timekeeper();
                timekeeper.setTimekeeperId(rs.getInt("TIMEKEEPER_ID"));
                timekeeper.setDateTime(rs.getTimestamp("DATE_TIME"));
                timekeeper.setInOut(rs.getString("IN_OUT"));
                timekeeper.setEmpId(rs.getInt("EMP_ID"));
                timekeepers.add(timekeeper);
            }
            timekeeperArray = new Timekeeper[timekeepers.size()];
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return timekeepers.toArray(timekeeperArray);
    }

    @Override
    public int insert(Timekeeper timekeeper) {
        String query = "INSERT INTO TIMEKEEPER (DATE_TIME, IN_OUT, EMP_ID) VALUES (?, ?, ?)";
        try {
            preStatement = conn.prepareStatement(query);
            preStatement.setTimestamp(1, timekeeper.getDateTime());
            preStatement.setString(2, timekeeper.getInOut());
            preStatement.setInt(3, timekeeper.getEmpId());
            int rowsInserted = preStatement.executeUpdate();
            return rowsInserted;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Timekeeper timekeeper) {
        String query = "UPDATE TIMEKEEPER SET DATE_TIME=?, IN_OUT=?, EMP_ID=? WHERE TIMEKEEPER_ID=?";
        try {
            preStatement = conn.prepareStatement(query);
            preStatement.setTimestamp(1, timekeeper.getDateTime());
            preStatement.setString(2, timekeeper.getInOut());
            preStatement.setInt(3, timekeeper.getEmpId());
            preStatement.setInt(4, timekeeper.getTimekeeperId());
            int rowsUpdated = preStatement.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
