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
                    let jsonData = JSON(data)
                    let cityList = jsonData["cities"].array!
                    let list = cityList.map({ (city) -> CityInfo in
                        let name = city["city_name"].string!
                        let id = city["city_id"].int!
                        
                        let newCity = CityInfo(name: name, id: id)

                        return newCity
                    })
                    
//                    list.forEach {
//                        print("name:\($0.name)")
//                        print("id:\($0.id)")
//                    }
                    do {
                        try self.dataController.storeCityList(list)
                    } catch {
                        print("Store city error")
                    }
                }
        }
    }
    
    func getShopInfoFromRemote(cityId:String, page:Int, resultHandler:([ShopBean]?) -> ()) {
        
        let parameter = ["city_id": cityId, "keyword": "美食", "page": page]
        
        Alamofire.request(.GET, "http://apis.baidu.com/baidunuomi/openapi/searchdeals", parameters: parameter as? [String : AnyObject], encoding: .URL, headers: appkey)
            .responseJSON { response in
                if let data = response.result.value {
                    let jsonData = JSON(data)
                    print("shop detail:\(jsonData)")
                    
                    let deals = jsonData["data"]["deals"].array!
                    
                    let titleList = deals.map {
                        ShopBean(name: $0["title"].string!, detail: $0["description"].string!)
                    }
                    
                    resultHandler(titleList)
                }
        }
    }
}