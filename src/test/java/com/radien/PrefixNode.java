package com.radien;

import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 21:09 2022/2/10
 * @Modified By:
 */
public class PrefixNode {

    private int passCount;
    private int endCount;
    private Map<Character, PrefixNode> branch;

    public PrefixNode(){
        this.branch = new HashMap<>();
    }

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

    public int getEndCount() {
        return endCount;
    }

    public void setEndCount(int endCount) {
        this.endCount = endCount;
    }

    public void addStr(String str){
        if (StringUtils.isBlank(str)){
            return;
        }
        char[] chars = str.toCharArray();
        PrefixNode node = this;
        for (Character c : chars){
            node.passCount += 1;
            PrefixNode prefixNode = node.branch.get(c);
            if (prefixNode != null){
                node = prefixNode;
            }else {
                prefixNode = new PrefixNode();
                node.branch.put(c, prefixNode);
                node = prefixNode;
            }
        }
        node.passCount += 1;
        node.endCount += 1;
    }

    public boolean queryStr(String str){
        if (StringUtils.isBlank(str)){
            return false;
        }else {
            PrefixNode node = this;
            char[] chars = str.toCharArray();
            for (Character c : chars){
                node.passCount += 1;
                PrefixNode prefixNode = node.branch.get(c);
                if (prefixNode != null){
                    node = prefixNode;
                }else {
                    return false;
                }
            }
            return true;
        }
    }

    public int countStr(String str){
        if (StringUtils.isBlank(str)){
            return 0;
        }else {
            PrefixNode node = this;
            char[] chars = str.toCharArray();
            for (Character c : chars){
                node.passCount += 1;
                PrefixNode prefixNode = node.branch.get(c);
                if (prefixNode != null){
                    node = prefixNode;
                }else {
                    return 0;
                }
            }
            return node.endCount;
        }
    }
}
