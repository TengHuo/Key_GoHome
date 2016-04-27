#Train Helper
An iOS app using 4 different api provide trian schedule and station information. Support train number search and train station around search. Otherwise it can show city weather information which the train station located.

##Deployment

###Envioronment

1. Xcode 7.2(7C68)
2. iOS Simulator 9.2(SimulatorApp-643)

###Development tools

1. cocoapods 1.0.0.beta.3

###Pod Dependencies

1. [AMap2DMap](https://cocoapods.org/pods/AMap2DMap) (3.3.0):Map SDK
2. [AMapSearch](https://cocoapods.org/pods/AMapSearch) (3.3.0):Map SDK
3. [Alamofire](https://github.com/Alamofire/Alamofire) (3.2.1):Alamofire is an HTTP networking library written in Swift.
4. [MJRefresh](https://github.com/CoderMJLee/MJRefresh) (3.1.0):An easy way to use pull-to-refresh framework.
5. [PKHUD](https://github.com/pkluz/PKHUD) (3.1.0):A Swift based reimplementation of the Apple HUD (Volume, Ringer, Rotation,…) for iOS 8 and up. 
6. [SWXMLHash](https://github.com/drmohundro/SWXMLHash) (2.3.0):SWXMLHash is a relatively simple way to parse XML in Swift.
7. [SwiftyJSON](https://github.com/SwiftyJSON/SwiftyJSON) (2.3.2):SwiftyJSON makes it easy to deal with JSON data in Swift.

###Deployment steps

1. Decompression zip package 
2. Use terminal to install dependencies

```
$ cd ./TrainHelper
$ pod install
```

3. Open TrainHelper.xcworkspace with Xcode and run.

##API Introduction

1. 12306

 I download train number list and station list from [12306.com](). All they are javascript file so I use JavaScriptCore in iOS app to format data from string. After this all data will be stored in CoreData. There are two lists link.

 * [train number list](https://kyfw.12306.cn/otn/resources/js/query/train_list.js)
 * [station list](https://kyfw.12306.cn/otn/resources/js/framework/station_name.js)

 And app can get train schedule from 12306.com with train number. Here is an example json data.
 
 * [train schedule API](https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=240000D3210B&from_station_telecode=VNP&to_station_telecode=SHH&depart_date=2016-04-01)

2. AMap
App use [AMap](http://lbs.amap.com/) SDK to show railway station in map. User can choose the station name on schedule, it will jump into map view to located the station.
 
3. OpenWeatherMap
All weather data come from [OpenWeatherMap](http://openweathermap.org/) with longitude and latitude data. AMap SDK can locate station city and coordinate.

4. Baidu nuomi
With city name app can load shop information from [Baidu nuomi]() and show stores list. Shop information consist of store name and simple description.

##Architecture Design and Implementation

####Architecture Logical diagram

![](/Users/huoteng/Desktop/TrainHelperImage/21.jpeg)

####CoreData Design

App store train number list and railway station name list in CoreData. When app ask Baidu nuomi data it need to a city name and code map that also store in CoreData.

![](/Users/huoteng/Desktop/coredata.png)

####Format Data

1. When app download files from 12306 it must format data from javascript. JavaScriptCore can create javascript enviroment and pick up formatted data. Then app use regex to match station name from string.
 
 ```
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
 
 let trainsInfo = context.evaluateScript("trainsInfo").toArray()
 ```
 
2. OpenWeatherMap provide weather data with XML, so it need to format data from XML. But there is no XML scheme or XSLT in iOS. Luckly SWXMLHash has a relatively simple way to parse XML in Swift. Conceptually, it provides a translation from XML to a dictionary of arrays.

 ```
 let xml = SWXMLHash.parse(data!)
 let tempMax = xml["current"]["temperature"][0].element!.attributes["max"]!
 let tempMin = xml["current"]["temperature"][0].element!.attributes["min"]!

 let condi = xml["current"]["weather"][0].element!.attributes["number"]!
 let desc = xml["current"]["weather"][0].element!.attributes["value"]!

 let weather = WeatherBean(condi: condi, desc: desc, min: tempMin, max: tempMax)
 ```
 Bean
 ```
 class WeatherBean {
    var condition:Int
    var description:String
    var temperatureMin:Double
    var temperatureMax:Double
    
    init(condi: String, desc: String, min: String, max: String) {
        condition = Int(condi)!
        description = desc
        temperatureMax = Double(max)! - 273.15
        temperatureMin = Double(min)! - 273.15
    }
 }
 ```

3. Baidu nuomi response is json format. And there are so many keys in store information object. I create another Bean to package data that is required. SwiftyJSON is used for pick up store name and descrition from response.

 ```
 let jsonData = JSON(data)
 print("shop detail:\(jsonData)")

 let deals = jsonData["data"]["deals"].array!

 let titleList = deals.map {
    ShopBean(name: $0["title"].string!, detail: $0["description"].string!)
 }
 ```

##UI

1. Train number list(home page)

 ![](/Users/huoteng/Desktop/TrainHelperImage/1.png)
 
2. Update CoreData
 User can update train data with the upper left corner button. Because of downloading javascript files I give advice before downloading data.

 ![](/Users/huoteng/Desktop/TrainHelperImage/2.png)

3. Train schedule

 ![](/Users/huoteng/Desktop/TrainHelperImage/3.png)

4. Map

 ![](/Users/huoteng/Desktop/TrainHelperImage/4.png)

5. Store information
 ![](/Users/huoteng/Desktop/TrainHelperImage/5.png)

6. Push refresh
 When user push down home page, it will update train number.

 ![](/Users/huoteng/Desktop/TrainHelperImage/6.png)

 After successfully finish update home page.
 
 ![](/Users/huoteng/Desktop/TrainHelperImage/7.png)
 
 If there is no data in CoreData, it will prompt user an error.
 
 ![](/Users/huoteng/Desktop/TrainHelperImage/18.png)
 
7. Search function
 In the top of home page, user can input train number to search.
 ![](/Users/huoteng/Desktop/TrainHelperImage/8.png)
 This is search result.
 ![](/Users/huoteng/Desktop/TrainHelperImage/10.png)
 If there is no result, such as user input "1234", it will prompt user.
 ![](/Users/huoteng/Desktop/TrainHelperImage/9.png)
 
8. Load more data
 In every view, user can push up to load more data.
 ![](/Users/huoteng/Desktop/TrainHelperImage/13.png)
 ![](/Users/huoteng/Desktop/TrainHelperImage/15.png)

