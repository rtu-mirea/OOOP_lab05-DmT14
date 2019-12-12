import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    // окна приложения
    private static JFrame playing = new JFrame("Морской бой");
    public static Auth auth = new Auth();
    public static SignUp signUp = new SignUp();
    public static JFrame tableData = new JFrame("Данные игроков");
    public static Change change = new Change();
    public static Delete deleteWindow = new Delete();

    public static JTable usersData = new JTable();

    // шрифт
    private static Font font = new Font("Times New Roman", Font.PLAIN, 14);

    // JComboBox для выбора координат из списка
    public static int[] coordinatesX = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static int[] coordinatesY = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static JComboBox<Integer> x = new JComboBox();
    public static JComboBox<Integer> y = new JComboBox();

    // панели главного окна
    private static JPanel panelFields = new JPanel();
    private static JPanel panelButtons = new JPanel();
    private static JPanel panelPlayingProcess = new JPanel();
    private static JPanel panelSetCoordinates = new JPanel();

    // метки главного окна
    public static JTextArea labelFields = new JTextArea("");
    public static JLabel labelPlayingProcess = new JLabel();

    // кнопки главного окна
    public static JButton buttonNull = new JButton("Начать заново");
    public static JButton buttonCreateGame = new JButton("Создать игру");
    public static JButton buttonAuth = new JButton("Авторизация");
    public static JButton buttonSignUp = new JButton("Регистрация");
    public static JButton buttonMake = new JButton("Сделать ход");
    public static JButton buttonOpenTable = new JButton("Данные игроков");

    public static JButton buttonChange = new JButton("Изменить");
    public static JButton buttonDelete = new JButton("Удалить");



    public static int lab = -1;                 // изменяется в случае нажатия на кнопку "сделать ход"
    public static String tempLogAuth = "";      // переменная для хранения логина при авторизации
    public static String tempPassAuth = "";     // и т.д.
    public static String tempLogSignUp = "";    // ...
    public static String tempPassSignUp = "";
    public static String tempNameSignUp = "";


    public static boolean readyAuth = false;
    public static boolean readySignUp = false;
    public static boolean newGame = false;      // если нажата кнопка "Начать заново", newGame = true


    public static void createGUI() {
        // главное окно
        playing.setSize(600, 400);
        playing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playing.setLocationRelativeTo(null);
        playing.setLayout(null);
        playing.setResizable(false);
        playing.setFont(font);


        // панель главного окна "поля игроков"
        playing.add(panelFields);
        panelFields.setLayout(new FlowLayout());
        panelFields.setBackground(new Color(198, 198, 198));
        panelFields.setBounds(0, 0, 400, 200);
        panelFields.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelFields.setFont(font);

        // панель главного окна "кнопки"
        playing.add(panelButtons);
        panelButtons.setLayout(new FlowLayout());
        panelButtons.setBackground(new Color(198, 198, 198));
        panelButtons.setBounds(400, 0, 200, 400);
        panelButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelButtons.setFont(font);

        // панель главного окна "сообщения от игры"
        playing.add(panelPlayingProcess);
        panelPlayingProcess.setLayout(new FlowLayout());
        panelPlayingProcess.setBackground(new Color(198, 198, 198));
        panelPlayingProcess.setBounds(0, 200, 400, 100);
        panelPlayingProcess.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelPlayingProcess.setFont(font);

        // панель главного окна "установка координат"
        playing.add(panelSetCoordinates);
        panelSetCoordinates.setLayout(new FlowLayout());
        panelSetCoordinates.setBackground(new Color(198, 198, 198));
        panelSetCoordinates.setBounds(0, 300, 400, 100);
        panelSetCoordinates.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelSetCoordinates.setFont(font);


        // метка главного окна "поля игроков"
        labelFields.setFont(font);
        labelFields.setBackground(new Color(198, 198, 198));
        labelFields.setEditable(false);
        labelFields.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelFields.add(labelFields);

        // метка главного окна "сообщения от игры"
        labelPlayingProcess.setFont(font);
        panelPlayingProcess.add(labelPlayingProcess);


        // текстовые поля основного окна
        panelSetCoordinates.add(x);
        x.setFont(font);
        for(int i = 0; i < coordinatesX.length; i++)
            x.addItem(coordinatesX[i]);

        panelSetCoordinates.add(y);
        y.setFont(font);
        for(int i = 0; i < coordinatesY.length; i++)
            y.addItem(coordinatesY[i]);


        // кнопка главного окна "сделать ход"
        panelSetCoordinates.add(buttonMake);
        buttonMake.setPreferredSize(new Dimension(150,30));
        buttonMake.setFont(font);
        buttonMake.setForeground(Color.blue);
        buttonMake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!BattleShip.firstIteration && Game.letTurn)
                    lab = 0;
            }
        });

        // кнопка главного окна "начать заново"
        panelButtons.add(buttonNull);
        buttonNull.setPreferredSize(new Dimension(150,30));
        buttonNull.setFont(font);
        buttonNull.setForeground(Color.blue);
        buttonNull.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Game.currentPlayer = 0;
                Game.status = 0;
                Game.letTurn = true;
                BattleShip.choise = 0;
                BattleShip.playChoise = 0;
                BattleShip.message = "Пожалуйста, зарегистрируйтесь или авторизуйтесь";
                BattleShip.print( BattleShip.message);
                BattleShip.flagEnemy = false;
                BattleShip.firstIteration = true;
                labelFields.setText("");
                newGame = true;
            }
        });

        // кнопка главного окна "создать игру"
        panelButtons.add(buttonCreateGame);
        buttonCreateGame.setPreferredSize(new Dimension(150,30));
        buttonCreateGame.setFont(font);
        buttonCreateGame.setForeground(Color.blue);
        buttonCreateGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BattleShip.playChoise = 2;
                GUI.newGame = false;
            }
        });

        // кнопка главного окна "авторизация"
        panelButtons.add(buttonAuth);
        buttonAuth.setPreferredSize(new Dimension(150,30));
        buttonAuth.setFont(font);
        buttonAuth.setForeground(Color.blue);
        buttonAuth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BattleShip.choise = 1;
                auth.setSize(250, 145);
                auth.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                auth.setLocationRelativeTo(null);
                auth.setLayout(new FlowLayout());
                auth.setTitle("Авторизация");
                auth.setResizable(false);

                auth.add(auth.panelAuth);
                auth.add(auth.panelGetData);
                auth.add(auth.panelLabels);
                auth.add(auth.panelText);
                auth.add(auth.panelButtonAuth);

                auth.setVisible(true);
            }
        });

        // кнопка дочернего окна "авторизация"
        auth.okButtonAuth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempLogAuth = auth.loginTextFieldAuth.getText();
                tempPassAuth = auth.passTextFieldAuth.getText();
                if (!tempLogAuth.equals("") && !tempPassAuth.equals("")) {
                    readyAuth = true;
                    auth.loginTextFieldAuth.setText("");
                    auth.passTextFieldAuth.setText("");
                    Game.letTurn = true;
                }
            }
        });

        // кнопка главного окна "регистрация"
        panelButtons.add(buttonSignUp);
        buttonSignUp.setPreferredSize(new Dimension(150,30));
        buttonSignUp.setFont(font);
        buttonSignUp.setForeground(Color.blue);
        buttonSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BattleShip.choise = 2;
                signUp.setSize(250, 175);
                signUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                signUp.setLocationRelativeTo(null);
                signUp.setLayout(new FlowLayout());
                signUp.setTitle("Регистрация");
                signUp.setResizable(false);

                signUp.add(signUp.panelSignUp);
                signUp.add(signUp.panelGetData);
                signUp.add(signUp.panelLabels);
                signUp.add(signUp.panelText);
                signUp.add(signUp.panelButtonSignUp);

                signUp.setVisible(true);
            }
        });

        // кнопка дочернего окна "регистрация"
        signUp.buttonSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempLogSignUp = signUp.loginTextFieldSignUp.getText();
                tempPassSignUp = signUp.passTextFieldSignUp.getText();
                tempNameSignUp = signUp.nameTextFieldSignUp.getText();
                if(!tempLogSignUp.equals("") && !tempPassSignUp.equals("") && !tempNameSignUp.equals("")) {
                    readySignUp = true;
                    signUp.loginTextFieldSignUp.setText("");
                    signUp.passTextFieldSignUp.setText("");
                    signUp.nameTextFieldSignUp.setText("");
                }
            }
        });

        // кнопка главного окна "данные игроков"
        panelButtons.add(buttonOpenTable);
        buttonOpenTable.setPreferredSize(new Dimension(150,30));
        buttonOpenTable.setFont(font);
        buttonOpenTable.setForeground(Color.blue);
        buttonOpenTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createTable();
            }
        });





        tableData.add(buttonDelete);
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteWindow.setSize(250, 150);
                deleteWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                deleteWindow.setLocationRelativeTo(null);
                deleteWindow.setLayout(new FlowLayout());
                deleteWindow.setTitle("Удаление");
                deleteWindow.setResizable(false);

                deleteWindow.add(deleteWindow.panelDelete);
                deleteWindow.add(deleteWindow.panelGetDataDelete);
                deleteWindow.add(deleteWindow.panelLabelsDelete);
                deleteWindow.add(deleteWindow.panelTextDelete);
                deleteWindow.add(deleteWindow.panelButtonDelete);

                deleteWindow.setVisible(true);
            }
        });

        deleteWindow.buttonDeleteData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UserController userController = new UserController();
                User user = null;
                int tmp = 0;
                outer: while (true) {
                    user = null;
                    System.out.println("1");
                    if (deleteWindow.loginTextFieldDelete.getText() == "" || deleteWindow.passTextFieldDelete.getText() == "") return;
                    User tmpUser = new User(deleteWindow.loginTextFieldDelete.getText(), deleteWindow.passTextFieldDelete.getText());
                    user = userController.userExists(tmpUser);
                    if (user == null) return;
                    System.out.println("2");
                    for (int i = 0; i < UserController.list.size(); i++) {
                        String n = UserController.list.get(i).getLogin();
                        String m = UserController.list.get(i).getPassword();
                        if (n.equals(deleteWindow.loginTextFieldDelete.getText()) && m.equals(deleteWindow.passTextFieldDelete.getText())) {
                            System.out.println("3");
                            UserController.list.remove(i);
                            tmp = 1;
                            break outer;
                        }
                    }
                    if(tmp != 1) { return; }
                }

                UserController.inFile();
                BattleShip.message = "Пользователь " + user.getName() + " удалён";
                BattleShip.print(BattleShip.message);

                deleteWindow.loginTextFieldDelete.setText("");
                deleteWindow.passTextFieldDelete.setText("");

                deleteWindow.setVisible(false);
                tableData.setVisible(false);
            }
        });

        tableData.add(buttonChange);
        buttonChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                change.setSize(250, 180);
                change.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                change.setLocationRelativeTo(null);
                change.setLayout(new FlowLayout());
                change.setTitle("Изменение");
                change.setResizable(false);

                change.add(change.panelChange);
                change.add(change.panelGetDataChange);
                change.add(change.panelLabelsChange);
                change.add(change.panelTextChange);
                change.add(change.panelButtonChange);


                change.setVisible(true);
            }
        });

        change.buttonChangeData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                User user = null;
                int tmp = 0;
                outer: while (true) {
                    UserController userController = new UserController();
                    user = null;
                    if (change.loginTextFieldChange.getText() == "" || change.oldPassTextFieldChange.getText() == "" || change.newPassTextFieldChange.getText() == "") return;
                    User tmpUser = new User(change.loginTextFieldChange.getText(), change.oldPassTextFieldChange.getText());
                    user = userController.userExists(tmpUser);
                    if (user == null) return;
                    for (int i = 0; i < UserController.list.size(); i++) {
                        String n = UserController.list.get(i).getLogin();
                        String m = UserController.list.get(i).getPassword();
                        if (n.equals(change.loginTextFieldChange.getText()) && m.equals(change.oldPassTextFieldChange.getText())) {
                            UserController.list.get(i).setData(change.newPassTextFieldChange.getText());
                            tmp = 1;
                            break outer;
                        }
                    }
                    if(tmp != 1) { return; }
                }

                UserController.inFile();
                BattleShip.message = "Данные пользователя " + user.getName() + " изменены";
                BattleShip.print(BattleShip.message);

                change.loginTextFieldChange.setText("");
                change.oldPassTextFieldChange.setText("");
                change.newPassTextFieldChange.setText("");

                change.setVisible(false);
                tableData.setVisible(false);
            }
        });




        playing.setVisible(true);
    }

    public static void createTable() {

        Object[] headers = {"Логин", "Пароль", "Имя"};
        int size = UserController.list.size();
        Object[][] data = new Object[size][3];

        for(int i = 0; i < size; i++) {
            data[i][0] = UserController.list.get(i).getLogin();
            data[i][1] = UserController.list.get(i).getPassword();
            data[i][2] = UserController.list.get(i).getName();
        }
        tableData = new JFrame("Данные игроков");
        usersData = new JTable(data, headers);
        tableData.add(buttonDelete);
        tableData.add(buttonChange);

        tableData.setSize(300, 200);
        tableData.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableData.setLocationRelativeTo(null);
        tableData.setLayout(new FlowLayout());
        tableData.setResizable(false);
        tableData.setFont(font);

        JScrollPane jscrlp = new JScrollPane(usersData);
        usersData.setPreferredScrollableViewportSize(new Dimension(250, 100));
        tableData.getContentPane().add(jscrlp);
        usersData.revalidate();
        tableData.setVisible(true);
    }
}