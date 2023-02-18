package com.radien;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 21:10 2022/2/10
 * @Modified By:
 */
public class PrefixHeader {

    private PrefixNode node;

    public void addStr(String str){
        if (node == null){
            node = new PrefixNode();
        }
        node.addStr(str);
    }


    public boolean queryStr(String str){
        if (node == null){
            return false;
        }
        return node.queryStr(str);
    }

    public int countStr(String str){
        if (node == null){
            return 0;
        }
        return node.countStr(str);
    }
}
