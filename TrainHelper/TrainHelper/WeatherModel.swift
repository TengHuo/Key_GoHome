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
    func getWeatherInfo(lat:Double, lon:Double, resultHandler:(WeatherBean?) -> ()) {
        
        let parameter = ["lat": lat, "lon": lon, "appid": "b1b15e88fa797225412429c1c50c122a", "mode": "xml"]
        
        Alamofire.request(.GET, "http://api.openweathermap.org/data/2.5/weather", parameters: parameter as? [String : AnyObject], encoding: .URL, headers: nil)
            .response { (request, response, data, error) -> Void in
                if let _ = data {
                    let xml = SWXMLHash.parse(data!)
                    print("xml:\(xml)")
                    let tempMax = xml["current"]["temperature"][0].element!.attributes["max"]!
                    let tempMin = xml["current"]["temperature"][0].element!.attributes["min"]!
                    
                    let condi = xml["current"]["weather"][0].element!.attributes["number"]!
                    let desc = xml["current"]["weather"][0].element!.attributes["value"]!
                    
                    let weather = WeatherBean(condi: condi, desc: desc, min: tempMin, max: tempMax)
                    resultHandler(weather)
                } else {
                    resultHandler(nil)
                }
        }
    }
}