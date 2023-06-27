package cppexam.plantszombies;

import java.util.HashMap;
import java.util.Map;

public class Plant {
    
    public static final int PEA = 0;
    public static final int NUT = 1;
    public static final int POTATO_INACTIVATED = 2;
    public static final int POTATO_ACTIVATED = 3;
    private static final Map<String, Integer> typeMap;
    
    static {
        typeMap = new HashMap<>();
        typeMap.put("pea", PEA);
        typeMap.put("nut", NUT);
        typeMap.put("potato", POTATO_INACTIVATED);
    }
    
    int type;
    int hp = 0;
    int atk = 0;
    Grid grid;
    
    public Plant(String type, int... args){
        this.type = typeMap.get(type);
        switch (this.type) {
            case PEA:
                this.hp = args[0];
                this.atk = args[1];
                this.grid = Board.getGrid(args[2], args[3]);
                break;
            case NUT:
                this.hp = args[0];
                this.grid = Board.getGrid(args[1], args[2]);
                break;
            case POTATO_INACTIVATED:
                this.atk = args[0];
                this.grid = Board.getGrid(args[1], args[2]);
                break;
            default:
                break;
        }
        if (this.grid != null) this.grid.addPlant(this);
    }
    
    public void beAttacked(int hurt) {
        if (this.type == PEA || this.type == NUT) {
            this.hp -= hurt;
            if (this.hp <= 0) {
                this.grid.removePlant();
                Game.plantCnt--;
            }
        }
    }
    
    public void attack() {
        switch (this.type) {
            case PEA:
                peaAttack();
                break;
            case POTATO_INACTIVATED:
                // 对于土豆的激活，只要僵尸出现，就可激活
                if (this.grid.hasZombie()) this.type = POTATO_ACTIVATED;
                break;
            case POTATO_ACTIVATED:
                potatoAttack();
                break;
            default:
                break;
        }
    }
    
    public void peaAttack() {
        for (int i = this.grid.y; i < Board.COL; i ++) {
            Grid g = Board.getGrid(this.grid.x, i);
            if (g.hasZombie()) {
                g.zombiesBeAttacked(atk);
                return;
            }
        }
    }
    
    public void potatoAttack() {
        
        int x = grid.x;
        int y = grid.y;
        
        int beginX = x-1 < 0 ? x : x-1;
        int beginY = y-1 < 0 ? y : y-1;
        int endX = x+1 >= Board.ROW ? x : x+1;
        int endY = y+1 >= Board.COL ? y : y+1;
        
        for (int i = beginX; i <= endX; i ++) {
            for (int j = beginY; j <= endY; j ++) {
                Grid g = Board.getGrid(i, j);
                if (g.hasZombie()) {
                    g.zombiesBeAttacked(atk);
                }
            }
        }
        
        this.grid.removePlant();
        Game.plantCnt--;
    }
}
