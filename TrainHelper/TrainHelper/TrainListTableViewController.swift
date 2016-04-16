//
//  TrainListTableViewController.swift
//  TrainHelper
//
//  Created by Teng on 4/10/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import UIKit
import MJRefresh
import MBProgressHUD

class TrainListTableViewController: UITableViewController {
    
    var trainList = [TrainList]()
    
    let trainModel = TrainModel()
    let dataModel = DataController()
    var page = 0
    let size = 30

    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem()
        
//        TrainModel().getTrainSchedule(from: "", to: "", trainNum: "", resultHandler:)
//        TrainModel().getTrainStations()
//        TrainModel().getTrainList()
//        WeatherModel().getWeatherInfo()
        
//        let testName = ["test1", "test2", "tset3"]
//        let testCode = ["c1", "c2", "c3"]
        
        
//        let test = DataController()
//        test.testAddData(testName, codes: testCode)
//        test.getData()
        
//        test.deleteAll()
//        test.testQuery("test")
//        test.displayData()
        
        tableView.mj_header = MJRefreshNormalHeader(refreshingBlock: { () -> Void in
            print("RefreshingHeader")
            let hud = MBProgressHUD.showHUDAddedTo(self.view, animated: true)
            //刷新CoreData库
            self.dataModel.getTrainsFromCoreData(0, size: self.size, resultHandler: { (list) -> Void in
                if let trains = list {
                    self.trainList = trains
                    self.tableView.reloadData()
                    hud.hide(true)
                }
            })

            dispatch_async(dispatch_get_main_queue()) { () -> Void in
                
                self.tableView.mj_header.endRefreshing()
            }
        })
        
        tableView.mj_footer = MJRefreshBackNormalFooter(refreshingBlock: { () -> Void in
            print("RefreshingFooter")
            
            //加载更多数据
            self.dataModel.getTrainsFromCoreData(self.page, size: self.size, resultHandler: { (list) -> Void in
                if let trains = list {
                    self.trainList.appendContentsOf(trains)
                    self.tableView.reloadData()
                }
            })
            
            dispatch_async(dispatch_get_main_queue()) { () -> Void in
                self.tableView.mj_footer.endRefreshing()
            }
        })
        
        tableView.mj_header.automaticallyChangeAlpha = true
        
        //从Coredata中加载数据
        dataModel.getTrainsFromCoreData(0, size: size, resultHandler: { (list) -> Void in
            if let trains = list {
                self.trainList = trains
                if self.trainList.count == 0 {
                    //如果没有数据从远端加载
                    let hud = MBProgressHUD.showHUDAddedTo(self.view, animated: true)
                    hud.mode = .Text
                    hud.labelText = "暂无数据"
                    hud.detailsLabelText = "下拉更新数据"
                    do {
                        try self.dataModel.deleteAllTrainList({ (result) -> Void in
                            if result {
                                print("delete train list in core data success")
                                self.trainModel.getTrainList({ (result) -> () in
                                    if result {
                                        print("store success")
                                        self.dataModel.getTrainsFromCoreData(0, size: self.size, resultHandler: { (list) -> Void in
                                            if let trains = list {
                                                self.trainList = trains
                                                self.tableView.reloadData()
                                                hud.hide(true)
                                            }
                                        })
                                    } else {
                                        print("store trains fail")
                                    }
                                })
                            } else {
                                print("delete train list in core data fail")
                            }
                        })
                    } catch {
                        print("throw exception delete data fail")
                    }
                } else {
                    self.tableView.reloadData()
                }
            }
        })
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return trainList.count
    }

    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCellWithIdentifier("trainCell", forIndexPath: indexPath)

        cell.textLabel?.text = trainList[indexPath.row].trainCode

        return cell
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
    
//    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
//        let controller = TrainTimeTableViewController()
////        let row = ( sender as! NSIndexPath ).row//Could not cast value of type 'UITableViewCell' (0x107a2c128) to 'NSIndexPath' (0x1063d7410).
//        controller.title = self.trainList[indexPath.row].trainCode
//        controller.trainNum = self.trainList[indexPath.row].trainNum
//        controller.fromStation = self.trainList[indexPath.row].fromStation
//        controller.toStation = self.trainList[indexPath.row].toStation
//    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if (segue.identifier == "Schedule") {
            let controller = segue.destinationViewController as! TrainTimeTableViewController
            controller.title = self.trainList[tableView.indexPathForSelectedRow!.row].trainCode
            controller.trainNum = self.trainList[tableView.indexPathForSelectedRow!.row].trainNum
            controller.fromStation = self.trainList[tableView.indexPathForSelectedRow!.row].fromStation
            controller.toStation = self.trainList[tableView.indexPathForSelectedRow!.row].toStation
        }
    }
    

}
