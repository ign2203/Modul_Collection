package Modul;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactManager {
    private static final Logger logger = Logger.getLogger(ContactManager.class.getName());
    LinkedHashSet<Contact> contactsSet = new LinkedHashSet<>();
    Map<String, List<Contact>> contactsByName = new HashMap<>();// библиотека, внутри которой находится List (список), чтобы в телефонной книге, чтобы была возможность иметь
    // контакты с одинаковым именем, но разными значениями
    HashMap<String, Contact> phoneByName = new HashMap<>();// Библиотека, где номер это ключ(для поиска по номеру)
    Map<String, List<Contact>> contactsByGroup = new HashMap<>();// библиотека для вывода контактов по группам


    public ContactManager() {// создаем три отдельные группы для добавления контактов
        // создание новые группы не реализовано
        contactsByGroup.put("семья", new ArrayList<>());
        contactsByGroup.put("работа", new ArrayList<>());
        contactsByGroup.put("друзья", new ArrayList<>());
    }

    public void listContact() { // метод для вывода всех контактов "кнопка 3"
        try {
            this.checkBookIsEmpty();// метод на случай если пользователь попробует вывести  пустой каталог контактов
            logAndPrint(Level.INFO, "Выводим все контакты в телефонной книге");
            System.out.println("Список всех контактов:");
            for (Contact CONTACT : contactsSet) {
                System.out.println(CONTACT);
            }
        } catch (IllegalStateException var1) {
            logAndPrint(Level.SEVERE, "Телефонная книга не была инициализирована");
        }
    }


    public void listContactsByGroup(String groupName) {// метод для вывода контактов по группам
        try {
            this.checkBookIsEmpty();// условия если у нас пользователь пытается вывести контакты, при пустом каталоге
            if ((!contactsByGroup.containsKey(groupName))) { //если в коллекии нет содержимого такого же как и наши ключи, то выводим, что нет такой группы
                //т.е. в коллекции проверяем contains Ключ и сраваниваем его с переменной group
                logAndPrint(Level.WARNING, "Группа '" + groupName + "' не распознана.");
                return;
            }
            logger.info("Выводим все контакты из группы: " + groupName);
            System.out.println("--- Контакты в группе " + groupName + " : ---");
            for (Contact CONTACT : contactsByGroup.get(groupName)) {
                logAndPrint(Level.INFO, "Контакт найден:");
                System.out.println(CONTACT);
            }
        } catch (IllegalStateException var1) {
            logAndPrint(Level.SEVERE, "Телефонная книга не была инициализирована");
        }
    }

    public void addContact(Contact CONTACT) { // метод для добавления контактов

        if (CONTACT == null) {// создаем условие, если пользователь попытается при добавлении ничего не вводить
            logAndPrint(Level.SEVERE, "Попытка ввести контакт с нулевым значением");
            throw new IllegalStateException("Нельзя вводить null значением");// ловим ошибку
        }
        String nameKey = CONTACT.getName().toLowerCase();// создаем переменные для добавления значений в коллекцию, нужны для актуального поиска и удаления
        String phoneKey = CONTACT.getPhone();// создаем переменные для добавления значений в коллекцию, нужны для актуального поиска и удаления
        String group = CONTACT.getGroup();//создаем переменную для возможности сохранения в отдельную коллекцию

        if (contactsSet.contains(CONTACT)) { // создаем условие, в котором contains отдельный метод проверки дублик актов. По переопределённым
            // методам в Contact
            logAndPrint(Level.SEVERE, "Такой контакт уже существует!" + CONTACT);
            return;
        }

        if ((contactsByGroup.get(group) == null)) { // если в каллерии нет содержимого такого же как и наши ключи (групп), то выводим, что нет такой группы
            //т.е. в коллекции проверяем contains Ключ и сравниваем его с переменной group
            logAndPrint(Level.WARNING, "Группа '" + group + "' не распознана. Контакт не добавлен в группу.");
            return;
        }
        logAndPrint(Level.INFO, "Успешное добавление контакта: " + CONTACT);
        contactsSet.add(CONTACT);// добавляем контакт
        contactsByName.putIfAbsent(nameKey, new ArrayList<>());// добавляем в коллекцию для имени
        contactsByName.get(nameKey).add(CONTACT);
        phoneByName.put(phoneKey, CONTACT);// добавляем в коллекцию для номера
        contactsByGroup.get(group).add(CONTACT);//добавляем в коллекцию по группам
        // мы добавляем CONTACT в  коллекцию contactsByGroup
    }


    public void removeContact(String phone) { // это объект phoneKey класса Contact
        try {
            this.checkBookIsEmpty();// метод на случай, если пользователь попытается удалить контакт при пустом каталоге
            if (phone == null || phone.trim().isEmpty()) { // если строка вводимая пользователем пуста, trim метод, для удаления пробелов в строке (в начале и в конце)
                //isEmpty() метод для проверки, пуста ли длина
                logAndPrint(Level.SEVERE, "Попытка удалить  контакт с нулевым значением.");
                System.out.println("Номер не может быть пустым.");
                return;
            }
            if (!phoneByName.containsKey(phone)) {// пишем условие если ключ phoneKey не найден в библиотеке
                logAndPrint(Level.WARNING, "Контакт с данным номером: " + phone + "' не найден.");
                return;
            }

            Contact contact = phoneByName.get(phone);// зная номер, мы можем найти КОНТАКТ, создаем Объект contact с номером phoneKey
            contactsSet.remove(contact);// удаляем из linkedHashBook
            phoneByName.remove(phone);// удаляем из библиотеки phoneByName
            contactsByName.remove(contact.getName().toLowerCase());// так как ранее мы уже нашли контакт по геттеру телефона
            // то здесь мы хотим удалить контакт из библиотеки где ключ, является Именем
            List<Contact> groupList = contactsByGroup.get(contact.getGroup());// создаем groupList, и говорим что он равен contactsByGroup, и эта группа, нашего контакта
            if (groupList != null) {// если лист не пуст, то вызываем итератор, для правильного удаления
                Iterator<Contact> iterator = groupList.iterator(); // итератор, по всем контактам  нашей группе
                while (iterator.hasNext()) {//проходим каждый объект
                    Contact c = iterator.next();// каждый следующий объект будет равен c
                    if (c.equals(contact)) { // если с, это наш контакт,
                        iterator.remove();//то удаляем его
                        break;// и закрываем цикл
                    }
                }
                logAndPrint(Level.INFO, "Успешное удаление  контакта: " + contact);
            }
        } catch (IllegalStateException var1) {
            logAndPrint(Level.SEVERE, "Телефонная книга не была инициализирована");
        }
    }

    public void checkBookIsEmpty() { // Здесь мы написали метод, для дальнейшего упрощения. Чтобы у нас была возможность применить этот метод в других методах
        // так как мы будем часто работать со значениями в телефонной книге
        if (this.contactsSet.isEmpty()) {
            logAndPrint(Level.SEVERE, "Телефонная книга не была инициализирована");
            throw new IllegalStateException("Телефонная книга не была инициализирована");
        }
    }

    public void searchByContactName(String name) { // метод для поиска контакта по имени
        try {
            this.checkBookIsEmpty();
            String nameKey = name.toLowerCase();
            List<Contact> contacts = contactsByName.get(nameKey); //создаем список contacts, который равен списку контактов которые мы получаем по геттеру nameKey,
            //который ввел ранее пользователь
            if (contacts != null && !contacts.isEmpty()) {// создаем условие, если список не пуст и он вообще существует
                logAndPrint(Level.INFO, "Найдено " + contacts.size() + " контактов с именем: " + name);
                for (Contact c : contacts) {
                    System.out.println(c);
                }
            } else {
                System.out.println("Контакт с именем" + name + " не найден");
            }
        } catch (IllegalStateException var1) {
            logAndPrint(Level.SEVERE, "Телефонная книга не была инициализирована");
        }
    }

    public void searchByContactPhone(String phone) { // метод для поиска контакта по номеру "кнопка 5"
        try {
            this.checkBookIsEmpty();
            if (phoneByName.containsKey(phone)) {//создаем условие, которое проверяем содержимое библиотеки phoneByName с помощью containsKey, по ключу который вводит пользователь
                logAndPrint(Level.INFO, "Успешный поиск по номеру");
                System.out.println(phoneByName.get(phone));
            } else {
                System.out.println("Контакт с номером" + phone + " не найден");
            }
        } catch (IllegalStateException var1) {
            logAndPrint(Level.SEVERE, "Телефонная книга не была инициализирована");
        }
    }

    public void logAndPrint(Level level, String message) {
        logger.log(level, message);
        System.out.println(message);
    }


}




