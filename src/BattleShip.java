import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BattleShip {
    private static List<User> users = new ArrayList<User>(); // Список всех зарегистрированных игроков
    private static List<Game> games = new ArrayList<Game>(); // Список всех начатых игр

    public static User currentUser; // чей ход
    private static User authUser; // пользователь который сейчас авторизован

    public static int choise = 0;       // 1 - авторазация, 2 - регистрация
    public static int playChoise = 0;   // 2 - создать партию

    private static int gid = 0; // Глобальное значение id игры (+1 за каждый начатый матч)
    public static String message = "Пожалуйста, зарегистрируйтесь или авторизуйтесь";
    public static boolean flagEnemy = false;
    public static boolean firstIteration = true;


    public static void main(String[] args) throws IOException {
         try {
             UserController userController = new UserController();
             Game mainGame = null;
             User user = null;
             User user2 = null;
             GUI.createGUI();
             //chooseFileToOut();
             // ЗАТЕМ ДОБАВИТЬ КНОПКУ "ВЫХОД" И СДЕЛАТЬ В НЕЙ ВЫБОР ФАЙЛА ДЛЯ ЗАПИСИ В НЕГО КОЛЛЕКЦИИ ПОЛЬЗОВАТЕЛЕЙ
             // И ЗАВЕРШИТЬ ВЫПОЛНЕНИЕ ПРОГРАММЫ
             outer: while (true) { // Пока запущена программа

                 print(message);

                 if (GUI.newGame == true) {
                     userController = new UserController();
                     authUser = null;
                     currentUser = null;
                     mainGame = null;
                     user = null;
                     user2 = null;
                     gid = 0;
                 }

                 if (choise == 2) { // Если выбрана регистрация
                     if (GUI.readySignUp) {
                         GUI.readySignUp = false;
                         User tmpUser = new User(GUI.tempNameSignUp, GUI.tempLogSignUp, GUI.tempPassSignUp);
                         if (userController.addNewUser(tmpUser)) {

                             message = "Новый пользователь (" + GUI.tempLogSignUp + ":" + GUI.tempPassSignUp + ") создан";
                             print(message);

                             choise = 0;
                             GUI.tempLogSignUp = "";
                             GUI.tempPassSignUp = "";
                             GUI.tempNameSignUp = "";
                             GUI.signUp.setVisible(false);
                             continue;
                         } else {
                             message = "Пользователь (" + GUI.tempLogSignUp + ":" + GUI.tempPassSignUp + ") уже существует";
                             print(message);
                         }
                     }
                 }

                 if (playChoise == 2 && user2 == null && user != null) { // Если выбрано создать игру
                     GUI.newGame = false;
                     message = "Соперник, авторизуйтесь, пожалуйста";
                     flagEnemy = true;
                     print(message);
                 }

                 if (choise == 1) { // Если выбрана авторизация
                     // если окно авторизации готово, делаем что-то
                     if (GUI.readyAuth) {
                         GUI.newGame = false;
                         GUI.readyAuth = false;
                         User tmpUser = new User(GUI.tempLogAuth, GUI.tempPassAuth); // создаем пользователя с пустым именем
                         // где-то тут просто можно сделать проверку, например такую
                         if (mainGame != null) {
                             if (userController.isOnline(tmpUser) && tmpUser.equals(mainGame.getCurrentPlayer())) {
                                 message = "Сделайте Ваш ход"; // "Пользователь " + tmpUser.getLogin() + " не найден"
                                 print(message);
                                 authUser = tmpUser;
                             } else {
                                 message = "У игрока с введёнными данными сейчас нет хода"; // "Пользователь " + tmpUser.getLogin() + " не найден"
                                 print(message);
                                 continue;
                             }
                         }
                         if (user == null && playChoise != 2) {
                             user = userController.userExists(tmpUser); // проверяем его на валидность введенных данных
                             // если пользователя не найдено, перейти к следующей итерации цикла
                             if (user == null) {
                                 message = "Ошибка авторизации: проверьте введённые данные"; // "Пользователь " + tmpUser.getLogin() + " не найден"
                                 print(message);
                                 continue;
                             }
                             message = "Пользователь " + user.getName() + " авторизован";
                             print(message);
                             authUser = user;

                             currentUser = user;
                             GUI.tempLogAuth = "";
                             GUI.tempPassAuth = "";
                             GUI.auth.setVisible(false);
                             choise = 0;
                             continue;
                         } else if (user2 == null && flagEnemy) {
                             //System.out.println("Происходит авторизация соперника");
                             user2 = userController.userExists(tmpUser); // проверяем его на валидность введенных данных
                             // если пользователя не найдено перейти к следующей итерации цикла
                             if (user2 == null) {
                                 message = "Ошибка авторизации: проверьте введённые данные"; // "Пользователь " + tmpUser.getLogin() + " не найден"
                                 print(message);
                                 continue;
                             }
                             gid++;
                             mainGame = new Game(gid, user, user2); // создание игры сделать таким образом
                             games.add(mainGame); // Когда соперник авторизован добавить игру в список games
                             //message = "Соперник " + user2.getName() + " добавлен в игру №" + gid;
                             print(message);
                             GUI.tempLogAuth = "";
                             GUI.tempPassAuth = "";
                             GUI.auth.setVisible(false);
                             choise = 0;
                             flagEnemy = false;
                         } else GUI.auth.setVisible(false);


                         if (Game.currentPlayer == 0) currentUser = user;
                         else currentUser = user2;

                         // еще одна проверка перед ходом
                         if (mainGame != null) {
                             if (userController.isOnline(authUser) && authUser.equals(mainGame.getCurrentPlayer())) {
                                 message = "Сделайте Ваш ход"; // "Пользователь " + tmpUser.getLogin() + " не найден"
                                 authUser = null;
                                 print(message);

                             } else {
                                 message = "Сейчас очередь другого игрока"; // "Пользователь " + tmpUser.getLogin() + " не найден"
                                 print(message);
                                 continue;
                             }
                         }

                         while (playChoise != 2) {}   // пока не будет нажата кнопка "создать игру", процесс стоит на месте

                         firstIteration = false;

                         List<Game> locgames = getCurrentUserGames(); // Получить список игр, в которых идет ход игрока currentUser

                         if (GUI.newGame == true) continue outer;
                         if (locgames.size() == 0) {
                             if (GUI.newGame == true) continue outer;
                             message = "Нет доступных игр для данного игрока";
                             print(message);
                         } else {
                             if (GUI.newGame == true) continue outer;
                             if(Game.status != -1)
                                for (Game game : locgames)  // Если есть доступные игры, выполнить ход в каждой из них
                                    game.makeTurn();

                             if (GUI.newGame == true) continue outer;
                             if (Game.getStatus() != 2) {
                                 message = "Конец хода игрока " + currentUser.getName();
                                 print(message);
                             }

                             if (GUI.newGame == true) continue outer;
                             if (Game.getStatus() == 2 && Game.battlefields[0].isDefeat()) {
                                 message = "Победил игрок " + user2.getName() + "!";
                                 print(message);
                                 firstIteration = true;
                             } else if (Game.getStatus() == 2 && Game.battlefields[1].isDefeat()) {
                                 message = "Победил игрок " + user.getName() + "!";
                                 print(message);
                                 firstIteration = true;
                             }
                             continue;
                         }
                     }
                 }
             }
         } catch(IOException e) {
             message = "Ошибка приложения: " + e;
             print(message);
         }
    }

    public static void print(String text) { GUI.labelPlayingProcess.setText(text); }

    public static User findUser(String login) { // Найти игрока в списке users по логину
        for (User user : users)
            if (user.getLogin().compareTo(login) == 0)
                return user;

        return null;
    }

    public static List<Game> getCurrentUserGames() { // Получить список игр, в которых идет ход игрока currentUser
        List<Game> locgames = new ArrayList<Game>();

        for (Game game : games)  // Для всех игр в списке games как game
            if (game.getCurrentPlayer() == currentUser)
                locgames.add(game);

        return locgames;
    }
}