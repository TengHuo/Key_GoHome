//
//  TrainListTableViewController.swift
//  TrainHelper
//
//  Created by Teng on 4/10/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import UIKit
import MJRefresh
import PKHUD

class TrainListTableViewController: UITableViewController {
    
    var trainList = [TrainList]()
    
    let trainModel = TrainModel()
    let dataModel = DataController()
    var page = 0
    let size = 30
    
    var completedUpdateStation = false
    var completedUpdateTrainList = false

    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.mj_header = MJRefreshNormalHeader(refreshingBlock: { () -> Void in
            print("RefreshingHeader")
            //刷新CoreData库
            self.dataModel.getTrainsFromCoreData(0, size: self.size, resultHandler: { (list) -> Void in
                if let trains = list {
                    self.trainList = trains
                    self.tableView.reloadData()
                    if 0 != self.trainList.count {
                        HUD.flash(.Success, delay: 1.0)
                    } else {
                        HUD.flash(.LabeledError(title: "暂无数据", subtitle: "请更新数据库"), delay: 1.5)
                    }
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
        getDataFromLocal()
    }
    
    func getDataFromLocal() {
        //从Coredata中加载数据
        dataModel.getTrainsFromCoreData(0, size: size, resultHandler: { (list) -> Void in
            if let trains = list {
                self.trainList = trains
                if self.trainList.count == 0 {
                    HUD.flash(.LabeledError(title: "暂无数据", subtitle: "请更新数据库"), delay: 1.5)
                } else {
                    self.tableView.reloadData()
                }
            }
        })
    }
    
    @IBAction func updateCoreData(sender: AnyObject) {
        //更新数据库
        let alertController = UIAlertController(title: "更新数据库", message: "更新数据库会下载较多数据，建议连接Wi-Fi", preferredStyle: .ActionSheet)
        let confirmAction = UIAlertAction(title: "更新", style: .Destructive) { (action) -> Void in
            HUD.show(.Progress)
            //更新train list
            do {
                try self.dataModel.deleteAllTrainData({ (result) -> Void in
                    if result {
                        print("delete train list in core data success")
                        self.trainModel.getTrainList({ isSuccess in
                            if isSuccess {
                                print("store trains success")
                                self.completedUpdateTrainList = true
                                self.completedUpdateTrainData()
                            } else {
                                print("store trains fail")
                                HUD.flash(.LabeledError(title: "更新失败", subtitle: "请检查网络设置"), delay: 2.0)
                            }
                        })
                        
                        self.trainModel.getTrainStations({ isSuccess in
                            if isSuccess {
                                print("store station info success")
                                self.completedUpdateStation = true
                                self.completedUpdateTrainData()
                            } else {
                                print("store station info fail")
                                HUD.flash(.LabeledError(title: "更新失败", subtitle: "请检查网络设置"), delay: 2.0)
                            }
                        })
                    } else {
                        HUD.flash(.LabeledError(title: "更新失败", subtitle: "请检查网络设置"), delay: 2.0)
                        print("delete train list in core data fail")
                    }
                })
            } catch {
                HUD.flash(.LabeledError(title: "更新失败", subtitle: "请检查网络设置"), delay: 2.0)
                print("throw exception delete data fail")
            }
        }
        let cancelAction = UIAlertAction(title: "取消", style: .Cancel, handler: nil)
        alertController.addAction(confirmAction)
        alertController.addAction(cancelAction)
        self.presentViewController(alertController, animated: true, completion: nil)
        
    }
    
    func completedUpdateTrainData() {
        if self.completedUpdateStation && self.completedUpdateTrainList {
            HUD.flash(.LabeledSuccess(title: "更新成功", subtitle: nil), delay: 1.0)
            getDataFromLocal()
        }
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

        let trainCodeLabel = cell.viewWithTag(100) as! UILabel
        trainCodeLabel.text = trainList[indexPath.row].trainCode
        
        let fromStationLabel = cell.viewWithTag(101) as! UILabel
        fromStationLabel.text = trainList[indexPath.row].fromStation
        
        let toStationLabel = cell.viewWithTag(102) as! UILabel
        toStationLabel.text = trainList[indexPath.row].toStation

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
