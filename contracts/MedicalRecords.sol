pragma solidity ^0.4.22;
contract MedicalRecords {
    
    // 定义一个结构体，用于存储病人信息
    struct Patient {
        string name; // 病人姓名
        uint age; // 病人年龄
        string personalInfo; // 病人个人信息
        string medicalHistory; // 病人病史
        string medicalContent; // 病人病情描述
    }
    
    //将病人地址映射到病人信息
    mapping(address => Patient) public patients;
    
    // 添加病人信息
    function addPatient(string memory _name, uint _age, string memory _personalInfo, string memory _medicalHistory, string memory _medicalContent) public {
        patients[msg.sender] = Patient(_name, _age, _personalInfo, _medicalHistory, _medicalContent);
    }
    
    /*********** 查看病人个人信息接口 **********/
   function getPersonalInfo() public view returns (string memory) {
        return patients[msg.sender].personalInfo;
   }
    
    // 获取病人病史
    function getMedicalHistory() public view returns (string memory) {
        return patients[msg.sender].medicalHistory;
    }
    
    /*********** 查看病人病情描述接口 **********/
    function getMedicalContent() public view returns (string memory) {
        return patients[msg.sender].medicalContent;
    }
}
