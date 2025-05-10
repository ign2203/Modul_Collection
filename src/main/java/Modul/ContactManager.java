package Modul;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactManager {
    private static final Logger logger = Logger.getLogger(ContactManager.class.getName());
    LinkedHashSet<Contact> contactsSet = new LinkedHashSet<>();
    Map<String, List<Contact>> contactsByName = new HashMap<>();// библиотека, внутри которой находится List (список), чтобы в телефонной книге, была возможность иметь
    // контакты с одинаковым именем, но разными значениями
    HashMap<String, Contact> phoneByName = new HashMap<>();// Библиотека, где номер это ключ(для поиска по номеру)
    Map<String, List<Contact>> contactsByGroup = new HashMap<>();


    public ContactManager() {
        contactsByGroup.put("семья", new ArrayList<>());
        contactsByGroup.put("работа", new ArrayList<>());
        contactsByGroup.put("друзья", new ArrayList<>());
    }

    public void listContact() { // метод для вывода всех контактов "кнопка 3"

    }


    public void listContactsByGroup(String groupName) {// метод для вывода контактов по группам

    }

    public void addContact(Contact CONTACT) { // в этом методе ошибка, так как при добавление контакта, он меняет группу всех контактов

    }


    public void removeContact(String phone) { // это объект phoneKey класса Contact

    }

    public void checkBookIsEmpty() { // Здесь мы написали метод, для дальнейшего упрощения. Чтобы у нас была возможность применить этот метод в других методах

    }

    public void searchByContactName(String name) { // метод для поиска контакта по имени "кнопка 4"

    }

    public void searchByContactPhone(String phone) { // метод для поиска контакта по номеру "кнопка 5"








    }


}




