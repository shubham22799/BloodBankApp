package com.example.bloodbankapp.data

object SampleData{

    fun bloodBankData(): ArrayList<BloodBank>{
        val data = ArrayList<BloodBank>()
        data.add(BloodBank("Seven Hills Health Care Private Ltd Hospital blood center", 4, 30, 40, 55, 70, 20, 50, 60, 33 ))
        data.add(BloodBank("Rotary DG Goenkar blood bank", 3, 30, 40, 55, 70, 20, 50, 60, 33))
        data.add(BloodBank("Kokilaben Dhirubhai Ambani bloodbankter", 2, 30, 40, 55, 70, 20, 50, 60, 33))
        data.add(BloodBank("Manas blood bank", 5, 30, 40, 55, 70, 20, 50, 60, 33))
        return data
    }

    fun ambulanceData(): ArrayList<AmbulanceData>{
        val data = ArrayList<AmbulanceData>()
        data.add(AmbulanceData("Metro Ambulance",5 , 24/7,9321377888 ))
        data.add(AmbulanceData("Medicare Ambulance",4 , 24/7,919820435177 ))
        data.add(AmbulanceData("SRTC Ambulance",2 , 24/7,8181938938 ))
        data.add(AmbulanceData("Cabulance Ambulance",3 , 24/7,919869398730 ))
        data.add(AmbulanceData("Prachi Ambulance",3 , 24/7,9321377888 ))
        return data
    }

    fun bloodGroupSpinner(): ArrayList<String> {
        val data = arrayListOf<String>()
        data.add("A+")
        data.add("A-")
        data.add("B+")
        data.add("B-")
        data.add("O+")
        data.add("O-")
        data.add("AB+")
        data.add("AB-")
        return data
    }

    fun unitSpinner(): ArrayList<Int> {
        val data = arrayListOf<Int>()
        data.add(1)
        data.add(2)
        data.add(3)
        data.add(4)
        data.add(5)
        return data
    }

}
