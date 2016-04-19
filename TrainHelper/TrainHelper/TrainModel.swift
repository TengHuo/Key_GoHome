//
//  TrainModel.swift
//  TrainHelper
//
//  Created by Teng on 4/14/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import Foundation
import Alamofire
import JavaScriptCore
import SwiftyJSON

class TrainModel {
    
    let dataModel = DataController()
    
    init() {
        let manager = Alamofire.Manager.sharedInstance
        manager.delegate.sessionDidReceiveChallenge = { session, challenge in
            var disposition: NSURLSessionAuthChallengeDisposition = .PerformDefaultHandling
            var credential: NSURLCredential?
            
            if challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodServerTrust {
                disposition = NSURLSessionAuthChallengeDisposition.UseCredential
                credential = NSURLCredential(forTrust: challenge.protectionSpace.serverTrust!)
            } else {
                if challenge.previousFailureCount > 0 {
                    disposition = .CancelAuthenticationChallenge
                } else {
                    credential = manager.session.configuration.URLCredentialStorage?.defaultCredentialForProtectionSpace(challenge.protectionSpace)
                    
                    if credential != nil {
                        disposition = .UseCredential
                    }
                }
            }
            
            return (disposition, credential)
        }

    }
    
    func getTrainStations(resultHandler: (Bool) -> Void) {
        var fileName: String?
        var finalPath: NSURL?
        
        Alamofire.download(.GET, "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js", destination: { (temporaryURL, response) in
            let directoryURL = NSFileManager.defaultManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask)[0]
            fileName = response.suggestedFilename!
            finalPath = directoryURL.URLByAppendingPathComponent(fileName!)
            return finalPath!
        }).response { (request, response, data, error) -> Void in
            if error != nil {
                print("REQUEST: \(request)")
                print("RESPONSE: \(response)")
            }
            
            if finalPath != nil {
//                print("filePath:\(finalPath!)")
//                print("NAME:\(fileName!)")
                
                if let dir : NSString = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.AllDomainsMask, true).first {
                    let path = dir.stringByAppendingPathComponent(fileName!)
                    
                    //reading
                    do {
                        let stationJS = try NSString(contentsOfFile: path, encoding: NSUTF8StringEncoding)
                        
                        let context = JSContext()
                        context.evaluateScript(stationJS as String)
                        
                        let stationStr = context.evaluateScript("station_names")
                        let stationsList = stationStr.toString().componentsSeparatedByString("@")
                        let stationInfoList = stationsList.map { $0.componentsSeparatedByString("|") }
                        
                        var stationInfo = [(String, String)]()
                        for info in stationInfoList {
                            if info.count > 1 {
                                stationInfo.append((info[1], info[2]))
                            }
                        }
                        
                        //存数据库
                        do {
                            try self.dataModel.storeStationList(stationInfo)
                            resultHandler(true)
                        } catch {
                            resultHandler(false)
                        }
                    }
                    catch {
                        print("ERROR")
                        resultHandler(false)
                    }
                }

            }
        }
    }
    
    func getTrainList(resultHandler: (Bool) -> ()) {
        var fileName: String?
        var finalPath: NSURL?
        
        Alamofire.download(.GET, "https://kyfw.12306.cn/otn/resources/js/query/train_list.js", destination: { (temporaryURL, response) in
            let directoryURL = NSFileManager.defaultManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask)[0]
            fileName = response.suggestedFilename!
            finalPath = directoryURL.URLByAppendingPathComponent(fileName!)
            return finalPath!
        }).response { (request, response, data, error) -> Void in
            if error != nil {
//                print("REQUEST: \(request)")
//                print("RESPONSE: \(response)")
            }
            
            if finalPath != nil {
//                print("filePath:\(finalPath!)")
//                print("NAME:\(fileName!)")
                
                if let dir : NSString = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.AllDomainsMask, true).first {
                    let path = dir.stringByAppendingPathComponent(fileName!)
                    
                    //reading
                    do {
                        let trainsJS = try NSString(contentsOfFile: path, encoding: NSUTF8StringEncoding)
                        
                        let context = JSContext()
                        context.evaluateScript(trainsJS as String)
                        context.evaluateScript("var trainArrayD = train_list['2016-03-24']['D'];")
                        context.evaluateScript("var trainArrayT = train_list['2016-03-24']['T'];")
                        context.evaluateScript("var trainArrayZ = train_list['2016-03-24']['Z'];")
                        context.evaluateScript("var trainArrayG = train_list['2016-03-24']['G'];")
                        context.evaluateScript("var trainArrayC = train_list['2016-03-24']['C'];")
                        context.evaluateScript("var trainArrayO = train_list['2016-03-24']['O'];")
                        context.evaluateScript("var trainArrayK = train_list['2016-03-24']['K'];")
                        context.evaluateScript("var trains = [].concat(trainArrayD).concat(trainArrayT).concat(trainArrayZ).concat(trainArrayG).concat(trainArrayC).concat(trainArrayO).concat(trainArrayK);")
                        context.evaluateScript("var trainsInfo = [];for (var i = trains.length - 1; i >= 0; i--) {trainsInfo.push(trains[i]['station_train_code'] + '|' + trains[i]['train_no']);}")
                        
//                        let trainsArrayD = context.evaluateScript("trainArrayD").toArray()
//                        let trainsArrayT = context.evaluateScript("trainArrayT").toArray()
//                        let trainsArrayZ = context.evaluateScript("trainArrayZ").toArray()
//                        let trainsArrayG = context.evaluateScript("trainArrayG").toArray()
//                        let trainsArrayC = context.evaluateScript("trainArrayC").toArray()
//                        let trainsArrayO = context.evaluateScript("trainArrayO").toArray()
//                        let trainsArrayK = context.evaluateScript("trainArrayK").toArray()
//                        
//                        print("D:\(trainsArrayD.count),T:\(trainsArrayT.count),Z:\(trainsArrayZ.count),G:\(trainsArrayG.count),C:\(trainsArrayC.count),O:\(trainsArrayO.count),K:\(trainsArrayK.count)")
//                        print("Sum:\(trainsArrayO.count + trainsArrayC.count + trainsArrayD.count + trainsArrayG.count + trainsArrayT.count + trainsArrayZ.count + trainsArrayK.count)")
                        
                        let trainsInfo = context.evaluateScript("trainsInfo").toArray()
                        let list = trainsInfo.map({ (train) -> (String, String, String, String) in
                            let trainDetail = train as! String
                            let details = trainDetail.componentsSeparatedByString("|")
                            let codeAndStart = details[0].componentsSeparatedByString("-")[0]
                            let code = codeAndStart.componentsSeparatedByString("(")[0]
                            let start = codeAndStart.componentsSeparatedByString("(")[1]
                            let end = details[0].componentsSeparatedByString("-")[1].componentsSeparatedByString(")")[0]
                            let number = details[1]
                            
                            return (code, start, end, number)
                        })
                        
                        //存数据库
                        do {
                            try self.dataModel.storeTrainList(list)
                            resultHandler(true)
                        } catch {
                            resultHandler(false)
                        }
                        
//                        print("First:\(list[0].0)")
//                        print("Last:\(list[list.count-1].0)")
//                        print("Lenght:\(list.count)")
                    }
                    catch {
                        print("ERROR")
                        resultHandler(false)
                    }
                }
            }
        }
    }
    
    func getTrainSchedule(from startStation: String, to toStation: String, trainNum: String, resultHandler: ([StopStation]?) -> Void) {
        
        let departDate = "2016-04-17"
//        let toStation = "SHH"
//        let fromStation = "VNP"
//        let trainNo = "240000D3210B"
        
        let url = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=\(trainNum)&from_station_telecode=\(startStation)&to_station_telecode=\(toStation)&depart_date=\(departDate)"
                
        Alamofire.request(.GET, url, parameters: nil, encoding: .URL, headers: nil)
            .responseJSON {  response in
                print(response.request)  // original URL request
                print(response.response) // URL response
                print(response.result)   // result of response serialization
                
                if let value = response.result.value {
                    let json = JSON(value)
                    print("Data:\(json)")
                    let data = json["data"]["data"].array!
                    var stopStations = [StopStation]()
                    data.forEach({ (stationInfo) -> () in
                        let start_station_name = stationInfo["start_station_name"].string
                        let end_station_name = stationInfo["end_station_name"].string
                        let train_class_name = stationInfo["train_class_name"].string
                        let stopover_time = stationInfo["stopover_time"].string
                        let arrive_time = stationInfo["arrive_time"].string
                        let start_time = stationInfo["start_time"].string
                        let station_name = stationInfo["station_name"].string
                        
                        let stopStation = StopStation(startStation: start_station_name, arrive: arrive_time!, station: station_name!, trainClass: train_class_name, startTime: start_time!, stopover: stopover_time!, endStation: end_station_name)
                        stopStations.append(stopStation)
                    })
                    
                    resultHandler(stopStations)
                } else {
                    resultHandler(nil)
                }
        }
    }
}