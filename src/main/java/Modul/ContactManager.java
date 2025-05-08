package Modul;


import java.util.*;
import java.util.logging.Logger;

public class ContactManager {
    private static final Logger logger = Logger.getLogger(ContactManager.class.getName());
    LinkedHashSet<Contact> linkedHashBook = new LinkedHashSet<>();
    HashMap<String, Contact> contactsByName = new HashMap<>(); // Библиотека, где имя это ключ(для поиска по имени)
    HashMap<String, Contact> phoneByName = new HashMap<>();// Библиотека, где номер это ключ(для поиска по номеру)
    Map<String, List<Contact>> contactsByGroup = new HashMap<>();

    public ContactManager() {
        contactsByGroup.put("Семья", new ArrayList<>());
        contactsByGroup.put("Работа", new ArrayList<>());
        contactsByGroup.put("Друзья", new ArrayList<>());
    }

    public void listContact() { // метод для вывода всех контактов "кнопка 3"

        this.checkBookIsEmpty();
        logger.info("Выводим все контакты в телефонной книге");
        System.out.println("Список всех контактов:");
        for (Contact CONTACT : linkedHashBook) {
            System.out.println(CONTACT);
        }
    }


    public void listContactsByGroup(String groupName) {
        this.checkBookIsEmpty();// условия если у нас пользователь пытается вывести контакты, при пустом каталоге
        if ((!contactsByGroup.containsKey(groupName))) { // если в коллекии нет содержимого такого же как и наши ключи, то выводим, что нет такой группы
            //т.е. в коллекции проверяем contains Ключ и сраваниваем его с переменной group
            logger.warning("Группа '" + groupName + "' не распознана. Невозможно вывести контакты группы");
            System.out.println("Группа '" + groupName + "' не распознана.");
            return;
        }
        logger.info("Выводим все контакты из группы: " + groupName);
        System.out.println("--- Контакты в группе " + groupName + " : ---");
        for (Contact CONTACT : contactsByGroup.get(groupName)) {
            System.out.println(CONTACT);
        }
    }

    public void addBook(Contact CONTACT) { // метод по добавлению контакта "кнопка 1"
        if (CONTACT == null) {// здесь мы говорим, если CONTACT пуст, то Нужен для того чтобы пользователь не ввел пустые значения
            System.out.println("Попытка ввести контакт с нулевым значением.");
            logger.severe("Попытка ввести контакт с нулевым значением");
            throw new IllegalStateException("Нельзя вводить null значением");// ловим ошибку
        }
        String nameKey = CONTACT.getName().toLowerCase();// создаем переменные для добавления значений в коллекцию, нужны для актуального поиска и удаления
        String phoneKey = CONTACT.getPhone();// создаем переменные для добавления значений в коллекцию, нужны для актуального поиска и удаления
        String group = CONTACT.getGroup();//создаем переменную для возможности сохранения в отдельную коллекцию

        if (linkedHashBook.contains(CONTACT)) { // как я понял contains отдельный метод првоерки дубликактов по переопределнным методам в Contact, но мое мнение
            // данный метод актуален, если у нас не много контактов, лучше вообще ArrayList поменять на что-то другое, например HashSet, но не помню будет ли там вывод контактов сортирован по добавлению
            // но это все равно лучше, если мы сравнивали длины двух коллекций
            logger.severe("Найден дубликат при добавлении");
            System.out.println("Такой контакт уже существует!" + CONTACT);
            return;
        }

        if ((!contactsByGroup.containsKey(group))) { // если в коллекии нет содержимого такого же как и наши ключи, то выводим, что нет такой группы
            //т.е. в коллекции проверяем contains Ключ и сраваниваем его с переменной group
            logger.warning("Группа '" + group + "' не распознана. Контакт не добавлен в группу.");
            System.out.println("Группа '" + group + "' не распознана. Контакт не добавлен в группу.");
            return;
        }
        logger.info("успешное добавление книги в библиотеку");
        System.out.println("Успешное добавлении книги в библиотеку!");
        linkedHashBook.add(CONTACT);// добавляем контакт в arrayBook
        contactsByName.put(nameKey, CONTACT);// добавляем в коллекцию для имени
        phoneByName.put(phoneKey, CONTACT);// добавляем в коллекцию для номера
        contactsByGroup.get(group).add(CONTACT);//добавляем в коллекцию по группам (здесь трудно читать, но можно)
        // мы добавляем CONTACT в в геттер group и в коллекцию contactsByGroup
    }

    public void removeContact(Contact phoneKey) { // это объект phoneKey класса Contact
        this.checkBookIsEmpty();// метод на случай, если пользователь попытается удалить контакт при пустом каталоге
        if (phoneKey == null) {// здесь мы говорим, если пользователь пытается ничего не вводить, то
            System.out.println("Попытка ввести контакт с нулевым значением.");
            logger.severe("Попытка ввести контакт с нулевым значением");
            throw new IllegalStateException("Нельзя вводить null значением");// ловим ошибку
        }
        String phone = phoneKey.getPhone();// создаем переменную phone, где она равна КЛЮЧУ getPhone
        if (!phoneByName.containsKey(phone)) {// пишем условие если ключ phoneKey не найден в библиотеке
            logger.warning("Номер " + phone + "не найден.");
            System.out.println("Контакт с данным номером: " + phone + "' не найден.");
            return;
        }
        Contact contact = phoneByName.get(phone);// зная номер, мы можем найти КОНТАКТ, создаем переменную contact с номером phoneKey
        linkedHashBook.remove(contact);// удаляем из linkedHashBook
        phoneByName.remove(phone);// удаляем из библиотеки phoneByName
        contactsByName.remove(contact.getName().toLowerCase());//удаляем из библиотеки contactsByName (contact.getName().toLowerCase()) здесь я не совсем понимаю удаление,зачем нам вообще он жен нужен toLowerCase() для ввода пользователем, что компилятор исключл регистр букв
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
            logger.info("Успешное удаление с контакта: " + contact);
            System.out.println("Успешное удаление контакта с номером: " + contact);
        }
    }

    public void checkBookIsEmpty() { // Здесь мы написали метод, для дальнейшего упрощения. чтобы у нас была возможность применить этот метод в других методах
        // так как мы будем часто работать со значениями в книге
        if (this.linkedHashBook.isEmpty()) {
            System.out.println("Телефонная книга не была инициализирована");
            logger.severe("Пользователь пытается выполнить операцию с пустой телефонной книгой");
            throw new IllegalStateException("Телефонная книга не была инициализирована");
        }
    }

    public void searchByContactName(String name) { // метод для поиска контакта по имени "кнопка 4"
        this.checkBookIsEmpty();
        String nameKey = name.toLowerCase();
        if (contactsByName.containsKey(nameKey)) {
            System.out.println("Вы успешно нашли контакт");
            System.out.println(contactsByName.get(nameKey));
        } else {
            System.out.println("Контакт с именем" + name + " не найден");
        }
    }

    public void searchByContactPhone(String phone) { // метод для поиска контакта по номеру "кнопка 5"
        this.checkBookIsEmpty();
        if (phoneByName.containsKey(phone)) {
            System.out.println("Вы успешно нашли контакт");
            System.out.println(phoneByName.get(phone));
        } else {
            System.out.println("Контакт с номером" + phone + " не найден");
        }
    }


}




