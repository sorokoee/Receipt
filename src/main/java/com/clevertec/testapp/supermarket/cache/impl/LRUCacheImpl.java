package com.clevertec.testapp.supermarket.cache.impl;

import com.clevertec.testapp.supermarket.cache.Cache;
import com.clevertec.testapp.supermarket.entity.BaseModel;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheImpl implements Cache {
    private Map<Long, CasheNode> casheNodeMap;
    private CasheNode head, tail;
    private int capacity;
    private int size;

    public LRUCacheImpl(int capacity) {
        this.capacity = capacity;
        casheNodeMap = new HashMap<>(capacity);
        head = new CasheNode();
        tail = new CasheNode();
        head.next = tail;
        tail.prev = head;

    }

    @Override
    public BaseModel get(Long key) {
        CasheNode casheNode = casheNodeMap.get(key);
        if (casheNode == null) {
            return null;
        }
        remove(casheNode);
        moveToHead(casheNode);
        return casheNode.value;
    }

    @Override
    public void put(BaseModel obj) {
        CasheNode node = casheNodeMap.get(obj.getId());
        if (node != null) {
            node.value = obj;
            remove(node);
            moveToHead(node);

        } else {
            node = new CasheNode();
            node.value = obj;
            node.key = obj.getId();
            casheNodeMap.put(obj.getId(), node);
            moveToHead(node);

        }
        if (size > capacity) {

            casheNodeMap.remove(tail.prev.key);
            remove(tail.prev);

        }

    }

    private void remove(CasheNode casheNode) {

        casheNode.prev.next = casheNode.next;
        casheNode.next.prev = casheNode.prev;
        size--;
    }

    private void moveToHead(CasheNode casheNode) {
        CasheNode node = head.next;
        head.next = casheNode;
        casheNode.prev = head;
        casheNode.next = node;
        node.prev = casheNode;
        size++;
    }

    private class CasheNode {
        BaseModel value;
        Long key;
        CasheNode prev;
        CasheNode next;
    }
}
