package com.github.yanglikun.securitydemo.controller;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

/**
 * 编码也可以用ESAPI的
 */
@Controller
@RequestMapping("/xss")
public class XssController {

    /**
     * 直接写脚本
     * <script>alert('/xss/')</script>
     * @param model
     * @return
     */
    @RequestMapping("")
    public String xss(Model model) {
        return "xss/xss";
    }


    @RequestMapping("/htmlTag")
    public String htmlTag(String userName, Model model) {
        model.addAttribute("userName", userName);
        //防御：编码html，比如用spring的HtmlUtils.htmlEscape
        //model.addAttribute("userName", HtmlUtils.htmlEscape(userName));
        return "xss/htmlTag";
    }

    /**
     * 闭合标签，比如用
     * "><script>alert('xss')</script><"
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping("/htmlAttr")
    public String htmlAttr(String userName, Model model) {
        model.addAttribute("userName", userName);
        //防御：编码html，比如用spring的HtmlUtils.htmlEscape
        //model.addAttribute("userName", HtmlUtils.htmlEscape(userName));
        return "xss/htmlAttr";
    }

    /**
     * 闭合引号
     * ";alert('/xss');//
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping("/script")
    public String script(String userName, Model model){
        model.addAttribute("userName", userName);
        //防御,使用JavaScriptUtils.javaScriptEscape,如果用htmlEscape呢？
        //model.addAttribute("userName", JavaScriptUtils.javaScriptEscape(userName));
        return "xss/script";
    }


    /**
     * 闭合
     * ');alert('/xss
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping("/event")
    public String event(String userName, Model model){
        model.addAttribute("userName", userName);
        //防御,使用JavaScriptUtils.javaScriptEscape,如果用htmlEscape呢？
        model.addAttribute("userName", JavaScriptUtils.javaScriptEscape(userName));
        return "xss/event";
    }
    /**
     * 闭合
     *
     * @param css
     * @param model
     * @return
     */
    @RequestMapping("/css")
    public String css(String css, Model model){
        model.addAttribute("css", css);
        //防御,使用JavaScriptUtils.javaScriptEscape,如果用htmlEscape呢？
        //model.addAttribute("css", css);
        return "xss/css";
    }

    /**
     * 比较不同的escape
     * @param args
     */
    public static void main(String[] args) {
        String input = "\";alert('/xss');//";
        System.out.println("--javascript--");
        System.out.println(JavaScriptUtils.javaScriptEscape(input));
        System.out.println(ESAPI.encoder().encodeForJavaScript(input));
        System.out.println("--html--");
        System.out.println(HtmlUtils.htmlEscape(input));
        System.out.println(ESAPI.encoder().encodeForHTML(input));
    }

}
