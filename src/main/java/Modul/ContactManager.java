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
        try {
            this.checkBookIsEmpty();
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
            if ((!contactsByGroup.containsKey(groupName))) { // если в коллекии нет содержимого такого же как и наши ключи, то выводим, что нет такой группы
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

    public void addContact(Contact CONTACT) { // в этом методе ошибка, так как при добавление контакта, он меняет группу всех контактов
        // или сохраняет контакты во все группы, так как при просмотре контактов по группе, контакты имеются во всех группах
        if (CONTACT == null) {// здесь мы говорим, если CONTACT пуст, то Нужен для того чтобы пользователь не ввел пустые значения
            logAndPrint(Level.SEVERE, "Попытка ввести контакт с нулевым значением");
            throw new IllegalStateException("Нельзя вводить null значением");// ловим ошибку
        }
        String nameKey = CONTACT.getName().toLowerCase();// создаем переменные для добавления значений в коллекцию, нужны для актуального поиска и удаления
        String phoneKey = CONTACT.getPhone();// создаем переменные для добавления значений в коллекцию, нужны для актуального поиска и удаления
        String group = CONTACT.getGroup();//создаем переменную для возможности сохранения в отдельную коллекцию

        if (contactsSet.contains(CONTACT)) { // как я понял contains отдельный метод првоерки дубликактов по переопределнным методам в Contact, но мое мнение
            // данный метод актуален, если у нас не много контактов, лучше вообще ArrayList поменять на что-то другое, например HashSet, но не помню будет ли там вывод контактов сортирован по добавлению
            // но это все равно лучше, если мы сравнивали длины двух коллекций
            logAndPrint(Level.SEVERE, "Такой контакт уже существует!" + CONTACT);
            return;
        }

        if ((contactsByGroup.get(group) == null)) { // если в коллекии нет содержимого такого же как и наши ключи, то выводим, что нет такой группы
            //т.е. в коллекции проверяем contains Ключ и сраваниваем его с переменной group
            logAndPrint(Level.WARNING, "Группа '" + group + "' не распознана. Контакт не добавлен в группу.");
            return;
        }
        logAndPrint(Level.INFO, "Успешное добавление контакта: " + CONTACT);
        contactsSet.add(CONTACT);// добавляем контакт в arrayBook
        contactsByName.putIfAbsent(nameKey, new ArrayList<>());// добавляем в коллекцию для имени
        contactsByName.get(nameKey).add(CONTACT);
        phoneByName.put(phoneKey, CONTACT);// добавляем в коллекцию для номера
        contactsByGroup.get(group).add(CONTACT);//добавляем в коллекцию по группам (здесь трудно читать, но можно)
        // мы добавляем CONTACT в в геттер group и в коллекцию contactsByGroup
    }


    public void removeContact(String phone) { // это объект phoneKey класса Contact
        try {
            this.checkBookIsEmpty();// метод на случай, если пользователь попытается удалить контакт при пустом каталоге
            if (phone == null || phone.trim().isEmpty()) {// здесь ошибка, если значения нет в библиотеки, то программа ложится. Значит нужно поймать ошибку, через try
                // а это условие оставляем,если пользователь вообще ничего не вводит
                logAndPrint(Level.SEVERE, "Попытка удалить  контакт с нулевым значением.");
                System.out.println("Номер не может быть пустым.");
                return;
            }
            if (!phoneByName.containsKey(phone)) {// пишем условие если ключ phoneKey не найден в библиотеке
                logAndPrint(Level.WARNING, "Контакт с данным номером: " + phone + "' не найден.");
                return;
                // это условие у меня компилятор вообще не видит. Он ловит другое условие если номер null
            }

            Contact contact = phoneByName.get(phone);// зная номер, мы можем найти КОНТАКТ, создаем Объект contact с номером phoneKey
            contactsSet.remove(contact);// удаляем из linkedHashBook
            phoneByName.remove(phone);// удаляем из библиотеки phoneByName
            contactsByName.remove(contact.getName().toLowerCase());// так как ранее мы уже нашли контакт по геттеру телефона
            // то здесь мы хотим удалить контакт из библиотеки где ключ, является Именем
            List<Contact> groupList = contactsByGroup.get(contact.getGroup());// создаем groupList, и говорим что он равен contactsByGroup, и эта группа, нашего контакта
            if (groupList != null) {// если лист не пуст, то вызываем итератор, для правильного удаления
                Iterator<Contact> iterator = groupList.iterator(); // итератор, по всем контактам , нашей группе
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
        // так как мы будем часто работать со значениями в книге
        if (this.contactsSet.isEmpty()) {
            logAndPrint(Level.SEVERE, "Телефонная книга не была инициализирована");
            throw new IllegalStateException("Телефонная книга не была инициализирована");
        }
    }

    public void searchByContactName(String name) { // метод для поиска контакта по имени "кнопка 4"
        // этот метод нужно допилить, он выводит результаты только одного контакта , а если у меня есть нескольконтактов о с одинаковым именем,
        // но разными номера и конкты находятся в разхных группах
        // компилятор почему выводит только один контакт
        //  я думаю дело в том, что библиотека с ключом не совсем корректно работает по имени
        // так как при добавлении ключа в библиотеку, он просто меняет ключ и меняет контакт, и в библиотеки вместо нескольких контактов сохранен только один
        try {
            this.checkBookIsEmpty();
            String nameKey = name.toLowerCase();
            List<Contact> contacts = contactsByName.get(nameKey);
            if (contacts != null && !contacts.isEmpty()) {
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
            if (phoneByName.containsKey(phone)) {
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




