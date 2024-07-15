package com.medical.controller.patient;

import com.medical.mapper.UserMapper;
import com.medical.model.CommonResponse;
import com.medical.model.bo.MedicalRecordManagementQueryedicalRecordInputBO;
import com.medical.model.entity.User;
import com.medical.model.exception.BaseException;
import com.medical.service.MedicalRecordManagementService;
import com.medical.utils.BaseContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/patient/record")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordManagementService medicalRecordManagementService;
    private final UserMapper userMapper;

    @GetMapping("/list")
    public CommonResponse listAllMedicalRecord() {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        try {
            String values = medicalRecordManagementService.getPatientAllMedical(user.getAccountAddress()).getValues();
            return CommonResponse.ok(values);
        } catch (Exception e) {
            throw new BaseException("获取病历列表失败");
        }
    }

    @GetMapping("/detail")
    public CommonResponse getMedicalRecordDetail(@RequestParam BigInteger id) {
        try {
            String values = medicalRecordManagementService.queryMedicalRecord(new MedicalRecordManagementQueryedicalRecordInputBO(id)).getValues();
            return CommonResponse.ok(values);
        } catch (Exception e) {
            throw new BaseException("获取病历详情失败");
        }
    }
}
