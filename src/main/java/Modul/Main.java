package Modul;

import java.util.InputMismatchException;//в
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
            try {         // ловим ошибку если пользователь вводит не число, программа падает
                int choice = console.nextInt();
                console.nextLine();
                switch (choice) {
                    default:
                        System.out.println("Введите число от 1 до 5");
                        break;
                    case 1:
                        System.out.println("Введите имя");
                        String name = console.nextLine();
                        System.out.println("Введите номер");
                        String phone = console.nextLine();
                        System.out.println("Введите email");
                        String email = console.nextLine();
                        System.out.println("Выберите группу для добавления контакта");
                        System.out.println("1 - семья");
                        System.out.println("2 - работа");
                        System.out.println("3 - друзья");
                        int choice2 = console.nextInt();
                        console.nextLine();
                        String group = "";
                        switch (choice2) { // здесь ошибка, добавление неправильное
                            case 1:
                                group = "семья";
                                break;
                            case 2:
                                group = "работа";
                                break;
                            case 3:
                                group = "друзья";
                                break;
                        }
                        Contact contact = new Contact(name, phone, email, group);
                        manager.addContact(contact);
                        break;
                    case 2:// программа ложится при вводе букв
                        System.out.println("Введите номер телефона для удаления контакта");
                        String phoneKey = console.nextLine();
                        manager.removeContact(phoneKey);
                        break;
                    case 3:
                        manager.listContact();
                        break;
                    case 4:// программа ложится нужно в самом методе поймать ошибку
                        System.out.println("Введите имя для поиска");
                        String name3 = console.nextLine();
                        manager.searchByContactName(name3);
                        break;
                    case 5: // программа ложится нужно в самом методе поймать ошибку
                        System.out.println("Введите номер для поиска");
                        String phoneSearch = console.nextLine();
                        manager.searchByContactPhone(phoneSearch);
                        break;
                    case 6:// программа ложится нужно в самом методе поймать ошибку
                        System.out.println("Выберите группу для вывода всех контактов");
                        System.out.println("1 - семья");
                        System.out.println("2 - работа");
                        System.out.println("3 - друзья");
                        int choice3 = console.nextInt();
                        console.nextLine();
                        String group2 = "";
                        switch (choice3) {
                            case 1:
                                group2 = "семья";
                                break;
                            case 2:
                                group2 = "работа";
                                break;
                            case 3:
                                group2 = "друзья";
                                break;
                        }
                        manager.listContactsByGroup(group2);

                        break;
                    case 0:
                        exit = true;
                        System.out.println("Выходим из приложения");
                        break;
                }
            } catch (InputMismatchException e) {
                manager.logAndPrint(Level.SEVERE, "Ошибка ввода, пользователь пытается ввести не число");
                System.out.println("Введите число от 1 до 5");
                console.next();
                exit = false;
            }

        }


    }
}

