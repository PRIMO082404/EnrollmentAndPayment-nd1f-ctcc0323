package com.mycompany.enrollmentandpayment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnrollmentAndPayment {
    private JFrame frame;
    private JTextField studentNameField;
    private JComboBox<String> coursesComboBox;
    private JTextField gradeField;
    private JTextField paymentField;
    private JTextArea resultArea;
    private JButton enrollButton;
    
    private String[] availableCourses = {"Pathfit 01", "Sinesos 02", "Discrete 03", "Purcomm 04", "Com Prog 05", "NPP 06", "NSTP 07", "UTS 08"};
    private int[] courseCredits = {2, 2, 2, 1};
    
    private String[] studentNames = new String[5];
    private int[] selectedCourses = new int[5];
    private double[] studentGrades = new double[5];
    private double[] studentPayments = new double[5];
    private int studentCount = 0;
    private final double MIN_GRADE = 75.0;

    public EnrollmentAndPayment() {
        frame = new JFrame("Enrollment and Payment System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        
        panel.add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        panel.add(studentNameField);
        
        panel.add(new JLabel("Select Course:"));
        coursesComboBox = new JComboBox<>(availableCourses);
        panel.add(coursesComboBox);
        
        panel.add(new JLabel("Grade"));
        gradeField = new JTextField();
        panel.add(gradeField);
        
        panel.add(new JLabel("Payment"));
        paymentField = new JTextField();
        panel.add(paymentField);
        
        enrollButton = new JButton("Enroll");
        panel.add(enrollButton);
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea));
        
        frame.add(panel, BorderLayout.CENTER);
        
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollStudent();
            }
        });
        
        frame.setVisible(true);
    }

    private void enrollStudent() {
      if (studentCount < 5) {
          String studentName = studentNameField.getText().trim();
          int selectedCourseIndex = coursesComboBox.getSelectedIndex();
          double grade;
          double payment;
          
          try {
              grade = Double.parseDouble(gradeField.getText().trim());
          } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(frame,"Please enter a valid grade","Invalid Input", JOptionPane.ERROR_MESSAGE);
              return;
          }
          
          try {
              payment = Double.parseDouble(paymentField.getText().trim());
          } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(frame,"Please enter a valid payment amount","Invalid Input", JOptionPane.ERROR_MESSAGE);
              return;
          }
          
          if (!studentName.isEmpty() && selectedCourseIndex >= 0 && grade >= 0 && grade <= 100) {
              if (grade >= MIN_GRADE) {
                  studentNames[studentCount] = studentName;
                  selectedCourses[studentCount] = selectedCourseIndex;
                  studentGrades[studentCount] = grade;
                  studentPayments[studentCount] = payment;
                  resultArea.append("Enrollment successful for " + studentName + " in " + availableCourses[selectedCourseIndex] + "\n");
                  studentCount++;
                  
                  if (studentCount == 5) {
                      displayEnrolledStudents();
                  }
              } else {
                  JOptionPane.showMessageDialog(frame,"Grade is below the minimum required for enrollment.", "Enrollment Denied", JOptionPane.WARNING_MESSAGE);
              }
          } else {
              JOptionPane.showMessageDialog(frame, "Please enter a valid name, select a course, and enter a valid grade.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
          }
      } else {
          JOptionPane.showMessageDialog(frame, "Maximum number of students enrolled.", "Limit Reached", JOptionPane.INFORMATION_MESSAGE);
      }
      
      studentNameField.setText("");
      gradeField.setText("");
      coursesComboBox.setSelectedIndex(0);
    }
    
   
    private void displayEnrolledStudents() {
        resultArea.append("\nPayment Details:\n");
        for (int i = 0; i < studentCount; i++) {
            int credits = courseCredits[selectedCourses[i]];
            double requiredPayment = credits * 1000;
            double remainingBalance = requiredPayment - studentPayments[i];
            resultArea.append(studentNames[i] + " needs to pay: " + requiredPayment + " units. Paid: " + studentPayments[i] + " units. Remaining balance: " + remainingBalance + " units\n");
        }
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnrollmentAndPayment());
    }
}
