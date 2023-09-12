package controller;

import model.Timekeeper;

import java.sql.SQLException;
public class TimekeeperController {
    // Add a new timekeeper
    public static void addTimekeeper(Timekeeper timekeeper) {
        // Check if the employee ID exists in the Employee table
        if (!isEmployeeIdExists(timekeeper.getEmpId())) {
            System.out.println("Error: Employee with ID " + timekeeper.getEmpId() + " does not exist.");
            return;
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO TIMEKEEPER (Timekeeper_Id, Date_Time, In_Out, EMP_ID) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, timekeeper.getTimekeeper_Id());
            preparedStatement.setTimestamp(2, new Timestamp(timekeeper.getDate_Time().getTime()));
            preparedStatement.setString(3, timekeeper.getIn_Out());
            preparedStatement.setBigDecimal(4, new BigDecimal(timekeeper.getEmpId()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing timekeeper
    public static void updateTimekeeper(Timekeeper timekeeper) {
        // Check if the employee ID exists in the Employee table
        if (!isEmployeeIdExists(timekeeper.getEmpId())) {
            System.out.println("Error: Employee with ID " + timekeeper.getEmpId() + " does not exist.");
            return;
        }

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE TIMEKEEPER SET Date_Time = ?, In_Out = ? WHERE Timekeeper_Id = ?")) {

            preparedStatement.setTimestamp(1, new Timestamp(timekeeper.getDate_Time().getTime()));
            preparedStatement.setString(2, timekeeper.getIn_Out());
            preparedStatement.setString(3, timekeeper.getTimekeeper_Id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a timekeeper by ID
    public static void deleteTimekeeper(String timekeeperId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM TIMEKEEPER WHERE Timekeeper_Id = ?")) {

            preparedStatement.setString(1, timekeeperId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a timekeeper by ID
    public static Timekeeper getTimekeeperById(String timekeeperId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM TIMEKEEPER WHERE Timekeeper_Id = ?")) {

            preparedStatement.setString(1, timekeeperId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Timekeeper timekeeper = new Timekeeper();
                    timekeeper.setTimekeeper_Id(resultSet.getString("Timekeeper_Id"));
                    timekeeper.setDate_Time(new java.util.Date(resultSet.getTimestamp("Date_Time").getTime()));
                    timekeeper.setIn_Out(resultSet.getString("In_Out"));
                    timekeeper.setEmpId(new BigInteger(resultSet.getBigDecimal("EMP_ID").toString()));
                    return timekeeper;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Timekeeper with the specified ID was not found
    }

    // Get a list of all timekeepers
    public static List<Timekeeper> getAllTimekeepers() {
        List<Timekeeper> timekeepers = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM TIMEKEEPER")) {

            while (resultSet.next()) {
                Timekeeper timekeeper = new Timekeeper();
                timekeeper.setTimekeeper_Id(resultSet.getString("Timekeeper_Id"));
                timekeeper.setDate_Time(new java.util.Date(resultSet.getTimestamp("Date_Time").getTime()));
                timekeeper.setIn_Out(resultSet.getString("In_Out"));
                timekeeper.setEmpId(new BigInteger(resultSet.getBigDecimal("EMP_ID").toString()));
                timekeepers.add(timekeeper);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timekeepers;
    }

    // Check if an employee ID exists in the Employee table
    private static boolean isEmployeeIdExists(BigInteger empId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM EMPLOYEE WHERE EMP_ID = ?")) {

            preparedStatement.setBigDecimal(1, new BigDecimal(empId));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
