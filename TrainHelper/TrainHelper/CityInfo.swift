//
//  CityInfo.swift
//  TrainHelper
//
//  Created by Teng on 4/16/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import Foundation

class CityInfo {
    var name: String
    var id: String
    
    init (name:String, id:Int) {
        self.name = name
        self.id = "\(id)"
    }
}