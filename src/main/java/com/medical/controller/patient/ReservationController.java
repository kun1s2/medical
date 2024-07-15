package com.medical.controller.patient;

import com.medical.mapper.UserMapper;
import com.medical.model.CommonResponse;
import com.medical.model.bo.MedicalRecordManagementEndVistisInputBO;
import com.medical.model.bo.MedicalRecordManagementGetReservationByNameInputBO;
import com.medical.model.bo.MedicalRecordManagementPatientReservationInputBO;
import com.medical.model.entity.User;
import com.medical.model.exception.BaseException;
import com.medical.service.MedicalRecordManagementService;
import com.medical.utils.BaseContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController("patientReservationController")
@RequestMapping("/patient/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final MedicalRecordManagementService medicalRecordManagementService;
    private final UserMapper userMapper;
    @GetMapping("/list")
    public CommonResponse listAllReservations(@RequestParam String deptName) {
        String values;
        try {
            if (StringUtils.isEmpty(deptName)) {
                values = medicalRecordManagementService.getReservationAllInfo().getValues();
                System.out.println(values);
            }else {
                values = medicalRecordManagementService.getReservationByName(new MedicalRecordManagementGetReservationByNameInputBO(deptName)).getValues();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return CommonResponse.ok(values);
    }

    @PostMapping("/reserve")
    public CommonResponse reserve(@RequestParam BigInteger id) {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        String privateKey = user.getAccountAddress().substring(2);
        Client client = medicalRecordManagementService.getClient();
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(privateKey));
        try {
            int returnCode = medicalRecordManagementService.patientReservation(new MedicalRecordManagementPatientReservationInputBO(id)).getReturnCode();
            if (returnCode != 0) {
                return CommonResponse.fail("预约失败");
            }
        } catch (Exception e) {
            throw new BaseException("预约失败");
        }
        return CommonResponse.ok(null);
    }

    @PostMapping("/endVisit")
    public CommonResponse endVisit(@RequestParam BigInteger id) {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        String privateKey = user.getAccountAddress().substring(2);
        Client client = medicalRecordManagementService.getClient();
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(privateKey));
        try {
            int returnCode = medicalRecordManagementService.endVistis(new MedicalRecordManagementEndVistisInputBO(id)).getReturnCode();
            if (returnCode != 0) {
                return CommonResponse.fail("取消预约失败");
            }
        } catch (Exception e) {
            throw new BaseException("取消预约失败");
        }
        return CommonResponse.ok(null);
    }

    @GetMapping("/list/own/reservation")
    public CommonResponse listOwnReservations() {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        try {
            String values = medicalRecordManagementService.getPatientReservationInfo(user.getAccountAddress()).getValues();
            System.out.println(values);
            return CommonResponse.ok(values);
        } catch (Exception e) {
            throw new BaseException("获取自己的预约信息失败");
        }
    }
}
