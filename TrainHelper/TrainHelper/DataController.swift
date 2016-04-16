//
//  DataModel.swift
//  TrainHelper
//
//  Created by Teng on 4/15/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import Foundation
import CoreData

class DataController: NSObject {
    var test = [Station]()
    let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
    var manageContext:NSManagedObjectContext
    
    override init() {
        manageContext = appDelegate.managedObjectContext
    }
    
    //Train list function
    func storeTrainList(trainList: [(String, String, String, String)]) throws {
        trainList.forEach { (code, start, end, number) -> () in
            let newTrain = NSEntityDescription.insertNewObjectForEntityForName("TrainList", inManagedObjectContext: manageContext)
            newTrain.setValue(code, forKey: "trainCode")
            newTrain.setValue(start, forKey: "fromStation")
            newTrain.setValue(end, forKey: "toStation")
            newTrain.setValue(number, forKey: "trainNum")
        }
        
        do {
            try manageContext.save()
        } catch {
            print("Failure to save context: \(error)")
            throw CoreDataError.storeError
        }
    }
    
    func getTrainsFromCoreData(page: Int, size: Int, resultHandler: ([TrainList]?) -> Void) {
        let fetchRequest = NSFetchRequest(entityName: "TrainList")
        
        fetchRequest.fetchLimit = size
        fetchRequest.fetchOffset = page
        
        do {
            let fetchResult = try manageContext.executeFetchRequest(fetchRequest)
            let list = fetchResult.map { $0 as! TrainList }
            resultHandler(list)
        } catch {
            resultHandler(nil)
            print("Failure to save context: \(error)")
        }

    }
    
    func deleteAllTrainList(resultHandler: (Bool) -> Void) throws {
        let coord = appDelegate.persistentStoreCoordinator
        let fetchRequest = NSFetchRequest(entityName: "TrainList")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: fetchRequest)
        
        do {
            try coord.executeRequest(deleteRequest, withContext: manageContext)
            resultHandler(true)
        } catch {
            resultHandler(false)
            print("Failure to save context: \(error)")
            throw CoreDataError.deleteError
        }
    }
    
    func getTrainNumByTrainCode(trainCode: String) throws -> TrainList? {
        var train:TrainList? = nil
        let fetchRequest = NSFetchRequest(entityName: "TrainList")
        fetchRequest.predicate = NSPredicate(format: "trainCode == %@", trainCode)
        do {
            let fetchResult = try manageContext.executeFetchRequest(fetchRequest)
            train = fetchResult.last as? TrainList
        } catch {
            print("Failure to save context: \(error)")
            throw CoreDataError.queryError
        }
        return train
    }
    
    
    //Train station function
    func storeStationList(stations: [(String, String)]) throws {
        stations.forEach { (name, code) -> () in
            let newTrain = NSEntityDescription.insertNewObjectForEntityForName("Station", inManagedObjectContext: manageContext)
            newTrain.setValue(name, forKey: "name")
            newTrain.setValue(code, forKey: "code")
        }
        
        do {
            try manageContext.save()
        } catch {
            print("Failure to save context: \(error)")
            throw CoreDataError.storeError
        }
    }
    
    func getStationsFromCoreData(page page: Int, size: Int, resultHandler: ([Station]?) -> Void) throws {
        let fetchRequest = NSFetchRequest(entityName: "Station")
        
        fetchRequest.fetchLimit = size
        fetchRequest.fetchOffset = page
        
        do {
            let fetchResult = try manageContext.executeFetchRequest(fetchRequest)
            let list = fetchResult.map { $0 as! Station }
            resultHandler(list)
        } catch {
            resultHandler(nil)
            print("Failure to save context: \(error)")
            throw CoreDataError.queryError
        }
    }
    
    func deleteAllStations(resultHandler: (Bool) -> Void) throws {
        let coord = appDelegate.persistentStoreCoordinator
        let fetchRequest = NSFetchRequest(entityName: "Station")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: fetchRequest)
        
        do {
            try coord.executeRequest(deleteRequest, withContext: manageContext)
            resultHandler(true)
        } catch {
            resultHandler(false)
            print("Failure to save context: \(error)")
            throw CoreDataError.deleteError
        }

    }
    
    func getStationCodeByName(name stationName: String) -> String? {
        var code:String?
        let fetchRequest = NSFetchRequest(entityName: "Station")
        fetchRequest.predicate = NSPredicate(format: "name == %@", stationName)
        do {
            let fetchResult = try manageContext.executeFetchRequest(fetchRequest)
            let station = fetchResult.last as? Station
            code = station?.code
        } catch {
            print("Failure to save context: \(error)")
        }
        return code
    }
    
    //For City ID list
    func storeCityList(cityList: [CityInfo]) throws {
        cityList.forEach { (city) -> () in
            let newCity = NSEntityDescription.insertNewObjectForEntityForName("City", inManagedObjectContext: manageContext)
            newCity.setValue(city.name, forKey: "name")
            newCity.setValue(city.id, forKey: "id")
        }
        
        do {
            try manageContext.save()
        } catch {
            print("Failure to save context: \(error)")
            throw CoreDataError.storeError
        }
    }
    
    func getCityIdByName(name:String) -> String? {
        var city:City?
        let fetchRequest = NSFetchRequest(entityName: "City")
        fetchRequest.predicate = NSPredicate(format: "name == %@", name)
        do {
            let fetchResult = try manageContext.executeFetchRequest(fetchRequest)
            city = fetchResult.last as? City
        } catch {
            print("Failure to save context: \(error)")
            return nil
        }
        return city?.id
    }
    
    
    //For Learning
    func testAddData(names: [String], codes: [String]) {


        for (var i = 0; i < names.count; i++) {
            let newStation = NSEntityDescription.insertNewObjectForEntityForName("Station", inManagedObjectContext: manageContext)
            newStation.setValue(names[i], forKey: "name")
            newStation.setValue(codes[i], forKey: "code")

        }
        
        do {
            try manageContext.save()
        } catch {
            fatalError("Failure to save context: \(error)")
        }
    }
    
    
    func getData() {
        let fetchRequest = NSFetchRequest(entityName: "Station")
        
        do {
            let fetchedResults = try manageContext.executeFetchRequest(fetchRequest)
            
            fetchedResults.map { test.append($0 as! Station) }
        } catch {
            fatalError("Failure to save context: \(error)")
        }
    }
    
    func displayData() {
        test.map { print("station name:\($0.name!)"); print("station code:\($0.code!)") }
    }
    
    func deleteAll() {
        let coord = appDelegate.persistentStoreCoordinator
        let fetchRequest = NSFetchRequest(entityName: "Station")
        let deleteRequest = NSBatchDeleteRequest(fetchRequest: fetchRequest)
        
        do {
            try coord.executeRequest(deleteRequest, withContext: manageContext)
        } catch let error as NSError {
            print(error)
        }
    }
    
    func testQuery(stationName: String) {
        let fetchRequest = NSFetchRequest(entityName: "Station")
        fetchRequest.fetchLimit = 20
        fetchRequest.fetchOffset = 0
        fetchRequest.predicate = NSPredicate(format: "name == %@", stationName)
        do {
            let fetchResult = try manageContext.executeFetchRequest(fetchRequest)
            fetchResult.map { test.append($0 as! Station) }
        } catch {
            print(error)
        }
    }
}