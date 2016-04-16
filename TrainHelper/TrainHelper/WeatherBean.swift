//
//  WeatherBean.swift
//  TrainHelper
//
//  Created by Teng on 4/16/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import Foundation


class WeatherBean {
    var condition:Int
    var description:String
    var temperatureMin:Double
    var temperatureMax:Double
    
    init(condi: String, desc: String, min: String, max: String) {
        condition = Int(condi)!
        description = desc
        temperatureMax = Double(max)! - 273.15
        temperatureMin = Double(min)! - 273.15
    }
}