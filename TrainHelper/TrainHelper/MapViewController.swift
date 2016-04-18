//
//  ViewController.swift
//  TrainHelper
//
//  Created by Teng on 4/10/16.
//  Copyright © 2016 huoteng. All rights reserved.
//

import UIKit

let APIKey = "5ae773cd45d72313c87b0f2dd3a0bddc"
class MapViewController: UIViewController, MAMapViewDelegate, AMapSearchDelegate {
    
    var mapView:MAMapView?
    var stationName:String?
    let search = AMapSearchAPI()
    var locaiton:CLLocation?
    var city:String?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        MAMapServices.sharedServices().apiKey = APIKey
        AMapSearchServices.sharedServices().apiKey = APIKey

        initMapView()
        if let name = stationName {
            print("1:\(name)")
            search.delegate = self
            
            let request = AMapPOIKeywordsSearchRequest()
            request.keywords = "\(name)"
            request.types = "150200"
            search.AMapPOIKeywordsSearch(request)
        }
        
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        
//        let pointAnnotation = MAPointAnnotation()
//        pointAnnotation.coordinate = CLLocationCoordinate2DMake(38.989631, 115.481018)
//        pointAnnotation.title = "测试"
//        pointAnnotation.subtitle = "细节"
//        
//        mapView!.addAnnotation(pointAnnotation)
    }
    
    func onPOISearchDone(request: AMapPOISearchBaseRequest!, response: AMapPOISearchResponse!) {
        if (response.pois.count == 0) {
            print("there is no data")
            return
        }
        
//        let strCount = "count:\(response.count)"
//        let strSuggestion = "suggestino:\(response.suggestion)"
        let desc = response.pois.map{ ($0 as! AMapPOI).name }
        print(desc)
        
        let poi = response.pois.first as! AMapPOI
        let coordinate = CLLocationCoordinate2DMake(Double(poi.location.latitude), Double(poi.location.longitude))
        let pointAnnotation = MAPointAnnotation()
        
        self.locaiton = CLLocation(latitude: coordinate.latitude, longitude: coordinate.longitude)
        self.city = poi.city
        pointAnnotation.coordinate = coordinate
        pointAnnotation.title = poi.name
        mapView!.addAnnotation(pointAnnotation)

        mapView!.centerCoordinate = coordinate
    }
    
    func mapView(mapView: MAMapView!, viewForAnnotation annotation: MAAnnotation!) -> MAAnnotationView! {
        print("tag")
        if annotation.isKindOfClass(MAPointAnnotation) {
            let annotationIdentifier = "invertGeoIdentifier"
            
            var poiAnnotationView = mapView.dequeueReusableAnnotationViewWithIdentifier(annotationIdentifier) as? MAPinAnnotationView
            
            if poiAnnotationView == nil {
                poiAnnotationView = MAPinAnnotationView(annotation: annotation, reuseIdentifier: annotationIdentifier)
            }
            poiAnnotationView!.pinColor = MAPinAnnotationColor.Green
            poiAnnotationView!.animatesDrop   = true
            poiAnnotationView!.canShowCallout = true
            
            return poiAnnotationView
        }
        return nil
    }
    
    func mapView(mapView: MAMapView!, didSelectAnnotationView view: MAAnnotationView!) {
        print("select point")
    }
    
    
    func mapView(mapView: MAMapView!, didAddAnnotationViews views: [AnyObject]!) {
        let annotationView: MAAnnotationView! = views[0] as! MAAnnotationView
        mapView.selectAnnotation(annotationView.annotation, animated: true)
    }
    
    func initMapView() {
        mapView = MAMapView(frame: self.view.bounds)
        mapView!.delegate = self
        mapView!.zoomEnabled = true
        mapView!.showsCompass = true
        mapView!.showsScale = true
        self.view.addSubview(mapView!)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "stationDetail" {
            let controller = segue.destinationViewController as! StationWeatherInfoTableViewController
            controller.location = locaiton
            controller.city = city
            controller.title = city
        }
    }

}

