package com.medical.controller.doctor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.medical.mapper.UserMapper;
import com.medical.model.CommonResponse;
import com.medical.model.bo.MedicalRecordManagementCloseSeekMedicalInputBO;
import com.medical.model.bo.MedicalRecordManagementGetReservationPatientInputBO;
import com.medical.model.bo.MedicalRecordManagementNewMedicalRecordInputBO;
import com.medical.model.bo.MedicalRecordManagementStartSeekMedicalInputBO;
import com.medical.model.entity.User;
import com.medical.model.exception.BaseException;
import com.medical.service.MedicalRecordManagementService;
import com.medical.utils.BaseContext;
import lombok.RequiredArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController("doctorConsultationController")
@RequestMapping("/doctor/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final MedicalRecordManagementService medicalRecordManagementService;
    private final UserMapper userMapper;
    @PostMapping("/start")
    public CommonResponse startConsultation(@RequestBody JSONObject params) {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        String privateKey = user.getAccountAddress().substring(2);
        Client client = medicalRecordManagementService.getClient();
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(privateKey));
        try {
            int returnCode = medicalRecordManagementService.startSeekMedical(new MedicalRecordManagementStartSeekMedicalInputBO(params.getString("patientId"), params.getBigInteger("id"))).getReturnCode();
            if (returnCode != 0){
                return CommonResponse.fail("开始就诊失败");
            }
        } catch (Exception e) {
            throw new BaseException("开始就诊失败");
        }
        return CommonResponse.ok(null);
    }

    @PostMapping("/end")
    public CommonResponse endConsultation(@RequestBody JSONObject params) {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        String privateKey = user.getAccountAddress().substring(2);
        Client client = medicalRecordManagementService.getClient();
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(privateKey));
        try {
            int returnCode = medicalRecordManagementService.closeSeekMedical(new MedicalRecordManagementCloseSeekMedicalInputBO(params.getString("patientId"), params.getBigInteger("id"))).getReturnCode();
            if (returnCode != 0){
                return CommonResponse.fail("结束就诊失败");
            }
        } catch (Exception e) {
            throw new BaseException("结束就诊失败");
        }
        return CommonResponse.ok(null);
    }


    @PostMapping("/newMedicalRecord")
    public CommonResponse newMedicalRecord(@RequestBody JSONObject params) {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        String privateKey = user.getAccountAddress().substring(2);
        try {
            Client client = medicalRecordManagementService.getClient();
            client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(privateKey));
            MedicalRecordManagementNewMedicalRecordInputBO medicalRecordManagementNewMedicalRecordInputBO = new MedicalRecordManagementNewMedicalRecordInputBO();
            medicalRecordManagementNewMedicalRecordInputBO.setPatientID(params.getString("patientId"));
            medicalRecordManagementNewMedicalRecordInputBO.setCurrentMedicalHistory(params.getString("currentMedicalHistory"));
            medicalRecordManagementNewMedicalRecordInputBO.setDepartment(params.getString("department"));
            medicalRecordManagementNewMedicalRecordInputBO.setPastMedicalHistory(params.getString("pastMedicalHistory"));
            medicalRecordManagementNewMedicalRecordInputBO.setDoctorName(params.getString("name"));
            medicalRecordManagementNewMedicalRecordInputBO.setRegistrationInfo(params.getString("registrationInfo"));
            int returnCode = medicalRecordManagementService.newMedicalRecord(medicalRecordManagementNewMedicalRecordInputBO).getReturnCode();
            if (returnCode != 0){
                return CommonResponse.fail("新建病历失败");
            }
        } catch (Exception e) {
            throw new BaseException("新建病历失败");
        }
        return CommonResponse.ok(null);
    }

    @GetMapping("/reservationPatient")
    public CommonResponse getReservationPatient() {
        String currentId = BaseContext.getCurrentId();
        User doctor = userMapper.selectById(currentId);
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getRole, 1));
        List<String> patientIds = users.stream().map(User::getAccountAddress).collect(Collectors.toList());
        try {
            String values = medicalRecordManagementService.getReservationPatient(new MedicalRecordManagementGetReservationPatientInputBO(patientIds, doctor.getAccountAddress())).getValues();
        } catch (Exception e) {
            throw new BaseException("获取预约病人失败");
        }
        return CommonResponse.ok(patientIds);
    }

}
