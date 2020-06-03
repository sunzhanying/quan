package com.jeesite.API.weixin.bean.material;


import com.jeesite.API.weixin.bean.message.Article;

import java.util.List;

public class NewsItem {

    private List<Article> news_item;

    public List<Article> getNews_item() {
        return news_item;
    }

    public void setNews_item(List<Article> news_item) {
        this.news_item = news_item;
    }


}
