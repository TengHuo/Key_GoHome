//
//  StationWeatherInfoTableViewController.swift
//  TrainHelper
//
//  Created by Teng on 4/13/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import UIKit
import MJRefresh

class StationWeatherInfoTableViewController: UITableViewController {
    
    var city:String?
    var location:CLLocation?
    
    let shopModel = ShopModel()
    let dataController = DataController()
    let weatherModel = WeatherModel()
    var weatherInfo:WeatherBean?
    var shopList = [String]()
    var page = 1
    var cityId:String!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.mj_footer = MJRefreshBackNormalFooter(refreshingBlock: { () -> Void in
            print("RefreshingFooter")
            
            //加载更多数据
            self.shopModel.getShopInfo(self.cityId, page: self.page, resultHandler: { result in
                if let shops = result {
                    self.shopList.appendContentsOf(shops)
                    self.tableView.reloadData()
                    self.page++
                }
            })
            
            dispatch_async(dispatch_get_main_queue()) { () -> Void in
                self.tableView.mj_footer.endRefreshing()
            }
        })

    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        if let _ = location {
            weatherModel.getWeatherInfo(location!.coordinate.latitude, lon: location!.coordinate.longitude, resultHandler: { ( result ) -> () in
                if let info = result {
                    self.weatherInfo = info
                    self.tableView.reloadData()
                }
            })
        }
        
        if let _ = city {
            if let id = dataController.getCityIdByName(city!) {
                self.cityId = id
                shopModel.getShopInfo(id, page: 1, resultHandler: { result in
                    if let shops = result {
                        self.shopList = shops
                        self.tableView.reloadData()
                        self.page++
                    }
                })
            } else {
                shopModel.getCityIdAndStore()
            }
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
        return shopList.count + 1
    }

    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var identifier = ""

        if 0 == indexPath.row {
            identifier = "weatherInfo"
            
            let cell = tableView.dequeueReusableCellWithIdentifier(identifier, forIndexPath: indexPath)
            if let info = weatherInfo {
                let weaterhImage = cell.viewWithTag(100) as! UIImageView
                weaterhImage.image = UIImage(named: getWeatherIconName(info.condition))
                let cityNameLabel = cell.viewWithTag(101) as! UILabel
                cityNameLabel.text = city!
                let weaterhInfoLabel = cell.viewWithTag(102) as! UILabel
                weaterhInfoLabel.text = "\(info.description)"
                
                let tempMinLabel = cell.viewWithTag(200) as! UILabel
                tempMinLabel.text = "\(info.temperatureMin)"
                
                let tempMaxLabel = cell.viewWithTag(201) as! UILabel
                tempMaxLabel.text = "\(info.temperatureMax)"
            }
            return cell
        } else {
            identifier = "shopInfo"
            let cell = tableView.dequeueReusableCellWithIdentifier(identifier, forIndexPath: indexPath)
            
            cell.textLabel?.text = shopList[indexPath.row-1]
            
            return cell
        }
        

    }
    
    override func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        if indexPath.row == 0 {
            return 150.0
        } else {
            return 44.0
        }
    }
    
    func getWeatherIconName(condition: Int) -> String {
        var imageName = "dunno"
        if (condition < 300) {
            imageName = "tstorm1"
        } else if (condition < 500) {
            imageName = "light_rain"
        } else if (condition < 600) {
            imageName = "shower3"
        } else if (condition < 700) {
            imageName = "snow4"
        } else if (condition < 771) {
            imageName = "fog"
        } else if (condition < 800) {
            imageName = "sunny"
        } else if (condition < 804) {
            imageName = "cloudy2"
        } else if (condition == 804) {
            imageName = "overcast"
        } else if ((condition >= 900 && condition < 903) || (condition > 904 && condition < 1000)) {
            imageName = "tstorm3"
        } else if (condition == 903) {
            imageName = "snow5"
        } else if (condition == 904) {
            imageName = "sunny"
        } else {
            imageName = "dunno"
        }
        return imageName
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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
