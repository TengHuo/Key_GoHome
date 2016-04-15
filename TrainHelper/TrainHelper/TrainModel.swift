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

class TrainModel {
    
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
    
    func getTrainStations() {
        var fileName: String?
        var finalPath: NSURL?
        
        Alamofire.download(.GET, "https://kyfw.12306.cn/otn/resources/js/framework/station_name.js", destination: { (temporaryURL, response) in
            if let directoryURL = NSFileManager.defaultManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask)[0] as? NSURL {
                
                fileName = response.suggestedFilename!
                finalPath = directoryURL.URLByAppendingPathComponent(fileName!)
                return finalPath!
            }
            
            return temporaryURL
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
                        
                        print("station_names:\(stationsList)")
                        stationsList.map { print("station:\($0)") }
                    }
                    catch {
                        print("ERROR")
                    }
                }

            }
        }
    }
    
    func getTrainList() {
        var fileName: String?
        var finalPath: NSURL?
        
        Alamofire.download(.GET, "https://kyfw.12306.cn/otn/resources/js/query/train_list.js", destination: { (temporaryURL, response) in
            if let directoryURL = NSFileManager.defaultManager().URLsForDirectory(.DocumentDirectory, inDomains: .UserDomainMask)[0] as? NSURL {
                
                fileName = response.suggestedFilename!
                finalPath = directoryURL.URLByAppendingPathComponent(fileName!)
                return finalPath!
            }
            
            return temporaryURL
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
                        
                        let trainsDict = context.evaluateScript("train_list").toDictionary()
                        
                        print("train_list:\(trainsDict)")
                    }
                    catch {
                        print("ERROR")
                    }
                }
                
            }
        }
    }
    
    func getTrainSchedule() {
        
        let departDate = "2016-04-01"
        let toStation = "SHH"
        let fromStation = "VNP"
        let trainNo = "240000D3210B"
        
        let url = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=\(trainNo)&from_station_telecode=\(fromStation)&to_station_telecode=\(toStation)&depart_date=\(departDate)"
                
        Alamofire.request(.GET, url, parameters: nil, encoding: .URL, headers: nil)
            .responseJSON {  response in
                print(response.request)  // original URL request
                print(response.response) // URL response
                print(response.result)   // result of response serialization
                
                if let JSON = response.result.value {
                    print("JSON: \(JSON)")
                }
        }
    }
}