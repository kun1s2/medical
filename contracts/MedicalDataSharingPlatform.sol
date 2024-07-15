contract MedicalDataSharingPlatform {
    
    //用于存储患者的个人信息
    struct Patient {
        string name; // 姓名
        string gender; // 性别
        uint age; // 年龄
        address accountAddress; // 账户地址
    }
    
    //将患者的ID映射到其个人信息
    mapping (string => Patient) patients;
    
    // 允许患者使用其身份证号和个人信息进行注册
    function register(string memory _id, string memory _name, string memory _gender, uint _age) public {
        // 将患者的个人信息存储在映射中
        patients[_id] = Patient(_name, _gender, _age, msg.sender);
    }
    
    /***********检索个人信息接口开发 **********/
    function getPatientInfo(string memory _id) public view returns (string memory, string memory, uint) {
            // 从映射中检索患者的个人信息
            Patient memory patient = patients[_id];
            // 返回患者的个人信息
            return (patient.name, patient.gender, patient.age);
    }


    
    /*********** 信息管理接口开发 **********/
    function makeAppointment(string memory _id, string memory _hospital, string memory _department) public {
        // 从映射中检索患者的个人信息
        Patient memory patient = patients[_id];
        // 将预约信息显示给患者
        string memory appointmentInfo = string(abi.encodePacked("You have made an appointment with ", _hospital, " ", _department));
        // 将预约信息发送到患者的账户地址
        bytes memory appointmentData = bytes(appointmentInfo);
        (bool success, ) = patient.accountAddress.call(appointmentData);
        require(success, "Failed to send appointment information");
    }    
}
