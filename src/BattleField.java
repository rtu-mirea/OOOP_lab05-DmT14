import java.util.ArrayList;
import java.util.List;

public class BattleField {
    private List<Ship> ships = new ArrayList<Ship>(); // ArrayList список всех кораблей на поле
    private List<Shot> shots = new ArrayList<Shot>(); // ArrayList список выстрелов кораблей на поле

    public boolean placeShip(Ship ship) { // Разместить корабль
        if(ship.getX()<0 || ship.getX()>=10 || ship.getY()<0 || ship.getY()>=10) // Проверить на принадлежность игровому полю 10x10
            return false;

        if(ship.getY() + ship.getLength() >= 10) // Проверить по длине
            return false;


        // Поверка пересечений с другими кораблями
        int x = ship.getX(); // Смотреть по столбцу с кораблем
        for(int y=ship.getY(); y<ship.getY()+ship.getLength(); y++) // Проход по каждой клетке текущего корабля
            for(Ship lship : ships)  // Проверка на пересечения с каждым другим размещенным кораблем
                if(x == lship.getX())
                    for(int i=lship.getY(); i<lship.getY()+lship.getLength(); i++)
                        if(y == i)
                            return false;

        if(ships.size() >= 3) // Макс. длина = 3
            return false;

        ships.add(ship); // Добавить в список кораблей

        return true;
    }

    public List<Ship> getShips () { // Получить список кораблей на поле
        return ships;
    }

    public List<Shot> getShots () { // Получить список выстрелов на поле
        return shots;
    }

    public boolean makeShot (int x, int y) { // Произвести выстрел
        if(x<0 || x>=10 || y<0 || y>=10) // Проверка на принадлежность полю 10x10
            return false;

        for(Shot shot : shots)  // Проверка на совпадение с уже совершенными
            if(shot.getX() == x && shot.getY() == y)
                return false;

        boolean stat = false;

        for(Ship ship : ships)  // Проверка на попадание по кораблю из ships
            if(x == ship.getX())
                for(int i=ship.getY(); i<ship.getY()+ship.getLength(); i++)
                    if(y == i) {
                        ship.makeWound();
                        stat = true;
            }

        shots.add(new Shot(x, y, stat)); // Добавить выстрел в список shots

        return true;
    }

    public boolean isDefeat () { // Проверить корабли на состояние
        int destroyed = 0;

        for(Ship ship : ships)
            if(ship.isDestroyed())
                destroyed++;

        if(destroyed >= 3) // Если все 3 корабля на поле уничтожены
            return true;

        return false;
    }
}