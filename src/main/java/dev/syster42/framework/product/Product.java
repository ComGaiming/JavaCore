package dev.syster42.framework.product;

public class Product {

    String name;
    String description;
    float price;
    String ean;

    public Product(){}

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String description, float price, String ean) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void createProduct(){

    }

    public void createProduct(String name) {
        this.name = name;
    }

    public void createProduct(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void createProduct(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public void createProduct(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void createProduct(String name, String description, float price, String ean) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ean = ean;
    }

}
