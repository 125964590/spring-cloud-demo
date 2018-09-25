package top.jbzm.cloud.jwtsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

/**
 * @author jbzm
 * @date 2018上午1:26
 **/
@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping("test")
    public ResponseEntity test(){

        return ResponseEntity.ok("lol");
    }
}