package main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Student> studentQueue = new LinkedBlockingQueue<>();
        BlockingQueue<StudentResult> resultQueue = new LinkedBlockingQueue<>();

       
        Thread thread1 = new Thread(() -> readStudents("F:\\Eclipse\\NguyenXuanHoangNhat_23ITB151\\src\\main\\student.xml", studentQueue));
        thread1.start();

       
        Thread thread2 = new Thread(() -> {
            try {
                while (true) {
                    Student student = studentQueue.take();
                    StudentResult result = calculateAgeAndEncode(student);
                    resultQueue.put(result);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread2.start();

    
        Thread thread3 = new Thread(() -> {
            try {
                while (true) {
                    StudentResult result = resultQueue.take();
                    checkPrime(result);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        thread3.start();
    }

    private static void readStudents(String fileName, BlockingQueue<Student> studentQueue) {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("Student");

            // Process each student
            for (int i = 0; i < studentList.getLength(); i++) {
                Element studentNode = (Element) studentList.item(i);
                String id = studentNode.getAttribute("id");
                String name = studentNode.getElementsByTagName("name").item(0).getTextContent();
                String address = studentNode.getElementsByTagName("address").item(0).getTextContent();
                String dob = studentNode.getElementsByTagName("dateOfBirth").item(0).getTextContent();

                LocalDate dateOfBirth = LocalDate.parse(dob);
                Student student = new Student(id, name, address, dateOfBirth);
                studentQueue.put(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StudentResult calculateAgeAndEncode(Student student) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(student.getDateofBirth(), currentDate);


        return new StudentResult(student, age.getYears() + age.getMonths() / 12 + age.getDays() / 365);
    }

    private static void checkPrime(StudentResult result) {
        int sumOfDigits = sumOfDigits(result.getStudent().getDateofBirth());
        boolean isPrime = isPrime(sumOfDigits);

        result.setSum(sumOfDigits);
        result.setPrime(isPrime);

        writeResultToXML("F:\\Eclipse\\NguyenXuanHoangNhat_23ITB151\\src\\main\\kq.xml", result);
    }

    private static int sumOfDigits(LocalDate dateOfBirth) {
        int sum = 0;
        int year = dateOfBirth.getYear();
        int month = dateOfBirth.getMonthValue();
        int day = dateOfBirth.getDayOfMonth();

        while (year > 0) {
            sum += year % 10;
            year /= 10;
        }

        while (month > 0) {
            sum += month % 10;
            month /= 10;
        }

        while (day > 0) {
            sum += day % 10;
            day /= 10;
        }

        return sum;
    }

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static void writeResultToXML(String fileName, StudentResult result) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Students");
            doc.appendChild(rootElement);

            Element studentElement = doc.createElement("Student");
            studentElement.setAttribute("id", result.getStudent().getId());
            studentElement.setAttribute("age", String.valueOf(result.getAge()));
            studentElement.setAttribute("sum", String.valueOf(result.getSum()));
            studentElement.setAttribute("isPrime", String.valueOf(result.isPrime()));

            rootElement.appendChild(studentElement);

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(fileName));
            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
    private static void readResultFromXML(String fileName) {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList studentList = doc.getElementsByTagName("Student");

            // Process each student result
            for (int i = 0; i < studentList.getLength(); i++) {
                Element studentNode = (Element) studentList.item(i);
                String id = studentNode.getAttribute("id");
                int age = Integer.parseInt(studentNode.getAttribute("age"));
                int sum = Integer.parseInt(studentNode.getAttribute("sum"));
                boolean isPrime = Boolean.parseBoolean(studentNode.getAttribute("isPrime"));

                // Decode any encoded data if necessary
                // For example, you can decode the age here

                System.out.println("Student ID: " + id);
                System.out.println("Age: " + age);
                System.out.println("Sum of digits: " + sum);
                System.out.println("Is Prime: " + isPrime);
                System.out.println("--------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}