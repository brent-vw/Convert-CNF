/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package folo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brent
 */
public class Folo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        String loc = "C:\\folo\\org.sat4j.core.jar";

        System.out.println("------------------------------");
        System.out.println("Calculating solutions for 3X3");
        System.out.println("------------------------------");
        int[] values3 = {1, 1, 1, 1, 1, 2, 2, 2, 0};
        Direction[] directions3 = {Direction.DOWN_RIGHT, Direction.DOWN, Direction.DOWN, Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.UP_RIGHT, Direction.UP, Direction.LEFT};
        Puzzle3by3 puzz3 = new Puzzle3by3(values3, directions3);
        File by3 = puzz3.createCNFFile();
        solveCNF(by3, loc);
        System.out.println("------------------------------");
        System.out.println("");
        System.out.println("------------------------------");
        System.out.println("Calculating solutions for 4X4");
        System.out.println("------------------------------");
        int[] values4 = {3,1,0,3,2,0,1,0,2,1,2,1,2,1,2,1};
        Direction[] directions4 = {Direction.DOWN, Direction.DOWN, Direction.DOWN_LEFT, Direction.LEFT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT, Direction.LEFT, Direction.DOWN, Direction.UP, 
                Direction.UP, Direction.UP, Direction.UP, Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_LEFT, Direction.UP_LEFT};
        Puzzle4by4 puzz4 = new Puzzle4by4(values4, directions4);
        File by4 = puzz4.createCNFFile();
        solveCNF(by4, loc);
        System.out.println("------------------------------");

    }

    public static void solveCNF(File f, String loc) {
        try {
            boolean s = true;
            while (s) {
                Process proc = Runtime.getRuntime().exec("java -jar " + loc + " " + f.getAbsolutePath());
                proc.waitFor();
                InputStream in = proc.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                String line;
                boolean satis = false;
                while ((line = rd.readLine()) != null) {
                    if ((line.startsWith("s SATISFIABLE"))) {
                        satis = true;
                    }
                    if ((line.startsWith("v"))) {
                        line = line.replaceAll("v", "");
                        line = line.replaceAll(" 0", "");
                        System.out.println(line.replaceAll(" ([0-9])", "  $1"));
                        line = " 0 " + line.replaceAll("-", "%").replaceAll(" ([0-9])", " -$1").replaceAll("%", "");
                        Files.write(f.toPath(), line.getBytes(Charset.forName("UTF-8")), StandardOpenOption.APPEND);
                    }
                }
                s = satis;
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(Folo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Folo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
