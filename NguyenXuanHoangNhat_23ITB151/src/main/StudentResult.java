package main;

class StudentResult {
    private Student student;
    private int age;
    private int sum;
    private boolean isPrime;

    public StudentResult(Student student, int age) {
        this.student = student;
        this.age = age;
    }

    public Student getStudent() {
        return student;
    }

    public int getAge() {
        return age;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public boolean isPrime() {
        return isPrime;
    }

    public void setPrime(boolean prime) {
        isPrime = prime;
    }
}
