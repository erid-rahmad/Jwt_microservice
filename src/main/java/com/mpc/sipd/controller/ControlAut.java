package com.mpc.sipd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;

@RestController
@RequestMapping("/sipd-bsb/sipd/api/transaction")

public class ControlAut {

    private static final Logger log = LoggerFactory.getLogger(ControlAut.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${url.inquiry}")
    private String urlinq;

    @Value("${url.checkStatus}")
    private String checkStatus;

    @Value("${url.trxHistory}")
    private String trxHistory;

    @Value("${url.overbooking}")
    private String overbooking;


    @PostMapping(value = "/inquiry")
    public Map<String,Object> getProductList1(@RequestBody Map<String,Object> json) {
        log.info("this logi");

        Map<String,Object> s =restTemplate.postForObject(urlinq,json, Map.class);

        return s;
    }

    @PostMapping(value = "/checkStatus")
    public Map<String,Object> getProductList2(@RequestBody Map<String,Object> json) {
        System.out.println(json);
        Map<String,Object> s =restTemplate.postForObject(checkStatus,json, Map.class);

        return s;
    }

    @PostMapping(value = "/trxHistory")
    public Map<String,Object> getProductList3(@RequestBody Map<String,Object> json) {
        System.out.println(json);
        Map<String,Object> s =restTemplate.postForObject(trxHistory,json, Map.class);

        return s;
    }

    @PostMapping(value = "/overbooking")
    public Map<String,Object> getProductList4(@RequestBody Map<String,Object> json) {
        System.out.println(json);
        Map<String,Object> s =restTemplate.postForObject(overbooking,json, Map.class);

        return s;
    }
}
