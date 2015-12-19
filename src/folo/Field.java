/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folo;

/**
 *
 * @author brent
 */
public class Field {
    
    private final Direction direction;
    private final int value;
    private final char name;
    
    public Field(Direction d, int value, char name){
        direction = d;
        this.value = value;
        this.name = name;
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public int getValue(){
        return value;
    }
    
    public char getName(){
        return name;
    }
    
}
