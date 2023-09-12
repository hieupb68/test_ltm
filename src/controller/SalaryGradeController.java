package controller;

import model.SalaryGrade;
import controller.utils.ConnectionUtils;
import controller.dao.DAOSalaryGrade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryGradeController {


    // Add a new salary grade
    public static void addSalaryGrade(SalaryGrade salaryGrade) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO SALARY_GRADE (GRADE, HIGH_SALARY, LOW_SALARY) VALUES (?, ?, ?)")) {

            preparedStatement.setInt(1, salaryGrade.getGrade());
            preparedStatement.setFloat(2, salaryGrade.getHighSalary());
            preparedStatement.setFloat(3, salaryGrade.getLowSalary());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing salary grade
    public static void updateSalaryGrade(SalaryGrade salaryGrade) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE SALARY_GRADE SET HIGH_SALARY = ?, LOW_SALARY = ? WHERE GRADE = ?")) {

            preparedStatement.setFloat(1, salaryGrade.getHighSalary());
            preparedStatement.setFloat(2, salaryGrade.getLowSalary());
            preparedStatement.setInt(3, salaryGrade.getGrade());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a salary grade by grade
    public static void deleteSalaryGrade(int grade) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM SALARY_GRADE WHERE GRADE = ?")) {

            preparedStatement.setInt(1, grade);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a salary grade by grade
    public static SalaryGrade getSalaryGradeByGrade(int grade) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM SALARY_GRADE WHERE GRADE = ?")) {

            preparedStatement.setInt(1, grade);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    SalaryGrade salaryGrade = new SalaryGrade();
                    salaryGrade.setGrade(resultSet.getInt("GRADE"));
                    salaryGrade.setHighSalary(resultSet.getFloat("HIGH_SALARY"));
                    salaryGrade.setLowSalary(resultSet.getFloat("LOW_SALARY"));
                    return salaryGrade;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Salary grade with the specified grade was not found
    }

    // Get a list of all salary grades
    public static List<SalaryGrade> getAllSalaryGrades() {
        List<SalaryGrade> salaryGrades = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM SALARY_GRADE")) {

            while (resultSet.next()) {
                SalaryGrade salaryGrade = new SalaryGrade();
                salaryGrade.setGrade(resultSet.getInt("GRADE"));
                salaryGrade.setHighSalary(resultSet.getFloat("HIGH_SALARY"));
                salaryGrade.setLowSalary(resultSet.getFloat("LOW_SALARY"));
                salaryGrades.add(salaryGrade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salaryGrades;
    }
}

