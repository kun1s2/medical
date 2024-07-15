package com.medical.controller.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("patientUserController")
@RequestMapping("/patient/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

}
