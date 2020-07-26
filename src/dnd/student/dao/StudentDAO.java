package dnd.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dnd.student.core.Student;

public class StudentDAO { // lớp Data Access Object (DAO): chuyên dùng để thao tác với database
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	public Connection getConnection() {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/demo";
			String user = "root";
			String password = "123456789";
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public boolean addStudent(Student student) {
		String sql = "INSERT INTO student(id,name,email) VALUES (?,?,?)";
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, student.getId());
			statement.setString(2, student.getName());
			statement.setString(3, student.getEmail());
			// statement.executeUpdate();
			// return true;
			return statement.executeUpdate() > 0;
			// dòng bên trên nghĩa là khi câu lệnh executeUpdate thực thi thành công thì sẽ
			// return về (1 > 0) <=> return về true
			// Còn khi executeUpdate lỗi thì sẽ chạy xuống catch và return false
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Xay ra loi trong qua trinh them sinh vien: " + e.getMessage());
			return false;

		} finally {
			closeConnection();
		}
	}

	public boolean updateStudent(Student student) {
		String sql = "UPDATE student SET name = ?, email = ? WHERE id = ?";
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setString(1, student.getName());
			statement.setString(2, student.getEmail());
			statement.setLong(3, student.getId());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Xay ra loi trong qua trinh cap nhat thong tin sinh vien: " + e.getMessage());
			return false;
		} finally {
			closeConnection();
		}
	}

	public boolean deleteStudentById(Student student) {
		String sql = "DELETE FROM student WHERE id = ?";
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setLong(1, student.getId());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Xay ra loi trong qua trinh xoa sinh vien: " + e.getMessage());
		} finally {
			closeConnection();
		}
		return false;
	}

	public ArrayList<Student> getAll() {
		ArrayList<Student> studentList = new ArrayList<Student>();
		String sql = "SELECT * FROM student";
		try {
			statement = getConnection().prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Student student = new Student();
				student.setId(resultSet.getLong("id"));
				student.setName(resultSet.getString("name"));
				student.setEmail(resultSet.getString("email"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Xay ra loi trong qua trinh lay du lieu danh sach cac sinh vien: " + e.getMessage());
		} finally {
			closeConnection();
		}
		return studentList;
	}

	public ArrayList<Student> findStudentByName(String name) {
		ArrayList<Student> studentList = new ArrayList<Student>();
		String sql = "SELECT * FROM student WHERE name LIKE ?";
		try {
			statement = getConnection().prepareStatement(sql);
			statement.setNString(1, "%" + name + "%");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Student student = new Student();
				student.setId(resultSet.getLong("id"));
				student.setName(resultSet.getString("name"));
				student.setEmail(resultSet.getString("email"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Xay ra loi trong qua trinh tim kiem: " + e.getMessage());
		} finally {
			closeConnection();
		}
		return studentList;
	}
}
