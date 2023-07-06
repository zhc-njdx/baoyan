package cppexam.memorymanagement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Memory {
    int[] mem;
    Map<Integer, Integer> obj2Addr;

    private static final int OCCUPIED = 1;
    private static final int FREE = 0;
    
    private static final int OFFSET = 3;
    private static final int UNIT = 8;
    private static final int INT_SIZE = 4;
    private static final int JUNK = 0xdfdfdfdf;
    private static final int MASK = 0xff;
    private static final int SIZE_MASK = 0x1fffffff;
    private static final int[] MOVE_BITS = new int[]{24, 16, 8, 0};

    private static final String ALLOC_SUCCESS_FORMAT = "succeeded to alloc object %d\n";
    private static final String ALLOC_FAIL_FORMAT = "failed to alloc object %d\n";
    private static final String FREE_SUCCESS_FORMAT = "succeeded to free object %d\n";
    private static final String FREE_FAIL_MESSAGE = "invalid memory access";
    
    private void initMemory() {
        Arrays.fill(mem, JUNK);
    }

    public Memory(int bytes) {
        int size = bytes / 4;
        mem = new int[size];
        initMemory();
        setFlag(0, size-1, FREE);
        obj2Addr = new HashMap<>();
    }

    private void setFlag(int start, int end, int occupied) {
        int freeUnit = (end - start - 1) / (UNIT / INT_SIZE);
        if (freeUnit < 0) return;
        mem[start] = mem[end] = (freeUnit << OFFSET) | occupied;
    }
    
    private int upperUnit(int size) {
        return ((size / UNIT) + (size % UNIT == 0 ? 0 : 1));
    }
    
    private int walk(int unit) {
        int i = 0;
        while (i < mem.length) {
            int occupied = mem[i] & 0x1;
            int memoryUnit = (mem[i] >> OFFSET) & SIZE_MASK;
            if (occupied == OCCUPIED
                    || memoryUnit < unit // 内存为空，但是小于需要的size
                ) {
                i = i + memoryUnit*(UNIT/INT_SIZE) + 2; // 指向下一个内存块的开始位置
            } else { // 找到了第一块满足要求的内存块
                int end = i + unit*(UNIT/INT_SIZE) + 1;
                setFlag(i, end, OCCUPIED);
                setFlag(end+1, i+memoryUnit*(UNIT/INT_SIZE)+1, FREE);
                return i;
            }
        }
        return -1;
    }

    public void alloc(int id, int bytes) {
        int unitShouldAlloc = upperUnit(bytes);
        int addr = walk(unitShouldAlloc);
        if (addr != -1) {
            obj2Addr.put(id, addr);
            System.out.format(ALLOC_SUCCESS_FORMAT, id);
        } else {
            System.out.format(ALLOC_FAIL_FORMAT, id);
        }
//        show();
    }

    public void free(int id) {
        if (obj2Addr.containsKey(id)) {
            int addr = obj2Addr.get(id);
            int memoryUnit = (mem[addr] >> OFFSET) & SIZE_MASK;
            setFlag(addr, addr+memoryUnit*(UNIT/INT_SIZE)+1, FREE);
            System.out.format(FREE_SUCCESS_FORMAT, id);
        } else {
            System.out.println(FREE_FAIL_MESSAGE);
        }
//        show();
    }

    public void show() {
        for (int i : mem) {
            for (int bit : MOVE_BITS) {
                System.out.format("%02x", (i >> bit) & MASK);
            }
            System.out.println();
        }
    }
}
