package com.radien;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        builder.append(val);
        builder.append(",");
        ListNode node = next;
        while (node != null){
            builder.append(node.val);
            node = node.next;
            if (node != null){
                builder.append(",");
            }
        }
        return builder.append("]").toString();
    }
}
