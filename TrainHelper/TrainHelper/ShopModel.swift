//
//  ShopModel.swift
//  TrainHelper
//
//  Created by Teng on 4/14/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON
import CoreData

class ShopModel {
    
    let dataController = DataController()
    let appkey = ["apikey": "8f871cc446037b7f6af24d23ba411358"]
    
    func getCityIdAndStore() {
        
        
        
        Alamofire.request(.GET, "http://apis.baidu.com/baidunuomi/openapi/cities", parameters: nil, encoding: .URL, headers: appkey)
            .responseJSON { response in
//                print(response.request)  // 请求对象
//                print(response.response) // 响应对象
//                print(response.data)     // 服务端返回的数据
                
                if let data = response.result.value {
                    print("JSON: \(data)")
                    let jsonData = JSON(data)
                    let cityList = jsonData["cities"].array!
                    let list = cityList.map({ (city) -> City in
                        let name = city["city_name"].string!
                        let id = city["city_id"].int!
                        
                        let newCity = City()
                        newCity.name = name
                        newCity.id = "\(id)"
                        return newCity
                    })
                    
                    print(list)
//                    do {
//                        try self.dataController.storeCityList(list)
//                    } catch {
//                        print("Store city error")
//                    }
                }
        }
    }
    
    func getShopInfo(cityId:String, resultHandler:() -> ()) {
        
        let parameter = ["city_id": cityId]
        
        Alamofire.request(.GET, "http://apis.baidu.com/baidunuomi/openapi/searchdeals", parameters: parameter, encoding: .URL, headers: appkey)
            .responseJSON { response in
                print(response.request)  // 请求对象
                print(response.response) // 响应对象
                print(response.data)     // 服务端返回的数据
                
                if let data = response.result.value {
                    print("shop detail:\(data)")
                }
        }
    }
}