//
//  WeatherModel.swift
//  TrainHelper
//
//  Created by Teng on 4/14/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import Foundation
import Alamofire
import SWXMLHash

class WeatherModel {
    func getWeatherInfo(cityName: String, resultHandler:() -> ()) {
        
        let parameter = ["q": "shanghai", "appid": "b1b15e88fa797225412429c1c50c122a", "mode": "xml"]
        
        Alamofire.request(.GET, "http://api.openweathermap.org/data/2.5/weather", parameters: parameter, encoding: .URL, headers: nil)
            .response { (request, Response, data, error) -> Void in
                let xml = SWXMLHash.parse(data!)
                print(xml["current"]["city"].description)
        }
    }
}