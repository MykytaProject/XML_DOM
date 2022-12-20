package main;

import java.util.Scanner;

import main.util.Metodos;

public class App {
    public static void main(String[] args) {
        Metodos menu = new Metodos();

        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("--- Menú ---");
            System.out.println("1. Leer XML");
            System.out.println("2. Crear XML");
            System.out.println("3. Agregar cliente");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    menu.leerXML();
                    break;
                case 2:
                    menu.crearXML();
                    break;
                case 3:
                    menu.agregarCliente();
                    break;
                case 4:
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
                    break;
            }
        }
        sc.close();
    }
}
