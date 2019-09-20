package com.bigfour.odp.api.controller;

import com.bigfour.odp.api.security.OdpUserDetails;
import com.bigfour.odp.api.security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zhujiwu
 * @date : 2019/9/20.
 */
@RestController
@RequestMapping("/passport")
public class PassportController {
    @GetMapping("/info")
    public OdpUserDetails info() {
        OdpUserDetails userDetails = SecurityUtils.getUserDetails();
        return userDetails;
    }
}
