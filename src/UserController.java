import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserController {
    public static ArrayList<User> list;
    private Set<User> onlineSet;
    public static final String pathName = "users.txt";

    public UserController() {
        onlineSet = new HashSet<>();
        list = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathName))) {
            list = (ArrayList<User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // проверяем правильность введенного логина и пароля для пользователя, если введенные данные соответствуют
    // пользователю из списка, то возвращается true и пользователь добавляется в онлайн список
    public User userExists(User user) {
        for (User u : list) {
            if (u.equals(user)) {
                if (onlineSet.size() < 2 && onlineSet.add(user)) {
                    return u;
                }
            }
        }
        return null;
    }

    public boolean isOnline(User user) {
        return onlineSet.contains(user);
    }

    // регистрация нового пользователя и добавление его в лист и в файл
    public boolean addNewUser(User user) throws IOException {
        boolean result = true;
        for (User u : list) {
            if (u.equals(user)) {
                result = false;
            }
        }
        if (result) {
            list.add(user);
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathName))) {
                out.writeObject(list);
            } catch (IOException e) {
                throw e;
            }
        }
        return result;
    }

    public static void inFile() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathName))) {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}