package com.jeesite.API.weixin.bean.menu;


import com.jeesite.API.weixin.bean.BaseResult;

import java.util.List;


public class Menu extends BaseResult {

    private MenuButtons menu;

    private List<MenuButtons> conditionalmenu;

    public MenuButtons getMenu() {
        return menu;
    }

    public void setMenu(MenuButtons menu) {
        this.menu = menu;
    }

    public List<MenuButtons> getConditionalmenu() {
        return conditionalmenu;
    }

    public void setConditionalmenu(List<MenuButtons> conditionalmenu) {
        this.conditionalmenu = conditionalmenu;
    }

}
