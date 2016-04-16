//
//  City+CoreDataProperties.swift
//  TrainHelper
//
//  Created by Teng on 4/16/16.
//  Copyright © 2016 huoteng. All rights reserved.
//
//  Choose "Create NSManagedObject Subclass…" from the Core Data editor menu
//  to delete and recreate this implementation file for your updated model.
//

import Foundation
import CoreData

extension City {

    @NSManaged var name: String?
    @NSManaged var id: String?

}
