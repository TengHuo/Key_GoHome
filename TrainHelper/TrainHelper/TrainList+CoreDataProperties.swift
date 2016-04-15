//
//  TrainList+CoreDataProperties.swift
//  TrainHelper
//
//  Created by Teng on 4/15/16.
//  Copyright © 2016 huoteng. All rights reserved.
//
//  Choose "Create NSManagedObject Subclass…" from the Core Data editor menu
//  to delete and recreate this implementation file for your updated model.
//

import Foundation
import CoreData

extension TrainList {

    @NSManaged var fromStation: String?
    @NSManaged var toStation: String?
    @NSManaged var trainNum: String?
    @NSManaged var trainCode: String?

}
