package yueworld.POJO;

import java.util.ArrayList;
import java.util.Random;

public class Product {

    public String name;
    public Integer id;

    public static Product generateProduct(){
        int i = new Random().nextInt(100);
        ArrayList<String> list = new ArrayList<>();
        list.add("spark");
        list.add("hbase");
        list.add("flink");
        Product product = new Product();
        product.setName(list.get(new Random().nextInt(3)));
        product.setId(i);

        return product;
    }

    public Product() {
    }

    public Product(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
