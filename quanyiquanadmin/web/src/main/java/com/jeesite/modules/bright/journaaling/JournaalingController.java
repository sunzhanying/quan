package com.jeesite.modules.bright.journaaling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 报表
 */
@Controller
@RequestMapping(value = "${adminPath}/journaaling/journaaling")
public class JournaalingController {

    //
    @RequestMapping(value = "journaaling")
    public String journaaling() {
        return "modules/bright/journaaling/journaaling";
    }

    @RequestMapping(value = "journaaling2")
    public String journaaling2() {
        return "modules/bright/journaaling/journaaling2";
    }

    @RequestMapping(value = "journaaling3")
    public String journaaling3() {
        return "modules/bright/journaaling/journaaling3";
    }

    @RequestMapping(value = "journaaling4")
    public String journaaling4() {
        return "modules/bright/journaaling/journaaling4";
    }
}
