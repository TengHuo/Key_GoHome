//
//  Station+CoreDataProperties.swift
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

extension Station {

    @NSManaged var name: String?
    @NSManaged var code: String?

}
