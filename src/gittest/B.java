package gittest;

import java.util.TreeMap;

public class B {
    @Override
    public String toString(){
        return "This is B";
    }

    public static void main(String[] args) {
        TreeMap map=new TreeMap();
        map.put(1,"aas");
        map.put(5,"sdf");
        map.put(2,"dsfsdf");
        System.out.println(map.lastKey());
    }
}
