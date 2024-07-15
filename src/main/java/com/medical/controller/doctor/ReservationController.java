package com.medical.controller.doctor;

import com.alibaba.fastjson.JSONObject;
import com.medical.mapper.UserMapper;
import com.medical.model.CommonResponse;
import com.medical.model.bo.MedicalRecordManagementNewReservationInfoInputBO;
import com.medical.model.entity.User;
import com.medical.model.exception.BaseException;
import com.medical.service.MedicalRecordManagementService;
import com.medical.utils.BaseContext;
import lombok.RequiredArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.springframework.web.bind.annotation.*;

@RestController("doctorReservationController")
@RequestMapping("/doctor/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final MedicalRecordManagementService medicalRecordManagementService;
    private final UserMapper userMapper;
    @GetMapping("/list")
    public CommonResponse listReservation() {
        User user = userMapper.selectById(BaseContext.getCurrentId());
        String values;
        try {
            values = medicalRecordManagementService.getDoctorAllReservation(user.getAccountAddress()).getValues();
        } catch (Exception e) {
            throw new BaseException("获取预约列表失败");
        }
        return CommonResponse.ok(values);
    }

    @PostMapping
    public CommonResponse addReservation(@RequestBody JSONObject params) {
        String currentId = BaseContext.getCurrentId();
        User user = userMapper.selectById(currentId);
        String privateKey = user.getAccountAddress().substring(2);
        Client client = medicalRecordManagementService.getClient();
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(privateKey));
        try {
            int returnCode = medicalRecordManagementService.newReservationInfo(new MedicalRecordManagementNewReservationInfoInputBO(params.getString("time"), params.getString("pm"), params.getBigInteger("reservationTime"))).getReturnCode();
            if (returnCode != 0){
                return CommonResponse.fail("新增预约失败");
            }
        } catch (Exception e) {
            throw new BaseException("新增预约失败");
        }

        return CommonResponse.ok(null);
    }
}
