import java.io.*;
import java.util.*;

import static java.lang.Double.parseDouble;

public class Program {
    static ArrayList<Beer> list= new ArrayList<Beer>();
    static Map<String, Comparator<Beer>> comps=new HashMap<>();
    static{
        comps.put("name", Comparator.comparing((a) -> a.getName()));
        comps.put("style", Comparator.comparing((a) -> a.getStyle()));
        comps.put("strength", Comparator.comparing((a) -> a.getStrength()));
    }
    public static void main(String[] args){
        HashMap<String, Command> commands1= new HashMap<String, Command>();
        commands1.put("list", Program::list);
        commands1.put("add", Program::add);
        commands1.put("save", Program::save);
        commands1.put("load", Program::load);
        commands1.put("search", Program::search);
        commands1.put("find", Program::find);
        commands1.put("delete", Program::delete);
        //commands1.put("exit", {System.exit(0)});
        /*Beer b1;
        b1 = new Beer("Gösser", "világos", 5.6);
        Beer b2;
        b2 = new Beer("Dreher", "világos", 5.5);
        System.out.println(b1.toString());
        System.out.println(b2.toString());*/
        InputStreamReader is=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(is);
        String line=null;
        while(true){
            try{
                line= br.readLine();
            }
            catch(IOException e){
                System.out.println("Sikertelen beolvasás!");
            }
            String[] commands=line.split(" ");
            if(!commands1.containsKey(commands[0])){
                System.out.println("Ilyen nincs!");
            }
            else{
                commands1.get(commands[0]).execute(commands);
            }
            //System.out.println(commands[0]+" "+commands.length);

            /*if(commands[0].equals("exit")){
                System.exit(0);
            }
            else if(commands[0].equals("add")){
                add(commands);
            }
            else if(commands[0].equals("list")){
                list(commands);
            }
            else if(commands[0].equals("save")){
                save(commands);
            }
            else if(commands[0].equals("load")){
                load(commands);
            }
            else if(commands[0].equals("search")){
                search(commands);
            }
            else if(commands[0].equals("find")){
                find(commands);
            }
            else if(commands[0].equals("delete")){
                delete(commands);

            }*/
        }
    }
    protected static void delete(String[] commands){
        Iterator<Beer> i=list.iterator();
        boolean found=false;
        while(i.hasNext() && !found){
            if(i.next().getName().equals(commands[1])){
                i.remove();
                found=true;
            }
        }
        if(!found){
            System.out.println("Sikertelen törlés");
        }
    }

    protected static void find(String[] commands) {
        for(Beer i : list){
            if(commands[1].equals("name")){
                if(i.getName().contains(commands[2])){
                    System.out.println(i.toString());
                }
            }
            else if(commands[1].equals("style")){
                if(i.getStyle().contains(commands[2])){
                    System.out.println(i.toString());
                }
            }
            else if(commands[1].equals("strength")){
                if(i.getStrength()>=parseDouble(commands[2])){
                    System.out.println(i.toString());
                }
            }
            else if(commands[1].equals("weaker")){
                if(i.getStrength()<=parseDouble(commands[2])){
                    System.out.println(i.toString());
                }
            }

        }
    }

    protected static void search(String[] commands) {
        for(Beer i : list){
            if(commands[1].equals("name")){
                if(i.getName().equals(commands[2])){
                    System.out.println(i.toString());
                }
            }
            else if(commands[1].equals("style")){
                if(i.getStyle().equals(commands[2])){
                    System.out.println(i.toString());
                }
            }
            else if(commands[1].equals("strength")){
                if(i.getStrength()==parseDouble(commands[2])){
                    System.out.println(i.toString());
                }
            }
        }
    }

    protected static void list(String[] commands) {
            if(commands.length>1){
                Comparator<Beer> cmp = comps.get("name");
                if (comps.containsKey(commands[1])) {
                    cmp = comps.get(commands[1]);
                }
                list.sort(cmp);
            }
        /*try{
            if(commands[1].equals("name")){

                Collections.sort(list, (b0, b1) -> b0.getName().compareTo(b1.getName()));
            }
            else if(commands[1].equals("style")){
                Collections.sort(list, (b0, b1) -> b0.getStyle().compareTo(b1.getStyle()));
            }
            else if(commands[1].equals("strength")){
                Collections.sort(list, (b0, b1) -> {return (int) (b0.getStrength()-b1.getStrength());});
            }
        }
        catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }*/
        Iterator<Beer> i=list.iterator();
        while(i.hasNext()){
            System.out.println(i.next().toString());
        }
    }

    protected static void add(String[] commands) {
        try{
            double percent=parseDouble(commands[3]);
            Beer b1=new Beer(commands[1], commands[2], percent);
            list.add(b1);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Sikertelen vegrehajtas");
        }

    }
    protected static void save(String[] commands){
        try{
            FileOutputStream f=new FileOutputStream(commands[1]);
            ObjectOutputStream out=new ObjectOutputStream(f);
            out.writeObject(list);
            out.close();
        }
        catch(IOException e){
            System.out.println("Nem sikerült menteni");
        }

    }
    protected static void load(String[] commands){
        try{
            FileInputStream f=new FileInputStream(commands[1]);
            ObjectInputStream in=new ObjectInputStream(f);
            list=(ArrayList<Beer>)in.readObject();
            in.close();
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println("Nem sikerült betölteni");
        }
    }
}