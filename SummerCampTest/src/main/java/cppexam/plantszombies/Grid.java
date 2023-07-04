package cppexam.plantszombies;

import java.util.LinkedList;
import java.util.List;

public class Grid {
    int x;
    int y;
    Plant plant;
    List<Zombie> zombies;
    
    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        zombies = new LinkedList<>();
    }
    
    public void addPlant(Plant plant) {
        this.plant = plant;
    }
    
    public void addZombie(Zombie zombie) {
        this.zombies.add(zombie);
    }
    
    public void removeZombie(Zombie zombie) {
        this.zombies.remove(zombie);
    }
    
    public void removePlant() {
        this.plant = null;
    }
    
    public boolean hasPlant() {
        return this.plant != null;
    }
    
    /**
     * 僵尸是否能够通过这个grid
     * @return 可以则返回true
     */
    public boolean canZombieMove() {
        return plant == null ||
                   plant.type == Plant.POTATO_ACTIVATED || plant.type == Plant.POTATO_INACTIVATED;
    }
    
    public boolean hasZombie() {
        return this.zombies != null && !this.zombies.isEmpty();
    }
    
    /**
     * 注意java.util.ConcurrentModificationException
     * zombie.move() 会对list做修改 不能使用增强for循环
     */
    public void zombiesMove() {
        int i = 0;
        while (i < zombies.size()) {
            Zombie zombie = zombies.get(i);
            boolean isMove = zombie.move();
            if (!isMove) i ++;
        }
    }
    
    public void zombiesAttack() {
        for (Zombie zombie : zombies) {
            zombie.attack();
        }
    }
    
    /**
     * 注意java.util.ConcurrentModificationException
     * zombie.beAttacked(hurt) 会对list做修改 不能使用增强for循环
     */
    public void zombiesBeAttacked(int hurt) {
        int i = 0;
        while (i < zombies.size()) {
            Zombie zombie = zombies.get(i);
            boolean isMove = zombie.beAttacked(hurt);
            if (!isMove) i ++;
        }
    }
}
