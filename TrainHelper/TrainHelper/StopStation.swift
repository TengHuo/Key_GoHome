//
//  StopStation.swift
//  TrainHelper
//
//  Created by Teng on 4/15/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import Foundation

class StopStation {
    var startStationName: String?
    var arriveTime: String
    var stationName: String
    var trainClassName: String?
    var startTime: String
    var stopoverTime: String
    var endStationName: String?
    
    init (startStation: String?, arrive:String, station: String, trainClass: String?, startTime: String, stopover: String, endStation: String?) {
        self.startStationName = startStation
        self.arriveTime = arrive
        self.stationName = station
        self.trainClassName = trainClass
        self.startTime = startTime
        self.stopoverTime = stopover
        self.endStationName = endStation
    }

//    "data": [
//    {
//    "start_station_name": "北京南",
//    "arrive_time": "----",
//    "station_train_code": "D321",
//    "station_name": "北京南",
//    "train_class_name": "动车",
//    "service_type": "1",
//    "start_time": "21:23",
//    "stopover_time": "----",
//    "end_station_name": "上海",
//    "station_no": "01",
//    "isEnabled": true
//    },
//    {
//    "arrive_time": "06:33",
//    "station_name": "南京",
//    "start_time": "06:37",
//    "stopover_time": "4分钟",
//    "station_no": "02",
//    "isEnabled": true
//    },
//    {
//    "arrive_time": "08:02",
//    "station_name": "无锡",
//    "start_time": "08:04",
//    "stopover_time": "2分钟",
//    "station_no": "03",
//    "isEnabled": true
//    },
//    {
//    "arrive_time": "09:12",
//    "station_name": "上海",
//    "start_time": "09:12",
//    "stopover_time": "----",
//    "station_no": "04",
//    "isEnabled": true
//    }
//    ]

}