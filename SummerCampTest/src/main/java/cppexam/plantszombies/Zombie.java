package cppexam.plantszombies;

public class Zombie {
    int hp;
    int atk;
    int speed;
    Grid grid;
    
    public Zombie(int... args) {
        this.hp = args[0];
        this.atk = args[1];
        this.speed = args[2];
        this.grid = Board.getGrid(args[3], 9);
        this.grid.addZombie(this);
    }
    
    public boolean beAttacked(int hurt) {
        this.hp -= hurt;
        if (this.hp <= 0) {
            this.grid.removeZombie(this); // 僵尸消失
            Game.zombieCnt--;
            return true;
        }
        return false;
    }
    
    public void attack() {
        if (grid.hasPlant()) { // 攻击同处一个格子的植物
            grid.plant.beAttacked(atk);
        }
    }
    
    public boolean move() {
        // 要注意，不能一下子直接移动speed格，沿途可能有植物
        boolean isMove = false;
        for (int i = 0; i < speed; i ++) {
            if (!grid.canZombieMove()) return isMove;
            if (grid.hasPlant()) { // 此时有植物肯定是土豆地雷
                grid.plant.type = Plant.POTATO_ACTIVATED; // 激活土豆地雷
            }
            grid.removeZombie(this);
            isMove = true;
            if (grid.y == 0) {
                Game.zombieIntoRoom = true;
                return true;
            }
            grid = Board.getGrid(grid.x, grid.y-1);
            grid.addZombie(this);
        }
        return isMove;
    }
}
