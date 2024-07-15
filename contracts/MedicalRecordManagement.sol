pragma solidity ^0.4.22;
contract MedicalRecordManagement {

    // 定义一个结构体用于存储病历
    struct MedicalRecord {
        string patientID; // 病人ID
        string hospitalName; // 医院名称
        string department; // 科室
        string doctorName; // 医生姓名
        string registrationInfo; // 信息管理信息
        string pastMedicalHistory; // 既往病史
        string currentMedicalHistory; // 现病史
        bool isFilled; // 是否填写完毕
    }

    // 定义一个映射用于存储病历
    mapping(string => MedicalRecord) medicalRecords;


    /*********** 新建病历接口 **********/
    function startConsultation(string memory patientID) public {
        // 检查病人是否有既往病史
        if (bytes(medicalRecords[patientID].pastMedicalHistory).length > 0) {
            // 授权医生D查看既往病史
            medicalRecords[patientID].isFilled = false;
        } else {
            // 如果没有既往病史，则创建一个新的病历
            medicalRecords[patientID] = MedicalRecord(patientID, "", "", "", "", "", "", false);
        }
    }

    // 定义一个函数，用于平台更新病历的信息管理和医生信息
    function updateMedicalRecord(string memory patientID, string memory hospitalName, string memory department, string memory doctorName, string memory registrationInfo) public {
        // 更新病历的信息管理和医生信息
        medicalRecords[patientID].hospitalName = hospitalName;
        medicalRecords[patientID].department = department;
        medicalRecords[patientID].doctorName = doctorName;
        medicalRecords[patientID].registrationInfo = registrationInfo;
    }

    // 定义一个函数，用于医生D填写病历
    function fillInMedicalHistory(string memory patientID, string memory pastMedicalHistory, string memory currentMedicalHistory) public {
        // 检查病历是否已经填写
        require(bytes(pastMedicalHistory).length > 0 && bytes(currentMedicalHistory).length > 0, "a");
        // 填写病历
        medicalRecords[patientID].pastMedicalHistory = pastMedicalHistory;
        medicalRecords[patientID].currentMedicalHistory = currentMedicalHistory;
        medicalRecords[patientID].isFilled = true;
    }

    /*********** 结束就诊接口 **********/
    function endConsultation(string memory patientID) public {
        // 检查病历是否已经填写
        require(medicalRecords[patientID].isFilled, "a");
        // 结束病历咨询
        medicalRecords[patientID].isFilled = false;
    }

    function getMedicalHistory(string memory patientID) public view returns (string memory, string memory) {
//        require(!medicalRecords[patientID].pastMedicalHistory != "", "medical records does not exists!");
        require(medicalRecords[patientID].isFilled, "Medical Record does not exist!");
        return (medicalRecords[patientID].pastMedicalHistory, medicalRecords[patientID].currentMedicalHistory);
    }
}
