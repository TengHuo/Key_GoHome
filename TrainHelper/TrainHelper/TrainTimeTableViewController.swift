//
//  TrainTimeTableViewController.swift
//  TrainHelper
//
//  Created by Teng on 4/13/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import UIKit

class TrainTimeTableViewController: UITableViewController {
    
    
    var trainCode: String?
    var trainNum: String?
    var fromStation: String?
    var toStation: String?
    var schedule = [StopStation]()
    let trainModel = TrainModel()
    let dataModel = DataController()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        guard let _ = dataModel.getStationCodeByName(name: "北京北") else {
            trainModel.getTrainStations({ (result) -> Void in
                if result {
                    print("success")
                } else {
                    print("error")
                }
            })
            if let number = trainNum, startStation = dataModel.getStationCodeByName(name: fromStation!), endStation = dataModel.getStationCodeByName(name: toStation!)  {
                
                trainModel.getTrainSchedule(from: startStation, to: endStation, trainNum: number, resultHandler: { (result) -> Void in
                    if let list = result {
                        self.schedule = list
                        self.tableView.reloadData()
                    }
                })
                
                
            }
            return
        }
        
        if let number = trainNum, startStation = dataModel.getStationCodeByName(name: fromStation!), endStation = dataModel.getStationCodeByName(name: toStation!)  {
            
            trainModel.getTrainSchedule(from: startStation, to: endStation, trainNum: number, resultHandler: { (result) -> Void in
                if let list = result {
                    self.schedule = list
                    self.tableView.reloadData()
                }
            })
            
            
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return schedule.count
    }

    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("timeCell", forIndexPath: indexPath)

        let stationLabel = cell.viewWithTag(100) as! UILabel
        stationLabel.text = schedule[indexPath.row].stationName
        
        let subtitle = cell.viewWithTag(101) as! UILabel
        subtitle.text = schedule[indexPath.row].arriveTime

        return cell
    }
    

    override func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return 70
    }
    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if (segue.identifier == "Map") {
            let controller = segue.destinationViewController as! MapViewController
            controller.title = self.schedule[tableView.indexPathForSelectedRow!.row].stationName
            controller.stationName = self.schedule[tableView.indexPathForSelectedRow!.row].stationName
        }
    }
    

}
