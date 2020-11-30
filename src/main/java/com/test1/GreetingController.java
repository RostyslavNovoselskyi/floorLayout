package com.test1;

import com.test1.domain.Room;
import com.test1.repos.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {
    List<Integer> list1 = new LinkedList<>();
    List<Integer> list2 = new LinkedList<>();

    @Autowired
    private RoomRepo roomRepo;

    @GetMapping("/greeting")
    public String greeting(Map<String, Object> model) {
        Iterable<Room> rooms = roomRepo.findAll();
        model.put("rooms", rooms);
        return "greeting";
    }

    @GetMapping("/good")
    public String good(Map<String, Object> model) {
        Iterable<Room> rooms = roomRepo.findAll();
        model.put("rooms", rooms);
        return "good";
    }

    @GetMapping
    public String main(Map<String, Object> model){
        Iterable<Room> rooms = roomRepo.findAll();

        model.put("rooms", rooms);

        return "main";
    }


    @PostMapping
    public String add(@RequestParam String text, Map<String, Object> model){
        char[] rep = text.toCharArray();
        int a, b;
        String qwe = "";
        String qwe1 = "";
        String s = "";
        for (int i = 0; i < rep.length; i++) {
            if (rep[i] == ','){
                for (int j = 0; j < i; j++) {
                    qwe += rep[j];
                }
                for (int j = i+1; j <rep.length ; j++) {
                   qwe1 += rep[j];
                }
            }
            s += rep[i];
        }

        try {
            a = Integer.valueOf(qwe);
            b = Integer.valueOf(qwe1);
            list1.add(a);
            list2.add(b);
        }catch (NumberFormatException e){
            System.out.println(e);
        }

//        rep.add(text);


        Room room1 = new Room(s);

        roomRepo.save(room1);


        Iterable<Room> rooms = roomRepo.findAll();
        model.put("rooms", rooms);

        return "main";
    }

    @PostMapping("verify")
    public String verify(Map<String, Object> model){
        if(list1.size() < list2.size() || list1.size() > list2.size()){
            return "redirect:greeting";
        }

        int a = 0;

        for (int i = 0; i < list1.size(); i++) {
            if (i > 0 && list1.size() >=4){
                if (!list1.get(i-1).equals(list1.get(i)) && list2.get(i-1).equals(list2.get(i))){
                    a++;
                }
                else if (list1.get(i-1).equals(list1.get(i)) && !list2.get(i-1).equals(list2.get(i))){
                    a++;
                }
            }

        }

        if (a == list1.size()-1) {
            return "redirect:good";
        }else {
            return "redirect:greeting";
        }

    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Room> rooms;

        if (filter != null && !filter.isEmpty()) {
            rooms = roomRepo.findByText(filter);
        } else{
            rooms = roomRepo.findAll();
        }

        model.put("rooms", rooms);
        return "main";
    }

    @PostMapping("deleting")
    public String deleting(Map<String, Object> model){
        Iterable<Room> rooms;
        roomRepo.deleteAll();
        list1.clear();
        list2.clear();

        return "redirect:";
    }

    @PostMapping("backtomain")
    public String back(){
        return "redirect:";
    }

}
