import java.io.IOException;

public class Game {
    public static BattleField[] battlefields = new BattleField[2]; // Список глобальных полей
    private User[] players = new User[2]; // Список игроков

    char field1[][] = new char[10][10]; // Локальное представление поля текущего игрока
    char field2[][] = new char[10][10]; // Локальное представление поля соперника

    private int id = -1; // id этого экземпляра игры

    public static int currentPlayer = 0; // Номер активного игрока
    public static boolean letTurn = true;   // Возможность установить корабль на поле


    /*
     * 0 - Расстановка кораблей
     * 1 - Игра
     * 2 - Конец игры
     */
    public static int status = 0; // Состояние игры

    public Game(int id, User player1, User player2) { // Конструктор
        this.id = id;
        players[0] = player1;
        players[1] = player2;

        battlefields[0] = new BattleField();
        battlefields[1] = new BattleField();
    }

    private void updateFields () { // Перестроить локальные представления полей
        for(int x = 0; x < 10; x++)
            for(int y = 0; y < 10; y++) {
                field1[x][y] = '^';
                field2[x][y] = '^';
            }

        for(Ship ship : battlefields[currentPlayer].getShips()) {
            for(int i=0; i<ship.getLength(); i++)
                field1[ship.getX()][ship.getY()+i] = '0';
        }

        for(Shot shot : battlefields[currentPlayer].getShots())
            field1[shot.getX()][shot.getY()] = shot.getStat() ? '+' : '–';

        for(Shot shot : battlefields[currentPlayer == 0 ? 1 : 0].getShots())
            field2[shot.getX()][shot.getY()] = shot.getStat() ? '+' : '–';
    }

    private void printFields () { // Вывести в консоль локальные представления полей
        GUI.labelFields.setText("");
        updateFields();
        GUI.labelFields.setText(GUI.labelFields.getText() + " Игрок " + (BattleShip.currentUser.getName()) +
                                "                                     " + "    Противник" + "\n");

        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++)
                GUI.labelFields.setText(GUI.labelFields.getText() + " " + field1[x][y] + " ");

            GUI.labelFields.setText(GUI.labelFields.getText() + "                 ");
            for(int x = 0; x < 10; x++)
                GUI.labelFields.setText(GUI.labelFields.getText() + " " + field2[x][y] + " ");

            GUI.labelFields.setText(GUI.labelFields.getText() + "\n");
        }
    }

    private void print (String text) { // Написать в консоль сообщение от имени текущей игры
        GUI.labelPlayingProcess.setText(text);
    }

    public User getCurrentPlayer () { // Получить ссылку на экземпляр активного игрока
        if(id <= 1)
            return players[currentPlayer];
        return null;
    }

    public void makeTurn () throws IOException { // Провести ход
        letTurn = true;
        if(battlefields[0].isDefeat()) // Если все корабли на поле 0 уничтожены
            print("Победил игрок "+players[1].getName()+"!");
        if(battlefields[1].isDefeat()) // Если все корабли на поле 1 уничтожены
            print("Победил игрок "+players[0].getName()+"!");
        int x, y;
        switch (status) {
            case 0: // Если идет расстановка
                printFields();
                for(int i=1; i<4; i++) {
                    Ship ship;

                    // Получить координаты корабля
                    print("Игрок "+players[currentPlayer].getName()+", укажите координаты корабля "+i);
                    do {
                        while(true) {
                            if(GUI.newGame == true) return;
                            System.out.print("");
                            if (GUI.lab == 0) {
                                x = Integer.valueOf((Integer) (GUI.x.getSelectedItem())) - 1;
                                y = Integer.valueOf((Integer) (GUI.y.getSelectedItem())) - 1;

                                break;
                            } else continue;
                        }

                        ship = new Ship(i, x, y);
                        GUI.lab = -1;
                    } while (!battlefields[currentPlayer].placeShip(ship));
                    printFields();
                }
                letTurn = false;
                if(currentPlayer == 0)
                    currentPlayer = 1;
                else {
                    currentPlayer = 0;
                    status = 1;
                }

                break;

            case 1: // Если идет бой
                print("Ход игрока "+players[currentPlayer].getName());

                printFields();

                // Получить координаты выстрела
                do {
                    while(true) {
                        if(GUI.newGame == true) return;
                        System.out.print("");
                        if (GUI.lab == 0) {
                            x = Integer.valueOf((Integer) (GUI.x.getSelectedItem())) - 1;
                            y = Integer.valueOf((Integer) (GUI.y.getSelectedItem())) - 1;

                            break;
                        } else continue;
                    }

                    GUI.lab = -1;
                } while (!battlefields[currentPlayer == 0 ? 1 : 0].makeShot(x , y));
                letTurn = false;
                if(GUI.newGame == true) return;
                printFields();

                // Вывод победителя после хода
                if(battlefields[0].isDefeat()) {
                    print("");
                    print("Победил игрок "+players[1].getName()+"!");
                    status = 2;
                }
                if(battlefields[1].isDefeat()) {
                    print("");
                    print("Победил игрок "+players[0].getName()+"!");
                    status = 2;
                }

                if(status != 2 && !GUI.newGame) print("Конец хода игрока "+players[currentPlayer].getName());

                // Сменить активного игрока
                if(currentPlayer == 0) currentPlayer = 1;
                else currentPlayer = 0;

                break;
        }
    }

    public static int getStatus() { return status; }
}