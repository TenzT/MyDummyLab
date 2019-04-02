package spring.ioc;

public class Car {
    private String brand;
    private String corp;
    private int price;
    private int maxSpeed;

    public Car(String brand, String corp, int price, int maxSpeed) {
        this.brand = brand;
        this.corp = corp;
        this.price = price;
        this.maxSpeed = maxSpeed;
    }

    public Car(String brand, String corp, int price) {
        this.brand = brand;
        this.corp = corp;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", corp='" + corp + '\'' +
                ", price=" + price +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}
