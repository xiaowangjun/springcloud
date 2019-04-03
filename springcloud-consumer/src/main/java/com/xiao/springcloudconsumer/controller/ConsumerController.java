package com.xiao.springcloudconsumer.controller;

import com.xiao.springcloudconsumer.service.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private HelloRemote helloRemote;

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.POST)
    public String index(@PathVariable("name") String name) {
        return helloRemote.hello(name);    }
}
