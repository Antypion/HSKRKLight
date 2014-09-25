package pl.hskrk.hskrklight;

/**
 * Created by Krzysiek on 2014-09-05.
 */
public class Light {
    private String id;//zawiera nazwę przełącznika(światłe na które oddziałuje)
    //Contains name of Light bulb that is switched by described toggle
    private boolean on;//Stan logiczny światła(włączone/wyłączone)
    //Tells you if light is on or off
    //Konstruktor/Constructor
    public Light(String id){
        this.id = id;
        on = false;
    }
    public Light(String id,boolean on){
        this.id = id;
        this.on = on;
    }
    //Przęłącznie światła
    //Toggling the light
    public void toggle(){
        if(on){
            //Switch off
            on = false;
        }else{
            //Switch on
            on = true;
        }
        //Tutaj się walnie obługę wyjątków
    }
    public String hasBeenLit(){
        if(on) return("on");
        else return("off");

    }
    public boolean getState(){
        return on;
    }
    public void setState(boolean has_been_lit){
        on = has_been_lit;
    }
    public String getName(){
        return id;
    }

    public String toString(){
        return(id +'('+this.hasBeenLit()+')');
        //return id;
    }
}
