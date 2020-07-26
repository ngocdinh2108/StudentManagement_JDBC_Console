package dnd.student.app;

import java.util.ArrayList;
import java.util.Scanner;

import dnd.student.core.Student;
import dnd.student.dao.StudentDAO;

public class App {

	private static Scanner scanner = new Scanner(System.in);
	private static StudentDAO studentDAO = new StudentDAO();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean loop = true;
		while (loop) {
			printMenu();
			System.out.print("Nhap vao lua chon cua ban: ");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				insertStudent();
				break;
			case 2:
				modifyStudent();
				break;
			case 3:
				removeStudentById();
				break;
			case 4:
				printStudentList();
				break;
			case 5:
				findStudent();
				break;
			case 6:
				loop = false;
				System.out.println("Ket thuc chuong trinh!");
				break;
			default:
				System.out.println("Lua chon khong hop le! Xin hay chon lai!");
				break;
			}
		}
	}

	private static void findStudent() {
		// TODO Auto-generated method stub
		scanner.nextLine(); // Xóa bộ nhớ đệm
		ArrayList<Student> studentList = new ArrayList<Student>();
		System.out.print("Nhap vao ten sinh vien can tim (nhap gan dung): ");
		String nameSearch = scanner.nextLine();
		studentList = studentDAO.findStudentByName(nameSearch);
		System.out.println("===>>> Ket qua <<<===");
		for (Student student : studentList) {
			System.out.println(student);
		}
	}

	private static void printStudentList() {
		// TODO Auto-generated method stub
		int row = 0;
		ArrayList<Student> studentList = new ArrayList<Student>();
		studentList = studentDAO.getAll();
		System.out.println("*** Danh sach sinh vien hien co la: ***");
		for (Student student : studentList) {
			System.out.println(student);
			row++;
		}
		System.out.println("====>>> Tong cong co " + row + " ban ghi. <<<====");
	}

	private static void removeStudentById() {
		// TODO Auto-generated method stub
		int count = 0;
		ArrayList<Student> studentList = new ArrayList<Student>();
		studentList = studentDAO.getAll();
		System.out.print("Nhap vao ma sinh vien can xoa: ");
		long idNeedRemove = scanner.nextLong();
		for (Student student : studentList) {
			if (idNeedRemove == student.getId()) {
				count++;
				boolean flag = studentDAO.deleteStudentById(student);
				if (flag) {
					System.out.println("Xoa sinh vien thanh cong!!!");
				} else {
					System.out.println("Xoa that bai!");
				}
			}
		}
		if (count == 0) {
			System.out.println("Khong tim thay ma sinh vien trong danh sach.");
		}
	}

	private static void modifyStudent() {
		// TODO Auto-generated method stub
		int count = 0;
		ArrayList<Student> studentList = new ArrayList<Student>();
		studentList = studentDAO.getAll();
		System.out.print("Nhap vao ma sinh vien can sua: ");
		long idOfStudentNeedUpdate = scanner.nextLong();
		scanner.nextLine(); // Xóa bộ nhớ đệm
		for (Student student : studentList) {
			if (idOfStudentNeedUpdate == student.getId()) {
				count++;
				System.out.print("Nhap moi ten sinh vien: ");
				student.setName(scanner.nextLine());
				System.out.print("Nhap moi email sinh vien: ");
				student.setEmail(scanner.nextLine());
				boolean flag = studentDAO.updateStudent(student);
				if (flag) {
					System.out.println("Cap nhat thong tin sinh vien thanh cong!!!");
				} else {
					System.out.println("Cap nhat that bai!");
				}
			}
		}
		if (count == 0) {
			System.out.println("Khong tim thay ma sinh vien trong danh sach.");
		}
	}

	private static void insertStudent() {
		// TODO Auto-generated method stub
		Student student = new Student();
		System.out.print("Nhap ma sinh vien: ");
		scanner.nextLine(); // xóa bộ nhớ đệm
		student.setId(scanner.nextLong());
		System.out.print("Nhap ten sinh vien: ");
		scanner.nextLine(); // xóa bộ nhớ đệm
		student.setName(scanner.nextLine());
		System.out.print("Nhap email sinh vien: ");
		student.setEmail(scanner.nextLine());
		boolean flag = studentDAO.addStudent(student);
		if (flag) {
			System.out.println("Them moi sinh vien thanh cong!");
		} else {
			System.out.println("Them moi that bai!");
		}
	}

	public static void printMenu() {
		System.out.println("=========== Chuong trinh quan ly sinh vien: ========");
		System.out.println("1) Them sinh vien.");
		System.out.println("2) Sua thong tin theo ma sinh vien.");
		System.out.println("3) Xoa theo ma sinh vien.");
		System.out.println("4) In ra danh sach sinh vien hien co");
		System.out.println("5) Tim kiem sinh vien theo ten gan dung");
		System.out.println("6) Thoat chuong trinh");
		System.out.println("===================================================");
	}

}
