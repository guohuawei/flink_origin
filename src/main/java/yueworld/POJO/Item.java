package yueworld.POJO;

import java.util.Random;

public class Item {
    public String name;
    public Integer id;

    public static Item generateItem(){
        int i = new Random().nextInt(100);
        Item item = new Item();
        item.setName("name" + i);
        item.setId(i);
        return item;
    }


    public Item() {
    }

    public Item(String name, Integer id) {
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
        return "Item{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
