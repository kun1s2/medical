package com.medical.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medical.mapper.UserMapper;
import com.medical.model.bo.MedicalRecordManagementGetDoctorInfoInputBO;
import com.medical.model.bo.MedicalRecordManagementGetPatientInfoInputBO;
import com.medical.model.bo.MedicalRecordManagementRegisterDoctorInputBO;
import com.medical.model.bo.MedicalRecordManagementRegisterInputBO;
import com.medical.model.entity.User;
import com.medical.model.exception.BaseException;
import com.medical.raw.MedicalRecordManagement;
import com.medical.service.MedicalRecordManagementService;
import com.medical.service.UserService;
import com.medical.utils.BaseContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService
{
    private final MedicalRecordManagementService medicalRecordManagementService;
    @Value("${system.contract.medicalRecordManagementAddress}")
    private String address;

    @Autowired
    private Client client;


    @Override
    public Map<String, Object> login(JSONObject loginParams)
    {
        String account = loginParams.getString("account");
        User user = this.getById(account);
        if (user == null)
        {
            throw new BaseException("用户不存在");
        }
        String password = loginParams.getString("password");
        if (!user.getPassword().equals(password))
        {
            throw new BaseException("密码错误");
        }
        String values;
        try
        {
            values = medicalRecordManagementService.getDoctorInfo(new MedicalRecordManagementGetDoctorInfoInputBO(user.getAccountAddress())).getValues();
        } catch (Exception e)
        {
            throw new BaseException("获取医生信息失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("account", user.getAccount());
        map.put("accountAddress", user.getAccountAddress());
        map.put("name", values);
        map.put("role", user.getRole());
        return map;

    }

    @Override
    public void register(JSONObject registerParams)
    {
        String account = registerParams.getString("account");
        User user = this.getById(account);
        if (user != null)
        {
            throw new BaseException("用户已存在");
        }
        user = new User();
        user.setAccount(account);
        user.setPassword(registerParams.getString("password"));
        user.setAccountAddress(registerParams.getString("blockchainAccount"));
        try
        {
            TransactionResponse register;
            if (registerParams.getString("deptName") != null)
            {
                user.setRole(0);
                MedicalRecordManagementRegisterDoctorInputBO medicalRecordManagementRegisterInputBO =
                        new MedicalRecordManagementRegisterDoctorInputBO(
                                user.getAccountAddress(),
                                registerParams.getString("name"),
                                registerParams.getString("sex"),
                                registerParams.getBigInteger("age"),
                                registerParams.getString("deptName"));
                //方式一
                register = medicalRecordManagementService.registerDoctor(medicalRecordManagementRegisterInputBO);
                MedicalRecordManagement raw = MedicalRecordManagement.load(address, client, this.client.getCryptoSuite().getCryptoKeyPair());
                //方式二
//                TransactionReceipt transactionReceipt = raw.registerDoctor(
//                        medicalRecordManagementRegisterInputBO.getAccount(),
//                        medicalRecordManagementRegisterInputBO.getDoctorName(),
//                        medicalRecordManagementRegisterInputBO.getGender(),
//                        medicalRecordManagementRegisterInputBO.getAge(),
//                        medicalRecordManagementRegisterInputBO.getDeptName());

            }
            else
            {
                user.setRole(1);
                MedicalRecordManagementRegisterInputBO medicalRecordManagementRegisterInputBO =
                        new MedicalRecordManagementRegisterInputBO(user.getAccountAddress(),
                                registerParams.getString("name"),
                                registerParams.getString("sex"),
                                registerParams.getBigInteger("age"));
                register = medicalRecordManagementService.register(medicalRecordManagementRegisterInputBO);
            }
            if (register.getReturnCode() != 0)
            {
                throw new BaseException("注册失败");
            }
            this.save(user);
        } catch (Exception e)
        {
            throw new BaseException("注册失败");
        }
    }

    @Override
    public Map<String, Object> getUser()
    {
        String currentId = BaseContext.getCurrentId();
        User user = this.getById(currentId);
        Map<String, Object> map = new HashMap<>();
        try
        {
            String values;
            if (user.getRole() == 0)
            {
                values = medicalRecordManagementService.getDoctorInfo(new MedicalRecordManagementGetDoctorInfoInputBO(user.getAccountAddress())).getValues();
            }
            else
            {
                values = medicalRecordManagementService.getPatientInfo(new MedicalRecordManagementGetPatientInfoInputBO(user.getAccountAddress())).getValues();
            }
            map.put("account", user.getAccount());
            map.put("accountAddress", user.getAccountAddress());
            map.put("name", values);
        } catch (Exception e)
        {
            throw new BaseException("获取用户信息失败");
        }

        return map;
    }
}
