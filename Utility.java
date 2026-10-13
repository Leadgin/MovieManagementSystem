/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moviesys;

/**
 *
 * @author MSI GFG3 THIN 11UD
 */
class Utility {
    public static void printHeader(String title) {
    int width = title.length() + 50; 
    System.out.println("-".repeat(width));

    int padding = (width - title.length()) / 2;
    String line = " ".repeat(padding) + title + " ".repeat(width - title.length() - padding);

    System.out.println(line);
    System.out.println("-".repeat(width));
    System.out.println(line);
}
}
