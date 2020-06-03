package com.jeesite.modules.bright.content.web.meterail;

import com.jeesite.modules.bright.setfocus.entity.others.salesroom.Salesroom;

import java.util.Comparator;

public class sortByDefault implements Comparator {

    public int compare(Object o1, Object o2) {
        int a =Integer.parseInt(((Salesroom)o1).getIsdefault());
        int b =Integer.parseInt(((Salesroom)o2).getIsdefault());

        if(a>b)
            return -1;
        return 1;
    }

}