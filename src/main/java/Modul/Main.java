package Modul;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.out.println("Приветствую Вас");
        Scanner console = new Scanner(System.in);
        boolean exit = false;
        ContactManager manager = new ContactManager();
        while (!exit) {
            System.out.println("Выберите действие:");
            System.out.println("1 - Добавить контакт");
            System.out.println("2 - Удалить контакт");
            System.out.println("3 - Посмотреть все контакты");
            System.out.println("4 - Найти контакт по имени");
            System.out.println("5 - Найти контакт по номеру");
            System.out.println("6 - Посмотреть контакты по группе");
            System.out.println("0 - Выход");

                int choice = console.nextInt();
                console.nextLine();
                switch (choice) {
                    default:
                        System.out.println("Введите число от 1 до 5");
                        break;
                    case 1:

                        break;
                    case 2:// программа ложится при вводе букв

                        break;
                    case 3:

                        break;
                    case 4:// программа ложится нужно в самом методе поймать ошибку

                        break;
                    case 5: // программа ложится нужно в самом методе поймать ошибку

                        break;
                    case 6:// программа ложится нужно в самом методе поймать ошибку

                        break;
                    case 0:

                }
            }
        }
    }
