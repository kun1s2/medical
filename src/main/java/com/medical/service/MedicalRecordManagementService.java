package com.medical.service;

import com.medical.model.bo.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@NoArgsConstructor
@Data
public class MedicalRecordManagementService {
  public static final String ABI = com.medical.utils.IOUtil.readResourceAsString("abi/MedicalRecordManagement.abi");

  public static final String BINARY = com.medical.utils.IOUtil.readResourceAsString("bin/ecc/MedicalRecordManagement.bin");


  @Value("${system.contract.medicalRecordManagementAddress}")
  private String address;

  @Autowired
  private Client client;

  AssembleTransactionProcessor txProcessor;

  @PostConstruct
  public void init() throws Exception {
    this.txProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(this.client, this.client.getCryptoSuite().getCryptoKeyPair());
  }

  public TransactionResponse newReservationInfo(MedicalRecordManagementNewReservationInfoInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "newReservationInfo", input.toArgs());
  }

  public TransactionResponse endVistis(MedicalRecordManagementEndVistisInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "endVistis", input.toArgs());
  }

  public CallResponse getReservationByName(MedicalRecordManagementGetReservationByNameInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getReservationByName", input.toArgs());
  }

  public CallResponse resAll(MedicalRecordManagementResAllInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "resAll", input.toArgs());
  }

  public TransactionResponse newMedicalRecord(MedicalRecordManagementNewMedicalRecordInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "newMedicalRecord", input.toArgs());
  }

  public TransactionResponse closeSeekMedical(MedicalRecordManagementCloseSeekMedicalInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "closeSeekMedical", input.toArgs());
  }

  public CallResponse getPatientAllMedical(String address) throws Exception {
    return this.txProcessor.sendCall(address, this.address, ABI, "getPatientAllMedical", Arrays.asList());
  }

  public TransactionResponse register(MedicalRecordManagementRegisterInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "register", input.toArgs());
  }

  public TransactionResponse queryMedicalRecord(MedicalRecordManagementQueryedicalRecordInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "queryMedicalRecord", input.toArgs());
  }

  public CallResponse getDoctorInfo(MedicalRecordManagementGetDoctorInfoInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getDoctorInfo", input.toArgs());
  }

  public TransactionResponse patientReservation(MedicalRecordManagementPatientReservationInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "patientReservation", input.toArgs());
  }

  public CallResponse getPatientReservationInfo(String address) throws Exception {
    return this.txProcessor.sendCall(address, this.address, ABI, "getPatientReservationInfo", Arrays.asList());
  }
  public CallResponse getReservationPatient(MedicalRecordManagementGetReservationPatientInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getReservationPatient", input.toArgs());
  }

  public CallResponse getPatientInfo(MedicalRecordManagementGetPatientInfoInputBO input) throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getPatientInfo", input.toArgs());
  }

  public CallResponse getDoctorAllReservation(String address) throws Exception {
    return this.txProcessor.sendCall(address, this.address, ABI, "getDoctorAllReservation", Arrays.asList());
  }

  public TransactionResponse registerDoctor(MedicalRecordManagementRegisterDoctorInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "registerDoctor", input.toArgs());
  }

  public CallResponse getReservationAllInfo() throws Exception {
    return this.txProcessor.sendCall(this.client.getCryptoSuite().getCryptoKeyPair().getAddress(), this.address, ABI, "getReservationAllInfo", Arrays.asList());
  }

  public TransactionResponse startSeekMedical(MedicalRecordManagementStartSeekMedicalInputBO input) throws Exception {
    return this.txProcessor.sendTransactionAndGetResponse(this.address, ABI, "startSeekMedical", input.toArgs());
  }
}
