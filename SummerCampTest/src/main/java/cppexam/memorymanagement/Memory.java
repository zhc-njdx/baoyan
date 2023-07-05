package cppexam.memorymanagement;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    int[] mem;
    Map<Integer, Integer> Obj2Addr;

    private static final int OCCUPIED = 1;
    private static final int FREE = 0;

    private static final String ALLOC_SUCCESS_FORMAT = "succeeded to alloc object %d";
    private static final String ALLOC_FAIL_FORMAT = "failed to alloc object %d";
    private static final String FREE_SUCCESS_FORMAT = "succeeded to free object %d";
    private static final String FREE_FAIL_MESSAGE = "invalid memory access";

    public Memory(int size) {
        mem = new int[size];
        setFlag(0, size-1, FREE);
        Obj2Addr = new HashMap<>();
    }

    private void setFlag(int start, int end, int occupied) {
        int freeSize = end - start + 1;
        if (freeSize < 0) return;
        mem[start] = mem[end] = (freeSize << 3) + occupied;
    }

    public void alloc(int id, int size) {

    }

    public void free(int id) {

    }

    public void show() {

    }
}
